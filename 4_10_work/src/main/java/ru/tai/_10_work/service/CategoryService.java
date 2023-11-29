package ru.tai._10_work.service;

import ru.tai._10_work.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Category category);
    Category deleteById(Long id);
}
