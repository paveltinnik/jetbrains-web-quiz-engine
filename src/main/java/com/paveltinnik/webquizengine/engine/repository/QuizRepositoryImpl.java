package com.paveltinnik.webquizengine.engine.repository;

import com.paveltinnik.webquizengine.engine.entities.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuizRepositoryImpl {
    @Autowired
    QuizRepository quizRepository;

    public Quiz addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        int id = quiz.getId();
        return quizRepository.findById(id).orElse(null);
    }

    public Quiz getQuizById(int id) {
        return quizRepository.findById(id).orElse(null);
    }

    public Page<Quiz> getAllQuizzes(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }

    public void deleteQuizById(int id) {
        quizRepository.deleteById(id);
    }
}