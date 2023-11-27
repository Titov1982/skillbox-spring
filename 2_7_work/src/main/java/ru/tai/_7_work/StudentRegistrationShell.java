package ru.tai._7_work;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.tai._7_work.event.CreateStudentEvent;
import ru.tai._7_work.event.DeleteStudentEvent;
import ru.tai._7_work.model.Student;
import ru.tai._7_work.service.StudentService;

import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class StudentRegistrationShell {

    private final StudentService studentService;
    private final ApplicationEventPublisher eventPublisher;

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
        System.out.println("Список студентов:");
        int count = 1;
        for (Student student : studentList) {
            System.out.println(count + ": " + student);
            count++;
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
