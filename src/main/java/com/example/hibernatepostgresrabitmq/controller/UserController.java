package com.example.hibernatepostgresrabitmq.controller;

import com.example.hibernatepostgres.model.User;
import com.example.hibernatepostgres.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @GetMapping()
    public ResponseEntity<?> userDetails() {
        List usersDetail = userService.getUserDetails();
        return new ResponseEntity<>(usersDetail, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userService.getUserBYId(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/session/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInCache(@PathVariable Integer id) {
        User user = userService.getUserInCache(User.class, new Integer(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/cache-level2/{id}")
    public ResponseEntity<?> cacheLevel2(@PathVariable Integer id) {
        User user = userService.getUserBYId(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/criteria")
    @Transactional(readOnly = true)
    public ResponseEntity<?> userDetailsByCriteria() {
        List usersDetail = userService.getUserDetailsByCriteria();
        return new ResponseEntity<>(usersDetail, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User res = userService.createUser(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
