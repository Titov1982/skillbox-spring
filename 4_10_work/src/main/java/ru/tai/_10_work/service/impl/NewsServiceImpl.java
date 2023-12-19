package ru.tai._10_work.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tai._10_work.aop.UserControl;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.model.News;
import ru.tai._10_work.model.User;
import ru.tai._10_work.repository.NewsRepository;
import ru.tai._10_work.service.CategoryService;
import ru.tai._10_work.service.NewsService;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.utils.BeanUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public List<News> findAll() {
        log.debug("NewsServiceImpl->findAll");
        return newsRepository.findAll();
    }

    @Override
    public List<News> findAll(int pageNumber, int pageSize) {
        log.debug("NewsServiceImpl->findAll pageNumber= {}, pageSize= {}", pageNumber, pageSize);
        return newsRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }

    @Override
    public News findById(Long id) {
        log.debug("NewsServiceImpl->findById id= {}", id);
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News save(News news) {
        log.debug("NewsServiceImpl->save news= {}", news);
        User user = userService.findById(news.getUser().getId());
        Category category = categoryService.findById(news.getCategory().getId());
        news.setUser(user);
        news.setCategory(category);
        news.setCreateAt(Instant.now());
        return newsRepository.save(news);
    }

    @Override
    @UserControl
    public News update(News news) {
        log.debug("NewsServiceImpl->update news= {}", news);
        User user = userService.findById(news.getUser().getId());
        Category category = categoryService.findById(news.getCategory().getId());
        News existedNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existedNews);
        existedNews.setUser(user);
        existedNews.setCategory(category);
        return newsRepository.save(existedNews);
    }

    @Override
    @UserControl
    public News deleteById(Long id) {
        log.debug("NewsServiceImpl->deleteById id= {}", id);
        News deletedNews = newsRepository.findById(id).orElse(null);
        newsRepository.deleteById(id);
        return deletedNews;
    }

    @Override
    @UserControl
    @Transactional
    public News deleteByIdAndUserId(Long id, Long userId) {
        log.debug("NewsServiceImpl->deleteByIdAndUserId id= {}, userId= {}", id, userId);
        News deletedNews = newsRepository.findById(id).orElse(null);
        newsRepository.deleteByIdAndUserId(id, userId);
        return deletedNews;
    }
}
