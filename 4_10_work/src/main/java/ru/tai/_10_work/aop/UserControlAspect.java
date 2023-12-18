package ru.tai._10_work.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.service.NewsService;
import ru.tai._10_work.service.impl.CommentServiceImpl;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserControlAspect {

    private final CommentService commentService;
    private final NewsService newsService;

    @Around("@annotation(UserControl)")
    public Object userControlAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

//        Class<?> argumentClazz = args[0].getClass();
//        String argumentClassName = argumentClazz.getSimpleName();
//        argumentClazz.newInstance();

        Signature signature = proceedingJoinPoint.getSignature();
        String methodName = signature.getName();

        SourceLocation sourceLocation = proceedingJoinPoint.getSourceLocation();
        String parentClassName = sourceLocation.getWithinType().getSimpleName();
        //        Class<?> parentClass = sourceLocation.getWithinType();



        if (parentClassName.equals("CommentServiceImpl")) {

            if (methodName.equals("update")) {
                Comment updateComment = (Comment) args[0];
                Long updateUserId = updateComment.getUser().getId();
                Long updateCommentId = updateComment.getId();
                Comment comment = commentService.findById(updateCommentId);
                if (comment.getUser().getId() == updateUserId) {
                    return proceedingJoinPoint.proceed(args);
                }
            }

            if (methodName.equals("deleteByIdAndUserId")) {
                Long commentId = (Long) args[0];
                Long userId = (Long) args[1];
                Comment deletingComment = commentService.findById(commentId);
                if (deletingComment.getUser().getId() == userId) {
                    return proceedingJoinPoint.proceed(args);
                }
            }
        }

        if (parentClassName.equals("NewsServiceImpl")) {

            if (methodName.equals("update")) {
                News updatingNews = (News) args[0];
                Long updatingUserId = updatingNews.getUser().getId();
                Long updatingNewsId = updatingNews.getId();
                News news = newsService.findById(updatingNewsId);
                if (news.getUser().getId() == updatingUserId) {
                    return proceedingJoinPoint.proceed(args);
                }
            }

            if (methodName.equals("deleteByIdAndUserId")) {
                Long newsId = (Long) args[0];
                Long userId = (Long) args[1];
                News deletingNews = newsService.findById(newsId);
                if (deletingNews.getUser().getId() == userId) {
                    return proceedingJoinPoint.proceed(args);
                }
            }
        }

        return null;
    }
}
