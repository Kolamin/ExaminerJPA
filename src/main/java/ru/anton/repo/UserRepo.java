package ru.anton.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anton.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
