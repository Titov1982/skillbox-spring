package ru.tai._10_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.model.User;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty("app.entity-generator.enabled")
@Transactional
public class EntityGenerator {

    private final UserService userService;
    private final CategoryService categoryService;
    private final NewsService newsService;
    private final CommentService commentService;

    @EventListener(ApplicationStartedEvent.class)
    public void startEntityGenerator() {
        log.debug("EntityGenerator->startEntityGenerator");
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("user111");
        user1 = userService.save(user1);

        Category category1 = new Category();
        category1.setName("Категоря 1");
        category1 = categoryService.save(category1);

        News news1 = new News();
        Category existedCategory1 = categoryService.findById(category1.getId());
        news1.setCategory(existedCategory1);
        news1.setTitle("Заголовок новости 1");
        news1.setDescription("Текст новости 1");
        news1.setCreateAt(Instant.now());
        User existedUser1 = userService.findById(user1.getId());
        news1.setUser(existedUser1);

        category1.addNews(news1);
        category1 = categoryService.save(category1);
        user1.addNews(news1);
        user1 = userService.save(user1);

        newsService.save(news1);

//--------------------------------------------------------------------------------

        existedUser1 = userService.findById(user1.getId());
        News existedNews1 = existedUser1.getNewsList().stream().filter((id) -> news1.getId() == 1).toList().get(0);

        Comment comment = new Comment();
        comment.setUser(existedUser1);
        comment.setNews(existedNews1);
        comment.setCreateAt(Instant.now());
        comment.setComment("Комментарий 1");
        commentService.save(comment);
        existedNews1.addComment(comment);
        newsService.save(existedNews1);
        existedUser1.addComment(comment);
        userService.save(existedUser1);

    }

}
