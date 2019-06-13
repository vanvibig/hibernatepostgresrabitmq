package com.example.hibernatepostgresrabitmq.controller;

import com.example.hibernatepostgresrabitmq.service.NumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/number")
public class NumberController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    // ...

    @Autowired
    private NumberService numberService;

    @GetMapping(path = "/square/{number}")
    public String getSquare(@PathVariable int number) {
        log.info("call numberService to square {}", number);
        return String.format("{\"square\": %s}", numberService.square(number));
    }
}
