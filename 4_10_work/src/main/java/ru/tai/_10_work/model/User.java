package ru.tai._10_work.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;

    private List<News> newsList = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
