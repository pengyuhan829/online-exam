package com.example.online_exam.repository;

import com.example.online_exam.entity.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {
    
    // 根据考试记录ID查找所有答题记录
    List<ExamAnswer> findByExamRecordId(Long examRecordId);
    
    // 根据考试记录ID和题目ID查找答题记录
    ExamAnswer findByExamRecordIdAndQuestionId(Long examRecordId, Long questionId);
    
    // 查找需要批改的主观题（未批改的）
    @Query("SELECT ea FROM ExamAnswer ea WHERE ea.examRecordId = :examRecordId AND ea.isCorrect IS NULL")
    List<ExamAnswer> findSubjectiveQuestionsToGrade(@Param("examRecordId") Long examRecordId);
    
    // 统计某次考试的客观题总分
    @Query("SELECT COALESCE(SUM(ea.score), 0) FROM ExamAnswer ea WHERE ea.examRecordId = :examRecordId AND ea.isCorrect IS NOT NULL")
    Double sumObjectiveScoreByExamRecordId(@Param("examRecordId") Long examRecordId);
    
    // 统计某次考试的主观题总分
    @Query("SELECT COALESCE(SUM(ea.score), 0) FROM ExamAnswer ea WHERE ea.examRecordId = :examRecordId AND ea.isCorrect IS NULL")
    Double sumSubjectiveScoreByExamRecordId(@Param("examRecordId") Long examRecordId);
}