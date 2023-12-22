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
public class UserControlDeleteCommentAspect {

    private final CommentService commentService;

    @Around("@annotation(UserControlDeleteComment)")
    public Object userControlDeleteCommentAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        Long commentId = (Long) args[0];
        Long userId = (Long) args[1];
        Comment deletingComment = commentService.findById(commentId);
        if (deletingComment.getUser().getId() == userId) {
            return proceedingJoinPoint.proceed(args);
        }

        return null;
    }
}
