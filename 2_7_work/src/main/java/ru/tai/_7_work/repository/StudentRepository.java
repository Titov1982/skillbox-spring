package ru.tai._7_work.repository;

import ru.tai._7_work.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentRepository {
    List<Student> findAll();
    Student findById(UUID id);
    void save(Student student);
    Student update(Student student);
    Student deleteById(UUID id);
    void deleteAll();
}
