package ru.tai._10_work.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;
    private String name;

    private List<News> newsList = new ArrayList<>();
}