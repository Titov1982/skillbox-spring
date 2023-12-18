package ru.tai._10_work.service;

import ru.tai._10_work.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();
    Comment findById(Long id);
    List<Comment> findAllByUserId(Long userId);
    List<Comment> findAllByNewsId(Long newsId);
    Comment save(Comment comment);
    Comment update(Comment comment);
    Comment deleteById(Long id);
    Long countAllByNewsId(Long newsId);
    Comment deleteByIdAndUserId(Long id, Long userId);
}
