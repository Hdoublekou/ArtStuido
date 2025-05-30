package com.artstudio.backend.controller;

import com.artstudio.backend.dto.WorkDto;
import com.artstudio.backend.model.Work;
import com.artstudio.backend.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<WorkDto> uploadWork(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        Work work = workService.uploadWork(userId, title, description, file);
        WorkDto dto = workService.toDto(work);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkDto>> getUserWorks(@PathVariable Long userId) {
        List<Work> works = workService.getUserWorks(userId);
        List<WorkDto> dtos = works.stream()
                .map(workService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/my")
    public ResponseEntity<List<WorkDto>> getMyWorks(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Work> workPage = workService.getUserWorksPaged(userId, page, size);
        List<WorkDto> dtos = workPage.getContent().stream()
                .map(workService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{workId}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long workId) {
        workService.deleteWork(workId);
        return ResponseEntity.noContent().build();
    }

    // ------- 点赞相关 --------

    @PostMapping("/{workId}/like")
    public ResponseEntity<Void> likeWork(@PathVariable Long workId, @RequestParam Long userId) {
        workService.likeWork(workId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{workId}/unlike")
    public ResponseEntity<Void> unlikeWork(@PathVariable Long workId, @RequestParam Long userId) {
        workService.unlikeWork(workId, userId);
        return ResponseEntity.ok().build();
    }

    // ------- 所有作品，按点赞数排序 --------

    @GetMapping("/all")
    public ResponseEntity<List<WorkDto>> getAllWorks(@RequestParam(required = false) Long userId) {
        List<Work> works = workService.getAllWorksOrderByLikesDesc();
        List<WorkDto> dtos = works.stream()
            .map(work -> workService.toDto(work, userId))
            .toList();
        return ResponseEntity.ok(dtos);
    }

}
