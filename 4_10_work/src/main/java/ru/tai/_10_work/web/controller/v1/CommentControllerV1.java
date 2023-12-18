package ru.tai._10_work.web.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tai._10_work.aop.UserControl;
import ru.tai._10_work.mapper.CommentMapper;
import ru.tai._10_work.model.Comment;
import ru.tai._10_work.model.News;
import ru.tai._10_work.service.CommentService;
import ru.tai._10_work.web.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        for (Comment comment : comments) {
            comment.setNews(null);
            comment.setNews(null);
            commentResponseList.add(commentMapper.commentToResponse(comment));
        }

        CommentListResponse commentListResponse = new CommentListResponse();
        commentListResponse.setComments(commentResponseList);
        return ResponseEntity.ok(commentListResponse);
    }


    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestParam UpsertCommentRequest request) {
        Comment comment = commentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(comment));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId, @RequestBody UpsertCommentRequest request) {
        Comment updatedComment = commentService.update(commentMapper.requestToComment(commentId, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam("userId") Long userId) {
        commentService.deleteByIdAndUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

}
