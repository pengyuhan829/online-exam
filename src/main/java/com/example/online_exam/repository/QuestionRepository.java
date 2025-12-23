package com.example.online_exam.repository;

import com.example.online_exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
     List<Question> findByContentContaining(String keyword);
}
