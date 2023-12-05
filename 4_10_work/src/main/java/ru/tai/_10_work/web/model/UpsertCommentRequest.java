package ru.tai._10_work.web.model;

import lombok.Data;

@Data
public class UpsertCommentRequest {

    private Long userId;
    private Long newsId;
    private String comment;
}
