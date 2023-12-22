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
public class UserControlUpdateNewsAspect {

    private final NewsService newsService;

    @Around("@annotation(UserControlUpdateNews)")
    public Object userControlUpdateNewsAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        News updatingNews = (News) args[0];
        Long updatingUserId = updatingNews.getUser().getId();
        Long updatingNewsId = updatingNews.getId();
        News news = newsService.findById(updatingNewsId);
        if (news.getUser().getId() == updatingUserId) {
            return proceedingJoinPoint.proceed(args);
        }

        return null;
    }
}
