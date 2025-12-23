package com.example.online_exam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "knowledge_point")
public class KnowledgePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 知识点名称

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject; // 所属学科

    // 无参构造器（JPA 要求）
    public KnowledgePoint() {}

    // Getter / Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
}
