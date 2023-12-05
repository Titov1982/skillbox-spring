package ru.tai._10_work.web.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tai._10_work.mapper.NewsMapper;
import ru.tai._10_work.model.News;
import ru.tai._10_work.service.NewsService;
import ru.tai._10_work.web.model.NewsListResponse;
import ru.tai._10_work.web.model.NewsResponse;
import ru.tai._10_work.web.model.UpsertNewsRequest;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsControllerV1 {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<NewsListResponse> findById(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(newsService.findAll(pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody UpsertNewsRequest request) {
        News u = newsMapper.requestToNews(request);
        News news = newsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(news));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId, @RequestBody UpsertNewsRequest request) {
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId, request));
        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
