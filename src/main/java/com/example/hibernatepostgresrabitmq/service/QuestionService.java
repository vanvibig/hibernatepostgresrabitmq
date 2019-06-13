package com.example.hibernatepostgresrabitmq.service;

import com.example.hibernatepostgresrabitmq.exception.ResourceNotFoundException;
import com.example.hibernatepostgresrabitmq.model.Question;
import com.example.hibernatepostgresrabitmq.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Page<Question> getQuestion(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long questionId, Question questionRequest) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    question.setTitle(questionRequest.getTitle());
                    question.setDescription(questionRequest.getDescription());
                    return questionRepository.save(question);
                }).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Question not found with id %b", questionId))
        );
    }

    public ResponseEntity<Object> deleteQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Question not found with id %b", questionId))
                );
    }
}
