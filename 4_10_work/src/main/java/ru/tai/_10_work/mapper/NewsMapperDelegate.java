package ru.tai._10_work.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.model.News;
import ru.tai._10_work.service.CategoryService;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.web.model.UpsertNewsRequest;

@Component
public abstract class NewsMapperDelegate implements NewsMapper{

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setDescription(request.getDescription());
        Category category = categoryService.findByName(request.getCategoryName());
        if (category == null) {
            category = new Category();
            category.setName(request.getCategoryName());
            category = categoryService.save(category);
        }
        news.setCategory(category);
        news.setUser(userService.findById(request.getUserId()));
        news.setComments(commentService.findAllByUserId(request.getUserId()));
        return news;
    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);
        return news;
    }
}
