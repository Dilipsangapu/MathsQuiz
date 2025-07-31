package com.QuizGmae.QuizGame.repository;


import com.QuizGmae.QuizGame.model.UserAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAnswerRepository extends MongoRepository<UserAnswer, String> {
}
