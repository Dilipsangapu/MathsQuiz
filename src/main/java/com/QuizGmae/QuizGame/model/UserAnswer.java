package com.QuizGmae.QuizGame.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "user_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswer {
    @Id
    private String id;
    private String username;
    private List<String> selectedAnswers;
    private int score;
    private LocalDateTime submittedAt;
}
