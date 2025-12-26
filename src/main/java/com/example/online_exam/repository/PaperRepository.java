package com.example.online_exam.repository;

import com.example.online_exam.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaperRepository extends JpaRepository<Paper, Long> {
    List<Paper> findByTeacherId(Long teacherId);
    List<Paper> findByIsActiveTrue();
}
