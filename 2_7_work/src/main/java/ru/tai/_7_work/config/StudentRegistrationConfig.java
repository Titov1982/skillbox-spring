package ru.tai._7_work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tai._7_work.service.StudentGenerator;
import ru.tai._7_work.service.StudentService;

@Configuration
public class StudentRegistrationConfig {

    @Bean
    StudentGenerator studentGenerator(StudentService studentService) {
        return new StudentGenerator(studentService);
    }
}
