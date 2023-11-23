package ru.tai._7_work.service;

import org.springframework.shell.standard.ShellOption;
import ru.tai._7_work.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    Student add(String firstName, String lastName, Integer age);
    List<Student> list();
    Student delete(UUID id);
    void deleteAll();
}
