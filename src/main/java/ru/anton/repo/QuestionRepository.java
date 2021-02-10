package ru.anton.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anton.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public Question findById(long id);
}
