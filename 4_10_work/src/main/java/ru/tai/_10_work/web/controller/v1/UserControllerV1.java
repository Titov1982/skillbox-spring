package ru.tai._10_work.web.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tai._10_work.exception.EntityAlreadyExistsExeption;
import ru.tai._10_work.exception.EntityNotFoundException;
import ru.tai._10_work.mapper.UserMapper;
import ru.tai._10_work.model.User;
import ru.tai._10_work.service.UserService;
import ru.tai._10_work.web.model.ErrorResponse;
import ru.tai._10_work.web.model.UpsertUserRequest;
import ru.tai._10_work.web.model.UserListResponse;
import ru.tai._10_work.web.model.UserResponse;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User v1", description = "User API version v1")
public class UserControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Получение список всех пользователей",
            description = "Получение списка всех пользователей",
            tags = {"user"}
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = UserListResponse.class), mediaType = "application/json")
            }
    )
    @GetMapping
    public ResponseEntity<UserListResponse> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(userService.findAll(pageNumber, pageSize))
        );
    }

    @Operation(
            summary = "Получение пользователя по его ID",
            description = "Получает пользователя по его ID. Возвращает id, username и newsList",
            tags = {"user", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(userMapper.userToResponse(user));
        }
        throw new EntityNotFoundException(MessageFormat.format("Пользователь с id= {0} не найдена", id));
    }

    @Operation(
            summary = "Создание нового пользователя",
            description = "Создание нового пользователя. Возвращает нового пользователя",
            tags = {"user"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = null;
        try {
            newUser = userService.save(userMapper.requestToUser(request));
        } catch (EntityAlreadyExistsExeption e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @Operation(
            summary = "Обновление пользователя по его ID",
            description = "Обновление пользователя по его ID. Возвращает id, username и newsList",
            tags = {"user", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId, @RequestBody @Valid UpsertUserRequest request) {
        User updatedUser = userService.update(userMapper.requestToUser(userId, request));
        if (updatedUser != null) {
            return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
        }
        throw new EntityNotFoundException(MessageFormat.format("Пользователь с id= {0} не найдена", userId));
    }

    @Operation(
            summary = "Удаление пользователя по его ID",
            description = "Удаляет пользователя по его ID",
            tags = {"user", "id"}
    )
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
