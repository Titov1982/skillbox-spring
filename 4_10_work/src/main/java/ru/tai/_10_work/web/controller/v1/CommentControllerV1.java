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
import ru.tai._10_work.mapper.CommentMapper;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.web.model.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comment v1", description = "Comment API version v1")
public class CommentControllerV1 {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(
            summary = "Получение всех комментариев по ID новости",
            description = "Получение всех комментариев по ID новости. Возвращает список комментариев",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{newsId}")
    public ResponseEntity<CommentListResponse> findByNewsId(@PathVariable("newsId") Long newsId) {

        List<CommentResponse> commentResponseList = new ArrayList<>();
        List<Comment> comments = commentService.findAllByNewsId(newsId);
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                comment.setNews(null);
                comment.setNews(null);
                commentResponseList.add(commentMapper.commentToResponse(comment));
            }

            CommentListResponse commentListResponse = new CommentListResponse();
            commentListResponse.setComments(commentResponseList);
            return ResponseEntity.ok(commentListResponse);
        }
        throw new EntityNotFoundException(MessageFormat.format("Комментарии для новости с id= {0} не найдены", newsId));
    }

    @Operation(
            summary = "Создание нового комментария",
            description = "Создание нового комментария. Возвращает новый комментарий",
            tags = {"comment"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment comment = commentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(comment));
    }

    @Operation(
            summary = "Обновление комментария по его ID",
            description = "Обновление комментария по его ID. Возвращает обновленный комментарий",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId, @RequestBody @Valid UpsertCommentRequest request) {
        Comment updatedComment = commentService.update(commentMapper.requestToComment(commentId, request));
        if (updatedComment != null) {
            return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
        }
        throw new EntityNotFoundException(MessageFormat.format("Комментарий с id= {0} не найден", commentId));
    }

    @Operation(
            summary = "Удаление комментария по его ID",
            description = "Удаление комментария по его ID",
            tags = {"comment", "id"}
    )
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam("userId") Long userId) {
        commentService.deleteByIdAndUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

}
