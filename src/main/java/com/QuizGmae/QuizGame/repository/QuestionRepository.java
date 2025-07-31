package com.QuizGmae.QuizGame.repository;


import com.QuizGmae.QuizGame.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {
}
