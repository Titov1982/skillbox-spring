package ru.tai._10_work.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsResponse {

    private Long id;
    private String title;
    private String description;
    private Long countComment;
    private List<CommentResponse> comments = new ArrayList<>();
}
