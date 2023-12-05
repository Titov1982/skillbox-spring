package ru.tai._10_work.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.service.NewsService;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.web.model.UpsertCommentRequest;

import java.time.Instant;

@Component
public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setCreateAt(Instant.now());
        comment.setUser(userService.findById(request.getUserId()));
        comment.setNews(newsService.findById(request.getNewsId()));
        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }
}
