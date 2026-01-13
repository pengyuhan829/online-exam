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
import java.util.*;

@Controller
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {
    
    private final PaperRepository paperRepository;
    private final PaperQuestionRepository paperQuestionRepository;
    private final QuestionRepository questionRepository;
    
    // ========== 添加：考试首页 ==========
    @GetMapping({"", "/"})
    public String examHome(Model model) {
        return list(model); // 直接调用list方法
    }
    
    // 考试列表（学生可见）
    @GetMapping("/list")
    public String list(Model model) {
        List<Paper> papers = paperRepository.findByIsActiveTrue();
        model.addAttribute("papers", papers);
        return "exam/list";
    }
    
    // 开始考试
    @GetMapping("/start/{paperId}")
    public String startExam(@PathVariable Long paperId, Model model) {
        Paper paper = paperRepository.findById(paperId)
            .orElseThrow(() -> new RuntimeException("试卷不存在"));
        
        // 获取试卷题目
        List<PaperQuestion> paperQuestions = paperQuestionRepository.findByPaperId(paperId);
        List<Map<String, Object>> questions = new ArrayList<>();
        
        for (PaperQuestion pq : paperQuestions) {
            questionRepository.findById(pq.getQuestionId()).ifPresent(q -> {
                Map<String, Object> qMap = new HashMap<>();
                qMap.put("id", q.getId());
                qMap.put("content", q.getContent());
                qMap.put("type", q.getType());
                qMap.put("score", pq.getScore());
                
                // 处理选项（JSON字符串转列表）
                if (q.getOptions() != null && !q.getOptions().trim().isEmpty()) {
                    try {
                        // 简单处理：假设格式是 ["A.选项1","B.选项2"]
                        String optionsStr = q.getOptions();
                        optionsStr = optionsStr.replace("[", "").replace("]", "").replace("\"", "");
                        List<String> options = Arrays.asList(optionsStr.split(","));
                        
                        // 打乱选项顺序（防作弊）
                        if (q.getType().equals("SINGLE") || q.getType().equals("MULTI")) {
                            Collections.shuffle(options);
                        }
                        
                        qMap.put("options", options);
                    } catch (Exception e) {
                        qMap.put("options", new ArrayList<>());
                    }
                }
                
                questions.add(qMap);
            });
        }
        
        // 打乱题目顺序（防作弊）
        Collections.shuffle(questions);
        
        model.addAttribute("paper", paper);
        model.addAttribute("questions", questions);
        model.addAttribute("startTime", LocalDateTime.now());
        
        return "exam/exam-page";
    }
    
    // 提交考试
    @PostMapping("/submit/{paperId}")
    public String submitExam(@PathVariable Long paperId,
                            @RequestParam Map<String, String> allParams) {
        
        System.out.println("=== 学生提交试卷 === - ExamController.java:95");
        System.out.println("试卷ID: - ExamController.java:96" + paperId);
        
        // 提取答案（所有以answer_开头的参数）
        Map<String, String> answers = new HashMap<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("answer_")) {
                String questionId = entry.getKey().substring(7); // 去掉"answer_"
                answers.put(questionId, entry.getValue());
                System.out.println("题目 - ExamController.java:104" + questionId + "答案: " + entry.getValue());
            }
        }
        
        System.out.println("================ - ExamController.java:108");
        
        // TODO: 这里让人3实现判分逻辑
        // 暂时重定向到结果页面
        
        return "redirect:/exam/result?paperId=" + paperId;
    }
    
    // 考试结果
    @GetMapping("/result")
    public String result(@RequestParam Long paperId, Model model) {
        model.addAttribute("paperId", paperId);
        model.addAttribute("message", "试卷提交成功！客观题已自动批改。");
        return "exam/result";
    }
    
    // 查看考试记录
    @GetMapping("/records")
    public String records(Model model) {
        // TODO: 获取当前登录学生的考试记录
        // 暂时返回空列表
        model.addAttribute("records", new java.util.ArrayList<>());
        return "exam/records";
    }
}