package ru.tai._10_work.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tai._10_work.exception.EntityAlreadyExistsExeption;
import ru.tai._10_work.model.News;
import ru.tai._10_work.model.User;
import ru.tai._10_work.repository.UserRepository;
import ru.tai._10_work.service.UserService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        log.debug("UserServiceImpl->findAll");
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll(int pageNumber, int pageSize) {
        log.debug("UserServiceImpl->findAll pageNumber= {}, pageSize= {}", pageNumber, pageSize);
        List<User> users = userRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
        for (User user : users) {
            user.setComments(null);
            for (News news : user.getNewsList()) {
                news.setComments(null);
            }
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        log.debug("UserServiceImpl->findById id= {}", id);
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public User save(User user) throws EntityAlreadyExistsExeption {
        log.debug("UserServiceImpl->save user= {}", user);
        User existedUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existedUser == null) {
            return userRepository.save(user);
        }
        throw new EntityAlreadyExistsExeption(MessageFormat.format("Пользователь с username= {0} уже существует", existedUser.getUsername()));
    }

    @Override
    public User update(User user) {
        log.debug("UserServiceImpl->update user= {}", user);
        User existedUser = userRepository.findById(user.getId()).orElse(null);
        if (existedUser != null) {
            existedUser.setUsername(user.getUsername());
            existedUser.setPassword(user.getPassword());
            existedUser.setComments(user.getComments());
            existedUser.setNewsList(user.getNewsList());
            return userRepository.save(existedUser);
        }
        return null;
    }

    @Override
    public User deleteById(Long id) {
        log.debug("UserServiceImpl->deleteById id= {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.deleteById(id);
            return user;
        }
        return null;
    }
}
