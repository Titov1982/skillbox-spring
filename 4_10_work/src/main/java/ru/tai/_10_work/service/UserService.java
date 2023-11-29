package ru.tai._10_work.service;

import ru.tai._10_work.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    User update(User user);
    User deleteById(Long id);

}
