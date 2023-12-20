package ru.tai._10_work.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertCategoryRequest {

    @NotBlank(message = "Не указано название категории")
    private String name;
}
