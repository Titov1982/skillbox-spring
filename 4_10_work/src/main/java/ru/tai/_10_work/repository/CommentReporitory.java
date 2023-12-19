package ru.tai._10_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tai._10_work.model.Comment;

import java.util.List;

@Repository
public interface CommentReporitory extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUserId(Long userId);
    List<Comment> findAllByNewsId(Long newsId);
    Long countAllByNewsId(Long newsId);
    void deleteByIdAndUserId(Long id, Long userId);
}
