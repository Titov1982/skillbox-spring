package ru.tai._10_work.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpsertNewsRequest {

    @NotNull(message = "Не указан ID пользователя")
    private Long userId;
    @NotBlank(message = "Необходимо указать заголовок новости")
    private String title;
    @NotBlank(message = "Необходимо внести текст новость")
    private String description;
    @NotBlank(message = "Не указано название категории новости")
    private String categoryName;
}
