package ru.tai._10_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tai._10_work.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
