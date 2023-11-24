package ru.tai._7_work.repository;

import org.springframework.stereotype.Repository;
import ru.tai._7_work.model.Student;

import java.util.*;

@Repository
public class MemoryStudentRepository implements ru.tai._7_work.repository.StudentRepository {

    private final Map<UUID, Student> students = new HashMap<>();

    @Override
    public List<Student> findAll() {
         return new ArrayList<>(students.values());
    }

    @Override
    public Student findById(UUID id) {
        return students.get(id);
    }

    @Override
    public void save(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public Student update(Student student) {
        return null;
    }

    @Override
    public Student deleteById(UUID id) {
        return students.remove(id);
    }

    @Override
    public void deleteAll() {
        students.clear();
    }


}
