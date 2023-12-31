package ru.tai._10_work.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tai._10_work.aop.UserControl;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.model.User;
import ru.tai._10_work.repository.CommentReporitory;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.service.NewsService;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.utils.BeanUtils;

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
        return commentReporitory.findById(id).orElse(null);
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
    public List<Comment> findAllByNewsId(Long newsId) {
        log.debug("CommentServiceImpl->findAllByNewsId newsId= {}", newsId);
        return commentReporitory.findAllByNewsId(newsId);
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
    @UserControl
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
    @UserControl
    public Comment deleteById(Long id) {
        log.debug("CommentServiceImpl->deleteById id= {}", id);
        Comment comment = commentReporitory.findById(id).orElse(null);
        if (comment != null){
            commentReporitory.deleteById(id);
            return comment;
        }
        return null;
    }

    @Override
    @UserControl
    @Transactional
    public Comment deleteByIdAndUserId(Long id, Long userId) {
        log.debug("CommentServiceImpl->deleteByIdAndUserId id= {}, userId= {}", id, userId);
        Comment deletedComment = commentReporitory.findById(id).orElse(null);
        commentReporitory.deleteByIdAndUserId(id, userId);
        return deletedComment;
    }

    @Override
    public Long countAllByNewsId(Long newsId) {
        log.debug("CommentServiceImpl->countAllByNewsId newsId= {}", newsId);
        return commentReporitory.countAllByNewsId(newsId);
    }
}
