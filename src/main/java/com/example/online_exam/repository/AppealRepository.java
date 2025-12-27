package com.example.online_exam.repository;

import com.example.online_exam.entity.Appeal;  // ✅ 正确的import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {
    
    // 根据用户ID查找申诉
    List<Appeal> findByUserId(Long userId);
    
    // 根据考试记录ID查找申诉
    List<Appeal> findByExamRecordId(Long examRecordId);
    
    // 根据状态查找申诉 ✅ 修正：应该是findByStatus
    List<Appeal> findByStatus(String status);
    
    // 查找待处理的申诉（按创建时间倒序）
    List<Appeal> findByStatusOrderByCreatedAtDesc(String status);
    
    // 查找某次考试中某道题的申诉
    Appeal findByExamRecordIdAndQuestionId(Long examRecordId, Long questionId);
    
    // 统计未处理的申诉数量
    @Query("SELECT COUNT(a) FROM Appeal a WHERE a.status = 'PENDING'")
    Long countPendingAppeals();
}