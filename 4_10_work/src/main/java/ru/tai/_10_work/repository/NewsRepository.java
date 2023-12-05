package ru.tai._10_work.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tai._10_work.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findAll(Pageable pageable);

}
