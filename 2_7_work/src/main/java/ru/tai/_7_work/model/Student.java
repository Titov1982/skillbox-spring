package ru.tai._7_work.model;

import lombok.*;

import java.text.MessageFormat;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Student {
    @ToString.Exclude
    private UUID id;
    @ToString.Exclude
    private String firstName;
    @ToString.Exclude
    private String lastName;
    @ToString.Exclude
    private Integer age;

    @Override
    public String toString() {
        return MessageFormat.format("ID: {0}, ФИО: {1} {2}, Возраст: {3}",
                id, firstName, lastName, age);
    }
}
