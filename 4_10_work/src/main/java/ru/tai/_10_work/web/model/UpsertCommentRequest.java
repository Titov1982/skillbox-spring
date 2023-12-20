package ru.tai._10_work.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpsertCommentRequest {

    @NotNull(message = "Не указан ID пользователя")
    private Long userId;
    @NotNull(message = "Не указан ID новости")
    private Long newsId;
    @NotBlank(message = "Не указан текст комментария")
    private String comment;
}
