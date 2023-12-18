package ru.tai._10_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.model.User;
import ru.tai._10_work.repository.CategoryRepository;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final NewsService newsService;
    private final CommentService commentService;
    private final CategoryRepository categoryRepository;

    @Transactional
    public User createUser(String name, String pass) {
        log.debug(MessageFormat.format("TestService->createUser user: username={0} password={1}", name, pass));
        User user = new User();
        user.setUsername(name);
        user.setPassword(pass);
        user = userService.save(user);
        return user;
    }

    @Transactional
    public List<News> createNewsListAndCategory(Long userId, Category category) {
        log.debug("TestService->createNewsListAndCategory");

        User existedUser = userService.findById(userId);
        Category existedCategory = categoryService.findById(category.getId());

        List<News> newsList = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            News news = new News();
            news.setCategory(existedCategory);
            news.setTitle("Заголовок новости " + i);
            news.setDescription("Текст новости " + i);
            news.setCreateAt(Instant.now());
            news.setUser(existedUser);

            existedUser.getNewsList().add(news);
            category.getNewsList().add(news);
            news = newsService.save(news);
            newsList.add(news);
        }
        return newsList;
    }

    @Transactional
    public Comment createComment(Long userId, Long newsId, String commentText) {
        log.debug("TestService->createCommentUser2ToNews1User1");

        Comment comment1 = new Comment();
        comment1.setComment(commentText);
        comment1.setCreateAt(Instant.now());
        User existedUser = userService.findById(userId);
        comment1.setUser(existedUser);
        News news =  newsService.findById(newsId);
        comment1.setNews(news);
        comment1 = commentService.save(comment1);
        return comment1;
    }

//    @Transactional
    public News createNews(Long userId, String newsTitle, String newsDesc, String categoryName) {
        log.debug("TestService->createOneNews");

        News news = new News();
        news.setCreateAt(Instant.now());
        news.setTitle(newsTitle);
        news.setDescription(newsDesc);
        User existedUser = userService.findById(userId);
        news.setUser(existedUser);
        Category category = categoryService.findByName(categoryName);
        if (category == null){
            category = createCategory(categoryName);
        }
        news.setCategory(category);
        news =  newsService.save(news);
        return news;
    }

    @Transactional
    public Category createCategory(String name) {
        log.debug(MessageFormat.format("TestService->createCategory name={0}", name));
        Category category = new Category();
        category.setName(name);
        category = categoryService.save(category);
        return category;
    }

    @Transactional
    public User deleteUser(Long userId) {
        return userService.deleteById(userId);
    }

    @Transactional
    public News deleteNews(Long newsId) {
        return newsService.deleteById(newsId);
    }

    @Transactional
    public Comment deleteComment(Long commentId) {
        return commentService.deleteById(commentId);
    }

    @Transactional
    public List<News> newsFindAll(int pageNumber, int pageSize) {
        return newsService.findAll(pageNumber, pageSize).stream().toList();
    }

    @Transactional
    public News newsFindById(Long newsId) {
        return newsService.findById(newsId);
    }

    @Transactional
    public List<Comment> commentFindByAllForNews(Long newsId) {
        News news =  newsService.findById(newsId);
        return news.getComments();
    }
}
