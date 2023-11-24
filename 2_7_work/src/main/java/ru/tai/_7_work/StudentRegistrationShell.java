package ru.tai._7_work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.tai._7_work.event.CreateStudentEvent;
import ru.tai._7_work.event.DeleteStudentEvent;
import ru.tai._7_work.model.Student;
import ru.tai._7_work.service.StudentService;

import java.text.MessageFormat;
import java.util.*;

@ShellComponent
public class StudentRegistrationShell {

    private final StudentService studentService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
//    @Autowired
//    private StudentGenerator studentGenerator;

//    public StudentRegistrationShell(StudentService studentService, StudentGenerator studentGenerator) {
//        this.studentService = studentService;
//        this.studentGenerator = studentGenerator;
//        this.studentGenerator.startStudentGenerator();
//    }

    public StudentRegistrationShell(StudentService studentService) {this.studentService = studentService;

    }

    @ShellMethod(key = "a")
    public void add(@ShellOption(value = "f") String firstName,
                      @ShellOption(value = "l") String lastName,
                      @ShellOption(value = "a") Integer age) {

        Student student = studentService.add(firstName, lastName, age);
        eventPublisher.publishEvent(new CreateStudentEvent(this, student));
    }

    @ShellMethod(key = "l")
    public void list() {
        List<Student> studentList = studentService.list();
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    @ShellMethod(key = "d")
    public void delete(UUID id) {
        Student student = studentService.delete(id);
        eventPublisher.publishEvent(new DeleteStudentEvent(this, student));
    }

    @ShellMethod(key = "da")
    public void deleteAll() {
        studentService.deleteAll();
        System.out.println("Список студентов очищен");
    }

    @ShellMethod
    public void exit() {
        System.exit(0);
    }
}
