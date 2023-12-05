package ru.tai._10_work.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.tai._10_work.model.News;
import ru.tai._10_work.web.model.NewsListResponse;
import ru.tai._10_work.web.model.NewsResponse;
import ru.tai._10_work.web.model.UpsertNewsRequest;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class})
public interface NewsMapper {

    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    NewsResponse newsToResponse(News news);

    List<NewsResponse> newsListToResponseList(List<News> newsList);

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse response = new NewsListResponse();
        response.setNewsList(newsListToResponseList(newsList));
        return response;
    }
}
