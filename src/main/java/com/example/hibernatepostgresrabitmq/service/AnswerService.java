package com.example.hibernatepostgresrabitmq.service;

import com.example.hibernatepostgresrabitmq.exception.ResourceNotFoundException;
import com.example.hibernatepostgresrabitmq.model.Answer;
import com.example.hibernatepostgresrabitmq.repository.AnswerRepository;
import com.example.hibernatepostgresrabitmq.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Answer addAnswer(Long questionId, Answer answer) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    answer.setQuestion(question);
                    return answerRepository.save(answer);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Question not found with id %b", questionId))
                );
    }

    public Answer updateAnswer(Long questionId,
                               Long answerId,
                               Answer answerRequest) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException(String.format("Question not found with id %b", questionId));
        }

        return answerRepository.findById(answerId)
                .map(answer -> {
                    answer.setText(answerRequest.getText());
                    return answerRepository.save(answer);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Answer not found with id %b", answerId))
                );
    }

    public ResponseEntity<?> deleteAnswer(Long questionId,
                                          Long answerId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException(String.format("Question not found with id %b", questionId));
        }

        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.delete(answer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Answer not found with id %b", answerId))
                );
    }
}
