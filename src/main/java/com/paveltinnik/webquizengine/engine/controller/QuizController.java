package com.paveltinnik.webquizengine.engine.controller;

import com.paveltinnik.webquizengine.engine.entities.*;
import com.paveltinnik.webquizengine.engine.repository.CompletionRepositoryImpl;
import com.paveltinnik.webquizengine.engine.repository.MyUserRepositoryImpl;
import com.paveltinnik.webquizengine.engine.repository.QuizRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class QuizController {
    @Autowired
    QuizRepositoryImpl quizRepository;
    @Autowired
    MyUserRepositoryImpl myUserRepositoryImpl;
    @Autowired
    CompletionRepositoryImpl completionRepositoryImpl;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id) {
        Quiz quiz = quizRepository.getQuizById(id);

        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quiz);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("api/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int pageSize,
                                                    @RequestParam(defaultValue = "id") String sortBy) {
        Page<Quiz> listOfQuizzes = quizRepository.getAllQuizzes(page, pageSize, sortBy);

        return new ResponseEntity<>(listOfQuizzes, new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("api/quizzes")
    public Quiz postQuiz(@Valid @RequestBody Quiz quiz) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUsername = auth.getName();
        quiz.setCreator(authUsername);

        return quizRepository.addQuiz(quiz);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Answer> getAnswer(@PathVariable(name = "id") int id,
                                            @RequestBody AnswerFromUser answer) {
        try {
            List<Integer> answers = quizRepository.getQuizById(id).getAnswer();

            if (answer.getAnswer().equals(answers) || answer.getAnswer().isEmpty() && answers == null) {
                // Получить email того, кто ответил правильно на вопрос
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String authUsername = auth.getName();

                // Добавить новый completion
                CompletionEntity completion = new CompletionEntity(authUsername, id, LocalDateTime.now());
                completionRepositoryImpl.addCompletion(completion);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Answer(true, "Congratulations, you're right!"));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Answer(false, "Wrong answer! Please, try again."));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/api/register")
    public ResponseEntity<Object> register(@Valid @RequestBody MyUser user) {
        MyUser userFromDb = myUserRepositoryImpl.getUser(user.getEmail());

        if (userFromDb == null) {
            myUserRepositoryImpl.addUser(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already in database");
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUsername = auth.getName();

        Quiz quiz = quizRepository.getQuizById(id);

        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            if (quiz.getCreator().equals(authUsername)) {
                quizRepository.deleteQuizById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("api/quizzes/completed")
    public ResponseEntity<Object> getCompletions(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestParam(defaultValue = "completedAt") String sortBy) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUsername = auth.getName();
        Page<CompletionEntity> listOfCompletions = completionRepositoryImpl
                .getAllCompletions(authUsername, page, pageSize, sortBy);

        return new ResponseEntity<>(listOfCompletions, new HttpHeaders(), HttpStatus.OK);
    }
}

