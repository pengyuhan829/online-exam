package com.example.online_exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_answer")
@Data
public class ExamAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "exam_record_id")
    private Long examRecordId;      // 关联的考试记录ID
    
    @Column(name = "question_id")
    private Long questionId;        // 题目ID
    
    @Column(name = "user_answer", columnDefinition = "TEXT")
    private String userAnswer;      // 学生答案
    
    @Column(name = "correct_answer", columnDefinition = "TEXT")
    private String correctAnswer;   // 标准答案
    
    private Double score = 0.0;     // 该题得分
    
    @Column(name = "max_score")
    private Double maxScore;        // 该题满分
    
    @Column(name = "is_correct")
    private Boolean isCorrect;      // 是否正确(仅客观题)
    
    @Column(name = "graded_by")
    private Long gradedBy;          // 批改人ID(主观题)
    
    @Column(name = "graded_at")
    private LocalDateTime gradedAt; // 批改时间
}