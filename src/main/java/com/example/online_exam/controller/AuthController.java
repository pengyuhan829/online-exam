package com.example.online_exam.controller;

import com.example.online_exam.entity.User;
import com.example.online_exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. 只有没登录的人才能看登录页
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 对应 templates/login.html
    }

    // 2. 注册页面
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // 对应 templates/register.html
    }

    // 3. 处理注册逻辑
    @PostMapping("/register")
    public String doRegister(User user, Model model) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "用户名已存在！");
            return "register";
        }

        // 密码加密存储（重要！）
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 默认设置为学生角色，如果需要老师，手动改数据库或者这里做个判断
        if (user.getRole() == null) {
            user.setRole("STUDENT");
        }
        user.setRealName(user.getUsername()); // 默认真实姓名为用户名

        userRepository.save(user);
        return "redirect:/login?registered"; // 注册成功跳回登录页
    }
}