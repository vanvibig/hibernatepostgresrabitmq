package com.example.hibernatepostgresrabitmq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class NumberService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    // ...
    @Cacheable(
            value = "squareCache",
            key = "#number",
            condition = "#number>10")
    public int square(int number) {
        int square = number*number;
        log.info("square of {} is {}", number, square);
        return square;
    }
}
