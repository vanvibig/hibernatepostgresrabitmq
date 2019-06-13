package com.example.hibernatepostgresrabitmq.controller;

import com.example.hibernatepostgres.model.Question;
import com.example.hibernatepostgres.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/questions")
/**
 * cache level 1, 2, transaction noted
 */
public class QuestionController {

    @GetMapping("/hello")
    public String hello() {
        return "World";
    }

    public void demoException() throws Exception {
        // do something
        throw new Exception("demo throw exception");
    }

    @Autowired
    private QuestionService questionService;

    /**
     * localhost:8080/questions?page=0&size=2&sort=createdAt,desc
     */
    @GetMapping()
    @Transactional(readOnly = true)
    public Page<Question> getQuestions(Pageable pageable) {
        return questionService.getQuestion(pageable);
    }

    /**
     * localhost:8080/questions/
     */
    @PostMapping()
    @Transactional(rollbackFor = Exception.class)
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @PutMapping("/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId,
                                   @Valid @RequestBody Question questionRequest) {
        return questionService.updateQuestion(questionId, questionRequest);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        return questionService.deleteQuestion(questionId);
    }
}
