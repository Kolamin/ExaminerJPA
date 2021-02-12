package ru.anton.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anton.models.CorrectAnswer;

@Repository
public interface AnswerRepository extends JpaRepository<CorrectAnswer, Long> {
        public CorrectAnswer findById(long id);
}
