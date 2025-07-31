package com.QuizGmae.QuizGame.service;

import com.QuizGmae.QuizGame.model.Question;
import com.QuizGmae.QuizGame.model.UserAnswer;
import com.QuizGmae.QuizGame.repository.QuestionRepository;
import com.QuizGmae.QuizGame.repository.UserAnswerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepo;
    private final UserAnswerRepository answerRepo;

    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    public int submitAnswers(UserAnswer userAnswer) {
        List<Question> questions = questionRepo.findAll();
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswer().equals(userAnswer.getSelectedAnswers().get(i))) {
                score++;
            }
        }

        userAnswer.setScore(score);
        userAnswer.setSubmittedAt(LocalDateTime.now());
        answerRepo.save(userAnswer);

        return score;
    }

    public List<UserAnswer> getLeaderboard() {
        return answerRepo.findAll()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getScore(), a.getScore()))
                .toList();
    }
    @PostConstruct
    public void seedInitialMathQuestions() {
        if (questionRepo.count() == 0) {
            List<Question> questions = List.of(
                    new Question("What is 5 + 3?", List.of("6", "7", "8", "9"), "8"),
                    new Question("What is 12 × 2?", List.of("20", "24", "22", "26"), "24"),
                    new Question("What is 15 ÷ 3?", List.of("4", "5", "6", "7"), "5"),
                    new Question("What is 9 × 9?", List.of("81", "72", "90", "99"), "81"),
                    new Question("What is 100 ÷ 4?", List.of("20", "25", "30", "15"), "25"),
                    new Question("What is 11²?", List.of("121", "111", "101", "131"), "121"),
                    new Question("Square root of 49?", List.of("6", "7", "8", "9"), "7"),
                    new Question("What is 7 × 8?", List.of("54", "56", "58", "60"), "56"),
                    new Question("What is 64 ÷ 8?", List.of("6", "7", "8", "9"), "8"),
                    new Question("Cube of 3?", List.of("27", "9", "18", "21"), "27"),
                    new Question("What is 18 + 12?", List.of("30", "28", "31", "32"), "30"),
                    new Question("What is 20 - 9?", List.of("11", "12", "10", "13"), "11"),
                    new Question("What is 6 × 7?", List.of("42", "36", "40", "48"), "42"),
                    new Question("What is 81 ÷ 9?", List.of("8", "9", "7", "6"), "9"),
                    new Question("What is 144 ÷ 12?", List.of("11", "12", "13", "14"), "12"),
                    new Question("What is 3 × 15?", List.of("45", "40", "42", "48"), "45"),
                    new Question("What is 8²?", List.of("64", "72", "60", "68"), "64"),
                    new Question("What is cube of 2?", List.of("6", "8", "10", "12"), "8"),
                    new Question("What is 100 ÷ 5?", List.of("20", "25", "30", "35"), "20"),
                    new Question("What is 45 + 55?", List.of("95", "100", "105", "110"), "100"),
                    new Question("What is 10 × 10?", List.of("100", "110", "120", "90"), "100"),
                    new Question("What is 13 + 14?", List.of("26", "27", "28", "29"), "27"),
                    new Question("What is 72 ÷ 9?", List.of("8", "7", "9", "6"), "8"),
                    new Question("What is 121 ÷ 11?", List.of("10", "11", "12", "13"), "11"),
                    new Question("What is 5²?", List.of("20", "25", "30", "35"), "25"),
                    new Question("What is 2³?", List.of("6", "8", "4", "10"), "8"),
                    new Question("What is 49 ÷ 7?", List.of("6", "7", "8", "9"), "7"),
                    new Question("What is 90 - 45?", List.of("45", "50", "55", "60"), "45"),
                    new Question("What is 4 × 12?", List.of("48", "44", "40", "36"), "48"),
                    new Question("What is 81 ÷ 3?", List.of("27", "26", "28", "29"), "27"),
                    new Question("What is 7 + 14?", List.of("20", "21", "22", "23"), "21"),
                    new Question("What is 11 × 3?", List.of("33", "36", "30", "31"), "33"),
                    new Question("What is 99 ÷ 11?", List.of("8", "9", "10", "11"), "9"),
                    new Question("What is 8 × 5?", List.of("40", "35", "45", "30"), "40"),
                    new Question("What is 6³?", List.of("216", "210", "220", "200"), "216"),
                    new Question("What is 3 × 11?", List.of("33", "36", "30", "39"), "33"),
                    new Question("What is 25 × 2?", List.of("50", "45", "55", "40"), "50"),
                    new Question("What is 14 + 6?", List.of("18", "20", "22", "24"), "20"),
                    new Question("What is 100 - 99?", List.of("1", "2", "0", "10"), "1"),
                    new Question("What is 8 × 11?", List.of("88", "81", "84", "89"), "88"),
                    new Question("What is 63 ÷ 9?", List.of("6", "7", "8", "9"), "7"),
                    new Question("What is 50% of 200?", List.of("100", "110", "90", "120"), "100"),
                    new Question("What is 0 × 99?", List.of("99", "0", "1", "-1"), "0"),
                    new Question("What is 36 ÷ 6?", List.of("6", "5", "4", "7"), "6"),
                    new Question("What is 90 ÷ 3?", List.of("30", "25", "20", "15"), "30"),
                    new Question("What is 40 + 60?", List.of("90", "100", "110", "120"), "100"),
                    new Question("What is 5³?", List.of("125", "115", "130", "120"), "125"),
                    new Question("What is 18 × 2?", List.of("36", "32", "38", "34"), "36"),
                    new Question("What is 9²?", List.of("81", "72", "91", "61"), "81"),
                    new Question("What is 72 ÷ 8?", List.of("9", "8", "7", "6"), "9"),
                    new Question("What is 33 + 66?", List.of("99", "100", "98", "101"), "99"),
                    new Question("What is 120 ÷ 10?", List.of("11", "12", "13", "14"), "12"),
                    new Question("What is 2 + 2 × 2?", List.of("6", "8", "4", "10"), "6"),
                    new Question("What is 3 + 6 ÷ 2?", List.of("6", "7", "9", "5"), "6"),
                    new Question("What is 9 × 8?", List.of("72", "64", "81", "70"), "72"),
                    new Question("What is 80 ÷ 4?", List.of("20", "25", "30", "15"), "20"),
                    new Question("What is 121?", List.of("11²", "10²", "12²", "13²"), "11²"),
                    new Question("What is 2 + 2 + 2 + 2 + 2?", List.of("8", "10", "12", "6"), "10"),
                    new Question("What is 3 × 4 × 5?", List.of("60", "50", "40", "30"), "60"),
                    new Question("What is 9 + 9 + 9?", List.of("27", "26", "25", "28"), "27"),
                    new Question("What is 100 × 0?", List.of("0", "100", "1", "Undefined"), "0"),
                    new Question("What is 2 × 2 × 2 × 2?", List.of("8", "16", "12", "14"), "16"),
                    new Question("What is 7 × 4?", List.of("28", "24", "32", "30"), "28"),
                    new Question("What is 14 × 2?", List.of("28", "26", "30", "24"), "28"),
                    new Question("What is 99 ÷ 3?", List.of("33", "31", "30", "34"), "33"),
                    new Question("What is 2⁵?", List.of("32", "64", "16", "48"), "32"),
                    new Question("What is 10 + 90?", List.of("100", "99", "101", "91"), "100"),
                    new Question("What is 9³?", List.of("729", "728", "700", "720"), "729")
                    // Add more if needed...
            );
            questionRepo.saveAll(questions);
            System.out.println("✅ Seeded " + questions.size() + " math questions.");
        }
    }
    public int calculateScore(List<Question> questions, List<String> selectedAnswers) {
        int score = 0;
        for (int i = 0; i < Math.min(questions.size(), selectedAnswers.size()); i++) {
            if (questions.get(i).getCorrectAnswer().equals(selectedAnswers.get(i))) {
                score++;
            }
        }
        return score;
    }
}
