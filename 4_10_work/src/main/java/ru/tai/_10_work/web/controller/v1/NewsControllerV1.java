package ru.tai._10_work.web.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.tai._10_work.web.model.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "News v1", description = "News API version v1")
public class NewsControllerV1 {

    private final NewsService newsService;
    private final NewsMapper newsMapper;
    private final CommentService commentService;

    @Operation(
            summary = "Получение список всех новостей",
            description = "Получение списка всех новостей",
            tags = {"news"}
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = NewsListResponse.class), mediaType = "application/json")
            }
    )
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

    @Operation(
            summary = "Получение новости по ее ID",
            description = "Получение новости по ее ID. Возвращает id, username и comments",
            tags = {"news", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
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

    @Operation(
            summary = "Создание новой новости",
            description = "Создание новой новости. Возвращает новую новость",
            tags = {"news"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News news = newsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(news));
    }

    @Operation(
            summary = "Обновление новости по ее ID",
            description = "Обновление новости по ее ID. Возвращает обновленную новость",
            tags = {"news", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId, @RequestBody @Valid UpsertNewsRequest request) {
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId, request));
        if (updatedNews != null) {
            return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
        }
        throw new EntityNotFoundException(MessageFormat.format("Новость с id= {0} не найдена", newsId));
    }

    @Operation(
            summary = "Удаление новости по ее ID",
            description = "Удаление новости по ее ID",
            tags = {"news", "id"}
    )
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam("userId") Long userId) {
        newsService.deleteByIdAndUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Поиск новостей по фильтру",
            description = "Поиск новостей по фильтру. Фильтр может включать категорию, пользователя или то и другое",
            tags = {"news", "id"}
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = NewsListResponse.class), mediaType = "application/json")
            }
    )
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
