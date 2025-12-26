package com.example.online_exam.controller;

import com.example.online_exam.entity.Paper;
import com.example.online_exam.entity.PaperQuestion;
import com.example.online_exam.repository.PaperRepository;
import com.example.online_exam.repository.PaperQuestionRepository;
import com.example.online_exam.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/paper")
@RequiredArgsConstructor
public class PaperController {
    
    private final PaperRepository paperRepository;
    private final PaperQuestionRepository paperQuestionRepository;
    private final QuestionRepository questionRepository;
    
    // ✅ 处理 /paper
    @GetMapping
    public String list(Model model) {
        List<Paper> papers = paperRepository.findAll();
        model.addAttribute("papers", papers);
        return "paper/list";
    }
    
    // ✅ 新增：处理 /paper/list（和上面的方法功能相同）
    @GetMapping("/list")
    public String listWithPath(Model model) {
        List<Paper> papers = paperRepository.findAll();
        model.addAttribute("papers", papers);
        return "paper/list";
    }
    
    // 创建试卷页面
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("paper", new Paper());
        return "paper/create";
    }
    
    // 保存试卷
    @PostMapping("/create")
    public String create(@ModelAttribute Paper paper) {
        paper.setCreatedTime(LocalDateTime.now());
        paperRepository.save(paper);
        return "redirect:/paper";  // 重定向到/paper而不是/paper/list
    }
    
    // 试卷详情
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Paper paper = paperRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("试卷不存在"));
        
        // 获取试卷的题目
        List<PaperQuestion> paperQuestions = paperQuestionRepository.findByPaperId(id);
        List<Map<String, Object>> questions = new ArrayList<>();
        
        for (PaperQuestion pq : paperQuestions) {
            questionRepository.findById(pq.getQuestionId()).ifPresent(q -> {
                Map<String, Object> qMap = new HashMap<>();
                qMap.put("id", q.getId());
                qMap.put("content", q.getContent());
                qMap.put("type", q.getType());
                qMap.put("score", pq.getScore());
                questions.add(qMap);
            });
        }
        
        model.addAttribute("paper", paper);
        model.addAttribute("questions", questions);
        return "paper/detail";
    }
    
    // 为固定卷添加题目（简单版）
    @GetMapping("/{id}/add-questions")
    public String addQuestionsForm(@PathVariable Long id, Model model) {
        Paper paper = paperRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("试卷不存在"));
        
        // 获取所有题目供选择
        var questions = questionRepository.findAll();
        
        model.addAttribute("paper", paper);
        model.addAttribute("questions", questions);
        return "paper/add-questions";
    }
    
    @PostMapping("/{id}/add-questions")
    public String addQuestions(@PathVariable Long id, 
                              @RequestParam List<Long> questionIds) {
        // 先删除旧的
        paperQuestionRepository.deleteByPaperId(id);
        
        // 添加新的
        Paper paper = paperRepository.findById(id).orElseThrow();
        int scoreEach = paper.getTotalScore() / questionIds.size();
        
        for (int i = 0; i < questionIds.size(); i++) {
            PaperQuestion pq = new PaperQuestion();
            pq.setPaperId(id);
            pq.setQuestionId(questionIds.get(i));
            pq.setOrderNum(i + 1);
            pq.setScore(scoreEach);
            paperQuestionRepository.save(pq);
        }
        
        return "redirect:/paper/" + id;
    }
}
