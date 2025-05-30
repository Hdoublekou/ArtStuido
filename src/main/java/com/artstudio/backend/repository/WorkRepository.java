package com.artstudio.backend.repository;

import com.artstudio.backend.model.Work;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Page<Work> findByUserIdAndDeletedFalse(Long userId, Pageable pageable);
    List<Work> findByUserIdAndDeletedFalse(Long userId); // 
    List<Work> findByDeletedFalseOrderByCreatedAtDesc();
    
    @Query(value = """
            SELECT w.* FROM works w
            LEFT JOIN (
                SELECT work_id, COUNT(*) AS like_count
                FROM work_likes
                GROUP BY work_id
            ) l ON w.id = l.work_id
            ORDER BY COALESCE(l.like_count, 0) DESC
            """, nativeQuery = true)
        List<Work> findAllOrderByLikeCountDesc();
    }