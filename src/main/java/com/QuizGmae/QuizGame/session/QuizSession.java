package com.QuizGmae.QuizGame.session;

import com.QuizGmae.QuizGame.model.Question;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class QuizSession {
    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private List<String> selectedAnswers = new ArrayList<>();
    private int score = 0;
}
