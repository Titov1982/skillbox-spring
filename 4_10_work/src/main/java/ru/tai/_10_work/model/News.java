package ru.tai._10_work.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    private Long id;
    private String title;
    private String description;

    private Category category;

    private User user;

    private List<Comment> comments;
}
