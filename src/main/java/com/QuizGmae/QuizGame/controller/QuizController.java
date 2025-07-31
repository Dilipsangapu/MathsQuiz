package com.QuizGmae.QuizGame.controller;

import com.QuizGmae.QuizGame.model.Question;
import com.QuizGmae.QuizGame.model.UserAnswer;
import com.QuizGmae.QuizGame.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@CrossOrigin // if frontend is separate
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/start")
    public ResponseEntity<List<Question>> startQuiz() {
        return ResponseEntity.ok(quizService.getAllQuestions());
    }

//    @PostMapping("/submit")
//    public ResponseEntity<Integer> submitQuiz(@RequestBody UserAnswer userAnswer) {
//        int score = quizService.submitAnswers(userAnswer);
//        return ResponseEntity.ok(score);
//    }

    @GetMapping("/scoreboard")
    public ResponseEntity<List<UserAnswer>> getScoreboard() {
        return ResponseEntity.ok(quizService.getLeaderboard());
    }
    @PostMapping("/submit")
    public ResponseEntity<Integer> submitQuiz(@RequestBody UserAnswer userAnswer, Principal principal) {
        String username = principal.getName(); // from Spring Security
        userAnswer.setUsername(username);
        int score = quizService.submitAnswers(userAnswer);
        return ResponseEntity.ok(score);
    }

}
