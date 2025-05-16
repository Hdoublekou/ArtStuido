package com.artstudio.backend.repository;

import com.artstudio.backend.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByUserIdAndDeletedFalse(Long userId);
}
