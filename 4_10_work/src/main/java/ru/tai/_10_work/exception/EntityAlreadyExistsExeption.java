package ru.tai._10_work.exception;

import java.sql.SQLException;

public class EntityAlreadyExistsExeption extends SQLException {

    public EntityAlreadyExistsExeption(String message) {
        super(message);
    }
}
