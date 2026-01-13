package com.example.online_exam.controller;

import com.example.online_exam.entity.Question;
import com.example.online_exam.repository.QuestionRepository;
import com.example.online_exam.repository.SubjectRepository;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository; // 新增

    public QuestionController(QuestionRepository questionRepository, SubjectRepository subjectRepository) {
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository; // 注入
    }

    @GetMapping
    public String list(Model model, 
                   @RequestParam(required = false) String search,
                   @RequestParam(required = false) Long subjectId,
                   @RequestParam(required = false) String type,
                   @RequestParam(required = false) Integer difficulty) {
        // 构造查询条件 - 先获取所有题目
        List<Question> questions = questionRepository.findAll();

        // 按搜索关键词过滤
        if (search != null && !search.trim().isEmpty()) {
            final String searchLower = search.toLowerCase();
            questions = questions.stream()
                    .filter(q -> q.getContent() != null && q.getContent().toLowerCase().contains(searchLower))
                    .toList();
        }

        // 按科目过滤
        if (subjectId != null) {
            questions = questions.stream()
                    .filter(q -> q.getSubjectId() != null && q.getSubjectId().equals(subjectId))
                    .toList();
        }

        // 按题型过滤
        if (type != null && !type.isEmpty()) {
            questions = questions.stream()
                    .filter(q -> q.getType() != null && q.getType().equals(type))
                    .toList();
        }

        // 按难度过滤
        if (difficulty != null) {
            questions = questions.stream()
                    .filter(q -> q.getDifficulty() != null && q.getDifficulty().equals(difficulty))
                    .toList();
        }

        // 传参给页面
        model.addAttribute("questions", questions);
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("search", search);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("type", type);
        model.addAttribute("difficulty", difficulty);
        return "question/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("question", new Question());
        return "question/form";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Question question) {
        if (question.getContent() == null || question.getContent().trim().isEmpty()) {
        return "question/form"; // 提示填写内容
    }
    
    if (question.getSubjectId() == null) {
        question.setSubjectId(1L); // 默认科目 ID
    }


        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) {
            return "redirect:/questions";
        }
        model.addAttribute("question", question);
        model.addAttribute("subjects", subjectRepository.findAll());
        return "question/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Question question) {
        Question existing = questionRepository.findById(id).orElse(null);
        if (existing == null) {
            return "redirect:/questions";
        }

        existing.setContent(question.getContent());
        existing.setType(question.getType());
        existing.setDifficulty(question.getDifficulty());
        existing.setAnswer(question.getAnswer());
        existing.setOptions(question.getOptions());

        // 如果没有设置 subject，保持原值
        if (question.getSubjectId() != null) {
            existing.setSubjectId(question.getSubjectId());
        }

        questionRepository.save(existing);
        return "redirect:/questions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/questions";
    }
}
