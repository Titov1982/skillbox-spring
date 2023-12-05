package ru.tai._10_work.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsListResponse {

    private List<NewsResponse> newsList = new ArrayList<>();
}
