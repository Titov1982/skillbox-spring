package ru.tai._7_work.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.tai._7_work.model.Student;

@Getter
public class DeleteStudentEvent extends ApplicationEvent {

    private final Student student;


    public DeleteStudentEvent(Object source, Student student) {
        super(source);
        this.student = student;
    }
}
