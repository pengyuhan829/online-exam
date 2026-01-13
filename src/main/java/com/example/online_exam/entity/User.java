package com.example.online_exam.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // 数据库表名叫 users (避免用 user 关键字)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 账号

    @Column(nullable = false)
    private String password; // 密码 (加密后的)

    private String role;     // 角色：TEACHER, STUDENT, ADMIN

    private String realName; // 真实姓名
}