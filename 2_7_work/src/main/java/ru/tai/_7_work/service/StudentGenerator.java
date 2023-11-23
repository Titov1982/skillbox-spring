package ru.tai._7_work.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

//@Service
@RequiredArgsConstructor
@ConditionalOnProperty("app.student-generator.enabled")
public class StudentGenerator {

    @Value("${app.random-max-student-count}")
    private String randomMaxStudentCount;
    private final StudentService studentService;

    @EventListener(ApplicationReadyEvent.class)
//    @EventListener(ApplicationContextEvent.class)
//    @EventListener(ContextStartedEvent.class)
    public void startStudentGenerator() {
        int studentCount = getRandomNumber(1, Integer.parseInt(randomMaxStudentCount));
        for (int i=0; i < studentCount; i++) {
            studentService.add("FirstName" + i, "LastName" + i, (i+1) * 5);
        }
        System.out.println(MessageFormat.format("Сгенерировано {0} случайных студентов", studentCount));
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
