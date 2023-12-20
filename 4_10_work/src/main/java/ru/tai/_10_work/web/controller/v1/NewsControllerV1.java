package ru.tai._10_work.web.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tai._10_work.exception.EntityNotFoundException;
import ru.tai._10_work.mapper.NewsMapper;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.service.NewsService;
import ru.tai._10_work.web.model.NewsFilter;
import ru.tai._10_work.web.model.NewsListResponse;
import ru.tai._10_work.web.model.NewsResponse;
import ru.tai._10_work.web.model.UpsertNewsRequest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsControllerV1 {

    private final NewsService newsService;
    private final NewsMapper newsMapper;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        List<NewsResponse> listNewsResponse = new ArrayList<>();
        List<News> newsList = newsService.findAll(pageNumber, pageSize);
        List<Long> countCommentList = newsList.stream().map(e->e.getComments().stream().count()).toList();

        List<NewsResponse> newsResponseList = newsMapper.newsListToResponseList(newsList);
        for (int i=0; i< newsResponseList.size(); i++) {
            newsResponseList.get(i).setCountComment(countCommentList.get(i));
            newsResponseList.get(i).setComments(new ArrayList<>());
            listNewsResponse.add(newsResponseList.get(i));
        }

        NewsListResponse newsListResponse = new NewsListResponse();
        newsListResponse.setNewsList(listNewsResponse);
        return ResponseEntity.ok(newsListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable("id") Long id) {
        News news = newsService.findById(id);
        if (news != null) {
            List<Comment> comments = commentService.findAllByNewsId(id);
            news.setComments(comments);
            NewsResponse newsResponse = newsMapper.newsToResponse(news);
            newsResponse.setCountComment(Long.valueOf(comments.size()));
            return ResponseEntity.ok(newsResponse);
        }
        throw new EntityNotFoundException(MessageFormat.format("Новость с id= {0} не найдена", id));
    }

    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News news = newsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(news));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId, @RequestBody @Valid UpsertNewsRequest request) {
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId, request));
        if (updatedNews != null) {
            return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
        }
        throw new EntityNotFoundException(MessageFormat.format("Новость с id= {0} не найдена", newsId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam("userId") Long userId) {
        newsService.deleteByIdAndUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long userId) {
        NewsFilter filter = new NewsFilter();
        filter.setCategoryId(categoryId);
        filter.setUserId(userId);
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(newsService.filterBy(filter))
        );
    }
}
