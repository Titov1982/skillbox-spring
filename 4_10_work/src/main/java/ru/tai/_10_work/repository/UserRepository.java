package ru.tai._10_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tai._10_work.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
