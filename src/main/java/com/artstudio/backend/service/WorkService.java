package com.artstudio.backend.service;

import com.artstudio.backend.model.User;
import com.artstudio.backend.model.Work;
import com.artstudio.backend.repository.UserRepository;
import com.artstudio.backend.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
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

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get("uploads/works/");
        Files.createDirectories(uploadDir);
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

    public void deleteWork(Long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));
        work.setDeleted(true);
        workRepository.save(work);
    }
}
