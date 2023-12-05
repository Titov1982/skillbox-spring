package ru.tai._10_work.web.model;

import lombok.Data;

@Data
public class UpsertNewsRequest {

    private Long userId;
    private String title;
    private String description;
    private String categoryName;
}
