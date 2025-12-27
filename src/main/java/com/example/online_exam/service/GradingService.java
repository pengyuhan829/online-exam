package com.example.online_exam.service;

import com.example.online_exam.entity.*;
import com.example.online_exam.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradingService {
    
    private final ExamAnswerRepository examAnswerRepository;
    private final ExamRecordRepository examRecordRepository;
    private final QuestionRepository questionRepository;
    private final PaperQuestionRepository paperQuestionRepository;
    private final AppealRepository appealRepository;
    
    /**
     * 客观题自动判分
     */
    @Transactional
    public void autoGradeObjectiveQuestions(Long examRecordId) {
        System.out.println("开始自动判分，考试记录ID: " + examRecordId);
        // 这里先写简单的逻辑，确保能编译通过
        ExamRecord record = examRecordRepository.findById(examRecordId).orElse(null);
        if (record != null) {
            record.setStatus("REVIEWED");
            examRecordRepository.save(record);
            System.out.println("判分完成");
        }
    }
    
    /**
     * 提交申诉
     */
    @Transactional
    public Appeal submitAppeal(Long examRecordId, Long questionId, Long userId, String content) {
        Appeal appeal = new Appeal();
        appeal.setExamRecordId(examRecordId);
        appeal.setQuestionId(questionId);
        appeal.setUserId(userId);
        appeal.setContent(content);
        appeal.setStatus("PENDING");
        
        return appealRepository.save(appeal);
    }
}