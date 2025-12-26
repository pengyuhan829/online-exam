package com.example.online_exam.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "paper_question")
@Data
public class PaperQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "paper_id")
    private Long paperId;
    
    @Column(name = "question_id")
    private Long questionId;
    
    @Column(name = "order_num")
    private Integer orderNum = 1;      // 题目顺序
    
    private Integer score = 5;         // 本题分值
}
