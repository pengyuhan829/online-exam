package com.example.online_exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "appeal")
@Data
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "exam_record_id")
    private Long examRecordId;      // 考试记录ID
    
    @Column(name = "question_id")
    private Long questionId;        // 题目ID
    
    @Column(name = "user_id")
    private Long userId;            // 申诉人ID
    
    @Column(columnDefinition = "TEXT")
    private String content;         // 申诉内容
    
    @Column(columnDefinition = "TEXT")
    private String reply;           // 回复内容
    
    private String status = "PENDING"; // PENDING, REPLIED, REJECTED
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "replied_at")
    private LocalDateTime repliedAt; // 回复时间
    
    @Column(name = "replied_by")
    private Long repliedBy;          // 回复人ID
}
