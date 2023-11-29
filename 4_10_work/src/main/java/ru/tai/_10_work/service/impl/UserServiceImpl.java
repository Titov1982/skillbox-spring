package ru.tai._10_work.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tai._10_work.model.User;
import ru.tai._10_work.repository.UserRepository;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.exception.EntityNotFoundException;

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
    public User findById(Long id) {
        log.debug("UserServiceImpl->findById id= {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            return user;
        }
        throw new EntityNotFoundException(MessageFormat.format("Пользователь с ID= {0} не найден!", id));
    }

    @Override
    public User save(User user) {
        log.debug("UserServiceImpl->save user= {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        log.debug("UserServiceImpl->update user= {}", user);
        User existedUser = userRepository.findById(user.getId()).orElse(null);
        if (existedUser != null){
            existedUser.setUsername(user.getUsername());
            existedUser.setPassword(user.getPassword());
            existedUser.setComments(user.getComments());
            existedUser.setNewsList(user.getNewsList());
            userRepository.save(existedUser);
        }
        throw new EntityNotFoundException(MessageFormat.format("Пользователь с ID= {0} не найден!", user.getId()));
    }

    @Override
    public User deleteById(Long id) {
        log.debug("UserServiceImpl->deleteById id= {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            userRepository.deleteById(id);
            return user;
        }
        throw new EntityNotFoundException(MessageFormat.format("Пользователь с ID= {0} не найден!", id));
    }
}
