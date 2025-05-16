package com.artstudio.backend.controller;

import com.artstudio.backend.model.Work;
import com.artstudio.backend.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/works")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("/upload")
    public ResponseEntity<Work> uploadWork(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        Work work = workService.uploadWork(userId, title, description, file);
        return ResponseEntity.ok(work);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Work>> getUserWorks(@PathVariable Long userId) {
        List<Work> works = workService.getUserWorks(userId);
        return ResponseEntity.ok(works);
    }

    @DeleteMapping("/{workId}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long workId) {
        workService.deleteWork(workId);
        return ResponseEntity.noContent().build();
    }
}
