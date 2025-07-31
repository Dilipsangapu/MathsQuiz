package com.QuizGmae.QuizGame.controller;

import com.QuizGmae.QuizGame.model.Question;
import com.QuizGmae.QuizGame.service.QuizService;
import com.QuizGmae.QuizGame.session.QuizSession;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz-ui")
public class QuizUIController {

    private final QuizService quizService;
    @GetMapping("/start")
    public String startQuizSession(HttpSession session) {
        QuizSession quizSession = new QuizSession();
        quizSession.setQuestions(quizService.getAllQuestions());
        session.setAttribute("quizSession", quizSession); // ✅ score starts at 0 inside object
        return "redirect:/quiz-ui/question";
    }


    @GetMapping("/question")
    public String showCurrentQuestion(HttpSession session, Model model) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");

        if (quizSession == null || quizSession.getCurrentIndex() >= quizSession.getQuestions().size()) {
            return "redirect:/quiz-ui/result";
        }

        Question question = quizSession.getQuestions().get(quizSession.getCurrentIndex());
        model.addAttribute("question", question);
        model.addAttribute("questionNumber", quizSession.getCurrentIndex() + 1);
        model.addAttribute("totalQuestions", quizSession.getQuestions().size());
        model.addAttribute("showFeedback", false); // ✅ fix added here
        return "quiz-question";
    }

//
//    @PostMapping("/answer")
//    public String submitAnswer(@RequestParam("selectedAnswer") String selectedAnswer, HttpSession session) {
//        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");
//        quizSession.getSelectedAnswers().add(selectedAnswer);
//        quizSession.setCurrentIndex(quizSession.getCurrentIndex() + 1);
//        return "redirect:/quiz-ui/question";
//    }

    @GetMapping("/result")
    public String showResult(HttpSession session, Model model) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");

        if (quizSession == null) return "redirect:/quiz-ui/start";

        int score = quizSession.getScore();
        model.addAttribute("score", score);
        model.addAttribute("total", quizSession.getQuestions().size());
        return "quiz-result";
    }


    // 1. Submit answer and show feedback
    @PostMapping("/answer")
    public String handleAnswer(@RequestParam String selectedAnswer, HttpSession session, Model model) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");

        if (quizSession == null) return "redirect:/quiz-ui/start";

        List<Question> questions = quizSession.getQuestions();
        int currentIndex = quizSession.getCurrentIndex();

        if (currentIndex >= questions.size()) {
            return "redirect:/quiz-ui/result";
        }

        Question currentQuestion = questions.get(currentIndex);
        quizSession.getSelectedAnswers().add(selectedAnswer);  // ✅ Store the answer

        // ✅ Don't increment currentIndex here!

        // Check correctness
        boolean isCorrect = currentQuestion.getCorrectAnswer().equals(selectedAnswer);
        if (isCorrect) {
            quizSession.setScore(quizSession.getScore() + 1);
        }

        model.addAttribute("question", currentQuestion);
        model.addAttribute("selectedAnswer", selectedAnswer);
        model.addAttribute("correctAnswer", currentQuestion.getCorrectAnswer());
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("score", quizSession.getScore());
        model.addAttribute("questionNumber", currentIndex + 1);  // ✅ accurate now
        model.addAttribute("showFeedback", true);

        return "quiz-question";
    }



    // 2. Proceed to next question
    @PostMapping("/next")
    public String goToNextQuestion(HttpSession session) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");

        if (quizSession == null) return "redirect:/quiz-ui/start";

        int currentIndex = quizSession.getCurrentIndex();

        // ✅ Increment index now, AFTER feedback is shown
        currentIndex++;

        if (currentIndex >= quizSession.getQuestions().size()) {
            return "redirect:/quiz-ui/result";
        }

        quizSession.setCurrentIndex(currentIndex);
        return "redirect:/quiz-ui/question";
    }



}
