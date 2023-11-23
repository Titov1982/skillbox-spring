package ru.tai._7_work.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;

}
