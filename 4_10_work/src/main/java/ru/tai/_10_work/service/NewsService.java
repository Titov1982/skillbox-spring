package ru.tai._10_work.service;

import org.springframework.data.domain.Page;
import ru.tai._10_work.model.News;

import java.util.List;

public interface NewsService {
    List<News> findAll();
    Page<News> findAll(int pageNumber, int PageSize);
    News findById(Long id);
    News save(News news);
    News update(News news);
    News deleteById(Long id);
}
