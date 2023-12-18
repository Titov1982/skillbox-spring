package ru.tai._10_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty("app.entity-generator.enabled")
public class EntityGenerator {

    private final TestService testService;

    @EventListener(ApplicationStartedEvent.class)
    public void startEntityGenerator() {
        log.debug("EntityGenerator->startEntityGenerator");

        User user1 = testService.createUser("User1", "User111");
        Category category = testService.createCategory("Категория 1");
        List<News> newsListUser1 = testService.createNewsListAndCategory(user1.getId(), category);

        User user2 = testService.createUser("User2", "User222");
        Comment comment1 = testService.createComment(user2.getId(), newsListUser1.get(0).getId(), "Комментарий 1");

        News news1 = testService.createNews(user1.getId(), "Новость новость 1", "Текст новости новости 1", "Категория 2");
        News news2 = testService.createNews(user2.getId(), "Новость новость 2", "Текст новости новости 2", "Категория 1");
        News news3 = testService.createNews(user1.getId(), "Новость новость 3", "Текст новости новости 3", "Категория 2");


        Comment comment2 = testService.createComment(user1.getId(), news2.getId(), "Комментарий 2");
        Comment comment3 = testService.createComment(user2.getId(), news2.getId(), "Комментарий 3");
        Comment comment4 = testService.createComment(user1.getId(), news1.getId(), "Комментарий 4");

        List<News> newsPage = testService.newsFindAll(0, 5);
        List<News> newsPage2 = testService.newsFindAll(1, 5);

        News existedNews = testService.newsFindById(newsPage.get(0).getId());
        List<Comment> commentList = testService.commentFindByAllForNews(existedNews.getId());



//        User deletedUser2 = testService.deleteUser(2L);
//        testService.deleteNews(news1.getId());
//
//        Comment comment5 = testService.createComment(user1.getId(), newsListUser1.get(0).getId(), "Комментарий 5");
//        testService.deleteComment(comment5.getId());

    }
}
