package ru.tai._10_work.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tai._10_work.exception.EntityNotFoundException;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.model.User;
import ru.tai._10_work.repository.CommentReporitory;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.service.NewsService;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.utils.BeanUtils;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentReporitory commentReporitory;
    private final UserService userService;
    private final NewsService newsService;
    @Override
    public List<Comment> findAll() {
        log.debug("CommentServiceImpl->findAll");
        return commentReporitory.findAll();
    }

    @Override
    public Comment findById(Long id) {
        log.debug("CommentServiceImpl->findById id= {}", id);
        Comment comment = commentReporitory.findById(id).orElse(null);
        if (comment != null){
            return comment;
        }
        throw new EntityNotFoundException(MessageFormat.format("Комментарий с ID= {0} не найден!", id));
    }

    @Override
    public List<Comment> findAllByUserId(Long userId) {
        log.debug("CommentServiceImpl->findAllByUserId userId= {}", userId);
        List<Comment> comments = commentReporitory.findAllByUserId(userId);
        if (comments != null) {
            return comments;
        }
        return new ArrayList<>();
    }

    @Override
    public Comment save(Comment comment) {
        log.debug("CommentServiceImpl->save comment= {}", comment);
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());
        comment.setCreateAt(Instant.now());
        comment.setUser(user);
        comment.setNews(news);
        return commentReporitory.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        log.debug("CommentServiceImpl->update comment= {}", comment);
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());
        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);
        existedComment.setUser(user);
        existedComment.setNews(news);
        return commentReporitory.save(existedComment);
    }

    @Override
    public Comment deleteById(Long id) {
        log.debug("CommentServiceImpl->deleteById id= {}", id);
        Comment comment = commentReporitory.findById(id).orElse(null);
        if (comment != null){
            commentReporitory.deleteById(id);
            return comment;
        }
        throw new EntityNotFoundException(MessageFormat.format("Комментарий с ID= {0} не найден!", id));
    }
}
