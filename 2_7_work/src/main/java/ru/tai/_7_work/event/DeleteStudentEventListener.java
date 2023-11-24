package ru.tai._7_work.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.tai._7_work.model.Student;

import java.text.MessageFormat;

@Component
public class DeleteStudentEventListener {

    @EventListener
    public void listen(DeleteStudentEvent deleteStudentEvent) {
        Student student = deleteStudentEvent.getStudent();
        System.out.println("Call listen method");
        System.out.println(MessageFormat.format("Удален студент:ID: {0}, ФИО: {1} {2}, Возраст: {3}",
                student.getId(), student.getFirstName(), student.getLastName(), student.getAge()));
    }
}
