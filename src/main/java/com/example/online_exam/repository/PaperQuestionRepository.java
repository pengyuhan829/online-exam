package com.example.online_exam.repository;

import com.example.online_exam.entity.PaperQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface PaperQuestionRepository extends JpaRepository<PaperQuestion, Long> {
    List<PaperQuestion> findByPaperId(Long paperId);
    
    @Transactional
    @Modifying
    void deleteByPaperId(Long paperId);
}
