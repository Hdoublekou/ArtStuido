package com.artstudio.backend.dto;

import lombok.Data;

@Data
public class WorkDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Long userId;
    private int likeCount;
    private boolean likedByCurrentUser;

}
