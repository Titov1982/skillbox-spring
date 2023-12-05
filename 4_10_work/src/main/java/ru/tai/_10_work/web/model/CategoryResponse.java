package ru.tai._10_work.web.model;

import lombok.Data;
import ru.tai._10_work.model.News;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private List<News> newsList = new ArrayList<>();
}
