package ru.tai._10_work.service;

import ru.tai._10_work.exception.EntityAlreadyExistsExeption;
import ru.tai._10_work.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    List<User> findAll(int pageNumber, int pageSize);
    User findById(Long id);
    User save(User user) throws EntityAlreadyExistsExeption;
    User update(User user);
    User deleteById(Long id);

}
