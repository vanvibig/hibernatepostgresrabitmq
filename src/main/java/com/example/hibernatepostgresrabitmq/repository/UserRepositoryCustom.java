package com.example.hibernatepostgresrabitmq.repository;

import com.example.hibernatepostgresrabitmq.model.User;

import java.util.List;

public interface UserRepositoryCustom {

    List getUserDetails();

    User createUser(User user);

    List getUserDetailsByCriteria();
}
