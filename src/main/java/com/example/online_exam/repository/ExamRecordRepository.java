package com.example.online_exam.repository;

import com.example.online_exam.entity.ExamRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
    
    // 根据试卷ID查找考试记录
    List<ExamRecord> findByPaperId(Long paperId);
    
    // 根据学生ID查找考试记录
    List<ExamRecord> findByStudentId(Long studentId);
    
    // 根据状态查找考试记录
    List<ExamRecord> findByStatus(String status);
    
    // 查找某个学生的某次考试记录
    ExamRecord findByPaperIdAndStudentId(Long paperId, Long studentId);
}
