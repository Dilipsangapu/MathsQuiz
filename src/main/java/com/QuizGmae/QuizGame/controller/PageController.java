package com.QuizGmae.QuizGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String showQuizPage() {
        return "quiz"; // This maps to templates/quiz.html
    }
}
