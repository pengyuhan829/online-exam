package com.example.online_exam.repository;

import com.example.online_exam.entity.PaperQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaperQuestionRepository extends JpaRepository<PaperQuestion, Long> {
    List<PaperQuestion> findByPaperId(Long paperId);
    void deleteByPaperId(Long paperId);
}
