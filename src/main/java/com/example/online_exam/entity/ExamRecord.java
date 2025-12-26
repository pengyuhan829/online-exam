package com.example.online_exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_record")
@Data
public class ExamRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "paper_id")
    private Long paperId;
    
    @Column(name = "student_id")
    private Long studentId = 1L; // 默认学生ID
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    private Integer score = 0;         // 得分
    
    private String status = "ONGOING"; // ONGOING, SUBMITTED, REVIEWED
    
    @Column(columnDefinition = "TEXT")
    private String answers; // 学生答案，JSON格式存储
}
