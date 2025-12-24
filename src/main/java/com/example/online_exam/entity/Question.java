package com.example.online_exam.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type = "SINGLE";        // SINGLE, MULTIPLE, FILL, SUBJECTIVE
    
    @Column(columnDefinition = "TEXT") // 使用文本类型存储大段内容
    private String content;     // 题干

    // 选项字段：用于存储选择题的选项。使用JSON格式字符串，例如：`["选项A", "选项B", "选项C", "选项D"]`
    // 对于填空题和主观题，此字段可为空
    @Column(columnDefinition = "TEXT")
    private String options;     // JSON 字符串，如 ["A.苹果","B.香蕉"]
    
    // 答案字段：根据题型，答案形式不同。
    // 单选题: "A", 多选题: "A,B", 填空题: "填空答案", 主观题: "参考要点"
    private String answer;      // 答案

    private Integer difficulty = 1; // 1~5,1最简单，5最难
   
     // 👇 添加默认值，防止 null
    private Long subjectId = 1L;  // 默认关联到 subject_id = 1 的科目

    // 知识点ID (关联已创建的knowledge_point表)
    private Long knowledgePointId;

    
    // 无参构造器，JPA需要
    public Question() {
    }

}
