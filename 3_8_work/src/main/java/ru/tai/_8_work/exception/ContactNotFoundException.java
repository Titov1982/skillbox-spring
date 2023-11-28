package ru.tai._8_work.exception;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(String message) {
        super(message);
    }
}
