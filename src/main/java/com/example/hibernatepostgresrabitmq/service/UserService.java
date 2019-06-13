package com.example.hibernatepostgresrabitmq.service;

import com.example.hibernatepostgres.model.User;

import java.util.List;

public interface UserService {

    List getUserDetails();

    User createUser(User user);

    List getUserDetailsByCriteria();

    User getUserBYId(int id);

    User getUserInCache(Class<?> theClass, Integer id);
}
