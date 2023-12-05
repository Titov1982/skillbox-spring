package ru.tai._10_work.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tai._10_work.exception.EntityNotFoundException;
import ru.tai._10_work.model.Category;
import ru.tai._10_work.repository.CategoryRepository;
import ru.tai._10_work.service.CategoryService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        log.debug("CategoryServiceImpl->findAll");
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        log.debug("CategoryServiceImpl->findById id= {}", id);
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null){
            return category;
        }
        throw new EntityNotFoundException(MessageFormat.format("Категория с ID= {0} не найдена!", id));
    }

    @Override
    public Category findByName(String name) {
        log.debug("CategoryServiceImpl->findByName name= {}", name);
        Category category = categoryRepository.findByName(name).orElse(null);
        if (category != null){
            return category;
        }
        return null;
    }

    @Override
    public Category save(Category category) {
        log.debug("CategoryServiceImpl->save category= {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        log.debug("CategoryServiceImpl->update category= {}", category);
        Category existedCategory = categoryRepository.findById(category.getId()).orElse(null);
        if (existedCategory != null){
            existedCategory.setName(category.getName());
            existedCategory.setNewsList(category.getNewsList());
            categoryRepository.save(existedCategory);
        }
        throw new EntityNotFoundException(MessageFormat.format("Категория с ID= {0} не найдена!", category.getId()));
    }

    @Override
    public Category deleteById(Long id) {
        log.debug("CategoryServiceImpl->deleteById id= {}", id);
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null){
            categoryRepository.deleteById(id);
            return category;
        }
        throw new EntityNotFoundException(MessageFormat.format("Категория с ID= {0} не найдена!", id));
    }
}
