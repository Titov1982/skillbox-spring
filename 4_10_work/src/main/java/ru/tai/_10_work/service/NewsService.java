package ru.tai._10_work.service;

import ru.tai._10_work.model.News;

import java.util.List;

public interface NewsService {
    List<News> findAll();
    News findById(Long id);
    News save(News news);
    News update(News news);
    News deleteById(Long id);
}
