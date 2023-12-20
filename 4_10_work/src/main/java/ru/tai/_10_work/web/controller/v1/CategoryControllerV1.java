package ru.tai._10_work.web.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tai._10_work.exception.EntityNotFoundException;
import ru.tai._10_work.mapper.CategoryMapper;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.model.News;
import ru.tai._10_work.service.CategoryService;
import ru.tai._10_work.web.model.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Slf4j
@Tag(name = "Category v1", description = "Category API version v1")
public class CategoryControllerV1 {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(
            summary = "Получение список всех категорий",
            description = "Получение списка всех категорий",
            tags = {"category"}
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = CategoryListResponse.class), mediaType = "application/json")
            }
    )
    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryListResponse(categoryService.findAll(pageNumber, pageSize))
        );
    }

    @Operation(
            summary = "Получение категории по ее ID",
            description = "Получение категории по ее ID. Возвращает категорию",
            tags = {"category", "id"}
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
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {

        Category category = categoryService.findById(id);
        if (category != null) {
            List<News> newsList = category.getNewsList();
            if (newsList != null && !newsList.isEmpty()) {
                for (News news : newsList) {
                    news.setCategory(null);
                    news.setComments(null);
                    news.setUser(null);
                }
            }
            return ResponseEntity.ok(
                    categoryMapper.categoryToResponse(category));
        }
        throw new EntityNotFoundException(MessageFormat.format("Категория с id= {0} не найдена", id));
    }

    @Operation(
            summary = "Создание новой категории",
            description = "Создание новой категории. Возвращает новую категорию",
            tags = {"category"}
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
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(newCategory));
    }

    @Operation(
            summary = "Обновление категории по ее ID",
            description = "Обновление категории по ее ID. Возвращает обновленную категорию",
            tags = {"category", "id"}
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
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId, @RequestBody @Valid UpsertCategoryRequest request) {
        Category updatedCategory = categoryService.update(categoryMapper.requestToCategory(categoryId, request));
        if (updatedCategory != null) {
            return ResponseEntity.ok(categoryMapper.categoryToResponse(updatedCategory));
        }
        throw new EntityNotFoundException(MessageFormat.format("Категория с id= {0} не найдена", categoryId));
    }

    @Operation(
            summary = "Удаление категории по ее ID",
            description = "Удаление категории по ее ID",
            tags = {"category", "id"}
    )
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
