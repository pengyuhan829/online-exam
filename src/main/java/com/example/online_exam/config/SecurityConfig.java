package com.example.online_exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 配置哪些页面需要登录，哪些不需要
                .authorizeHttpRequests(auth -> auth
                        // 公开访问的页面
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**", "/error", "/diagnostic").permitAll()
                        
                        // 管理员可以访问所有页面
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        
                        // 老师可以访问：题目管理、试卷管理、判分管理
                        .requestMatchers("/paper/**", "/grading/**").hasAnyRole("TEACHER", "ADMIN")
                        
                        // 题库所有登录用户都可以查看（学生提交试卷后可以查看）
                        .requestMatchers("/questions/**").authenticated()
                        
                        // 学生可以访问：考试相关
                        .requestMatchers("/exam/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        
                        // 首页所有登录用户都可以访问
                        .requestMatchers("/").authenticated()
                        
                        // 其他所有请求都需要登录
                        .anyRequest().authenticated()
                )
                // 2. 配置登录功能
                .formLogin(form -> form
                        .loginPage("/login")        // 自定义登录页面的路径
                        .loginProcessingUrl("/login") // 提交表单的路径
                        .defaultSuccessUrl("/", true) // 登录成功后跳到首页
                        .failureUrl("/login?error=true") // 登录失败跳转
                        .permitAll()
                )
                // 3. 配置退出功能
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // 4. 禁用CSRF（开发阶段，生产环境应该启用）
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // 密码加密器（存入数据库的密码必须是加密的）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
