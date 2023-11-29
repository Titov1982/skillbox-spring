package ru.tai._10_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tai._10_work.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
