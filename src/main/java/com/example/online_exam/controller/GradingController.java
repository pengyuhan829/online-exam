package com.example.online_exam.controller;

import com.example.online_exam.service.GradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grading")
@RequiredArgsConstructor
public class GradingController {
    
    private final GradingService gradingService;
    
    /**
     * 判分管理首页
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "判分管理后台");
        model.addAttribute("message", "欢迎使用判分管理系统");
        return "grading/dashboard";
    }
    
    /**
     * 自动判分页面
     */
    @GetMapping("/auto-grade")
    public String autoGradePage(Model model) {
        model.addAttribute("title", "客观题自动判分");
        return "grading/auto-grade";
    }
    
    /**
     * 执行自动判分
     */
    @PostMapping("/execute-auto-grade")
    public String executeAutoGrade(@RequestParam Long examRecordId, Model model) {
        try {
            gradingService.autoGradeObjectiveQuestions(examRecordId);
            model.addAttribute("success", "自动判分完成！考试记录ID: " + examRecordId);
        } catch (Exception e) {
            model.addAttribute("error", "判分失败: " + e.getMessage());
        }
        return "grading/auto-grade";
    }
    
    /**
     * 申诉管理页面
     */
    @GetMapping("/appeal")
    public String appealPage(Model model) {
        model.addAttribute("title", "申诉管理");
        return "grading/appeal";
    }
    
    /**
     * 成绩报表页面
     */
    @GetMapping("/report")
    public String reportPage(Model model) {
        model.addAttribute("title", "成绩报表");
        return "grading/report";
    }
}
