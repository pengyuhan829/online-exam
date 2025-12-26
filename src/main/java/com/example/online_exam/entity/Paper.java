package com.example.online_exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "paper")
@Data
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    
    @Column(name = "paper_type")
    private String paperType = "FIXED"; // FIXED:固定卷, RULE:规则卷
    
    @Column(name = "total_score")
    private Integer totalScore = 100;
    
    private Integer duration = 120; // 分钟
    
    @Column(name = "teacher_id")
    private Long teacherId = 1L; // 默认教师ID
    
    @Column(name = "created_time")
    private LocalDateTime createdTime = LocalDateTime.now();
    
    @Column(name = "is_active")
    private Boolean isActive = true;
}
