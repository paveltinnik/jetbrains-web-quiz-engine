package com.paveltinnik.webquizengine.engine.repository;

import com.paveltinnik.webquizengine.engine.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Optional<Quiz> findByTitle(String title);
    Optional<Quiz> findById(int id);
}
