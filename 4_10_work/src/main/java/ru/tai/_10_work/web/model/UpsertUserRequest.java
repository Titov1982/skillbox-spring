package ru.tai._10_work.web.model;

import lombok.Data;

@Data
public class UpsertUserRequest {

    private String username;
    private String password;

}
