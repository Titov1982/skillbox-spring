package ru.tai._10_work.web.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tai._10_work.exception.EntityNotFoundException;
import ru.tai._10_work.mapper.UserMapper;
import ru.tai._10_work.model.User;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.web.model.UpsertUserRequest;
import ru.tai._10_work.web.model.UserListResponse;
import ru.tai._10_work.web.model.UserResponse;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(userService.findAll(pageNumber, pageSize))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(userMapper.userToResponse(user));
        }
        throw new EntityNotFoundException(MessageFormat.format("Пользователь с id= {0} не найдена", id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId, @RequestBody @Valid UpsertUserRequest request) {
        User updatedUser = userService.update(userMapper.requestToUser(userId, request));
        if (updatedUser != null) {
            return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
        }
        throw new EntityNotFoundException(MessageFormat.format("Пользователь с id= {0} не найдена", userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
