package ru.tai._10_work.web.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tai._10_work.exception.EntityNotFoundException;
import ru.tai._10_work.mapper.CommentMapper;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.web.model.CommentListResponse;
import ru.tai._10_work.web.model.CommentResponse;
import ru.tai._10_work.web.model.UpsertCommentRequest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentControllerV1 {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

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


    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment comment = commentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId, @RequestBody @Valid UpsertCommentRequest request) {
        Comment updatedComment = commentService.update(commentMapper.requestToComment(commentId, request));
        if (updatedComment != null) {
            return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
        }
        throw new EntityNotFoundException(MessageFormat.format("Комментарий с id= {0} не найден", commentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam("userId") Long userId) {
        commentService.deleteByIdAndUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

}
