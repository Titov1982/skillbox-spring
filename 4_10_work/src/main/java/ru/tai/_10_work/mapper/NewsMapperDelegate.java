package ru.tai._10_work.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        news.setCategory(categoryService.findByName(request.getCategoryName()));
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
