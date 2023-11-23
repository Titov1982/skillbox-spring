package ru.tai._7_work.service;

import org.springframework.stereotype.Service;
import ru.tai._7_work.exception.NotFoundStudentException;
import ru.tai._7_work.model.Student;
import ru.tai._7_work.repository.StudentRepository;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(String firstName, String lastName, Integer age) {
        Student student = new Student(UUID.randomUUID(), firstName, lastName, age);
        studentRepository.save(student);
        return student;
    }

    @Override
    public List<Student> list() {
        List<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()) {
            throw new NotFoundStudentException("Список студентов пуст!");
        }
        return studentList;
    }

    @Override
    public Student delete(UUID id) {
        Student student = studentRepository.deleteById(id);
        if (student == null) {
            throw new NotFoundStudentException("Студент не был удален!");
        }
        return student;
    }

    @Override
    public void deleteAll() {
        studentRepository.deleteAll();
    }
}
