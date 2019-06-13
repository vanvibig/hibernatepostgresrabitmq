package com.example.hibernatepostgresrabitmq.repository.impl;

import com.example.hibernatepostgresrabitmq.model.User;
import com.example.hibernatepostgresrabitmq.repository.UserRepositoryCustom;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<User> getUserDetails() {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root contactRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(contactRoot);

        return session.createQuery(criteriaQuery).getResultList();
    }

    public List<User> getUserDetailsByCriteria() {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(User.class);

        Root<?> contactRoot = criteria.from(User.class);

        Predicate ltPredicate = builder.lt(contactRoot.get("id"), 6);
        Predicate nePredicate = builder.notEqual(contactRoot.get("id"), 3);
        Predicate btPredicate = builder.between(contactRoot.get("id"), 2, 4);

        criteria.where(ltPredicate, nePredicate, btPredicate);

        criteria.select(contactRoot);

        return session.createQuery(criteria).getResultList();
    }

    @Override
    public User createUser(User user) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        session.save(user);
        return user;
    }
}
