package ru.tai._10_work.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpsertUserRequest {

    @NotBlank(message = "Необходимо ввести имя клиента")
    @Size(min = 3, message = "Имя пользователя не должно быть меньше 3 символов")
    private String username;
    @NotBlank(message = "Необходимо ввести пароль")
    @Size(min = 3, message = "Пароль пользователя не должно быть меньше 3 символов")
    private String password;

}
