package ru.tai._10_work.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListResponse {

    private List<CategoryResponse> categories = new ArrayList<>();
}
