package ru.tai._10_work.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.service.NewsService;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserControlUpdateCommentAspect {

    private final CommentService commentService;

    @Around("@annotation(UserControlUpdateComment)")
    public Object userControlUpdateCommentAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        Comment updateComment = (Comment) args[0];
        Long updateUserId = updateComment.getUser().getId();
        Long updateCommentId = updateComment.getId();
        Comment comment = commentService.findById(updateCommentId);
        if (comment.getUser().getId() == updateUserId) {
            return proceedingJoinPoint.proceed(args);
        }

        return null;
    }
}
