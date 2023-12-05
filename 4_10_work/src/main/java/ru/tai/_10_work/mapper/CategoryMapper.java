package ru.tai._10_work.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.web.model.CategoryListResponse;
import ru.tai._10_work.web.model.CategoryResponse;
import ru.tai._10_work.web.model.UpsertCategoryRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewsMapper.class})
public interface CategoryMapper {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    List<CategoryResponse> categoryListToResponseList(List<Category> categories);

    default CategoryListResponse categoryListToCategoryListResponse(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categoryListToResponseList(categories));
        return response;
    }
}
