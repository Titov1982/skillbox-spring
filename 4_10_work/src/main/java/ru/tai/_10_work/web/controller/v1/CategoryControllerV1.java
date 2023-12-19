package ru.tai._10_work.web.controller.v1;

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
import ru.tai._10_work.web.model.CategoryListResponse;
import ru.tai._10_work.web.model.CategoryResponse;
import ru.tai._10_work.web.model.UpsertCategoryRequest;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryControllerV1 {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryListResponse(categoryService.findAll(pageNumber, pageSize))
        );
    }

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

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody UpsertCategoryRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId, @RequestBody UpsertCategoryRequest request) {
        Category updatedCategory = categoryService.update(categoryMapper.requestToCategory(categoryId, request));
        if (updatedCategory != null) {
            return ResponseEntity.ok(categoryMapper.categoryToResponse(updatedCategory));
        }
        throw new EntityNotFoundException(MessageFormat.format("Категория с id= {0} не найдена", categoryId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
