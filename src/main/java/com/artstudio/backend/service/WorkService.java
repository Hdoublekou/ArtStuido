package com.artstudio.backend.service;

import com.artstudio.backend.dto.WorkDto;
import com.artstudio.backend.model.User;
import com.artstudio.backend.model.Work;
import com.artstudio.backend.repository.UserRepository;
import com.artstudio.backend.repository.WorkRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;
    private final UserRepository userRepository;

    public Work uploadWork(Long userId, String title, String description, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String rootDir = System.getProperty("user.dir");
        Path uploadDir = Paths.get(rootDir, "uploads", "works");
        Files.createDirectories(uploadDir);

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);
        file.transferTo(filePath.toFile());

        Work work = new Work();
        work.setTitle(title);
        work.setDescription(description);
        work.setImageUrl("/uploads/works/" + filename);
        work.setUser(user);

        return workRepository.save(work);
    }

    public List<Work> getUserWorks(Long userId) {
        return workRepository.findByUserIdAndDeletedFalse(userId);
    }

    public Page<Work> getUserWorksPaged(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return workRepository.findByUserIdAndDeletedFalse(userId, pageable);
    }

    public void deleteWork(Long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));
        work.setDeleted(true);
        workRepository.save(work);
    }
    
    public WorkDto toDto(Work work, Long userId) {
        WorkDto dto = new WorkDto();
        dto.setId(work.getId());
        dto.setTitle(work.getTitle());
        dto.setDescription(work.getDescription());
        dto.setImageUrl(work.getImageUrl());
        dto.setUserId(work.getUser() != null ? work.getUser().getId() : null);
        dto.setLikeCount(getLikeCount(work.getId()));
        dto.setLikedByCurrentUser(userId != null && isLikedByCurrentUser(work.getId(), userId));
        return dto;
    }

    public WorkDto toDto(Work work) {
        WorkDto dto = new WorkDto();
        dto.setId(work.getId());
        dto.setTitle(work.getTitle());
        dto.setDescription(work.getDescription());
        dto.setImageUrl(work.getImageUrl());
        dto.setUserId(work.getUser() != null ? work.getUser().getId() : null);
        return dto;
    }
    
    public List<Work> getAllWorks() {
        return workRepository.findByDeletedFalseOrderByCreatedAtDesc();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getLikeCount(Long workId) {
        String sql = "SELECT COUNT(*) FROM work_likes WHERE work_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, workId);
    }

    public boolean isLikedByCurrentUser(Long workId, Long userId) {
        String sql = "SELECT COUNT(*) FROM work_likes WHERE work_id = ? AND user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, workId, userId);
        return count != null && count > 0;
    }

    public void likeWork(Long workId, Long userId) {
        String sql = "INSERT INTO work_likes (work_id, user_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(sql, workId, userId);
    }

    public void unlikeWork(Long workId, Long userId) {
        String sql = "DELETE FROM work_likes WHERE work_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, workId, userId);
    }
    
    public List<Work> getAllWorksOrderByLikesDesc() {
        return workRepository.findAllOrderByLikeCountDesc();
    }


}
