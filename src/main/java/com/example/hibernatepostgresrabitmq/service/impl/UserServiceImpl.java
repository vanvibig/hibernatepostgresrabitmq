package com.example.hibernatepostgresrabitmq.service.impl;

import com.example.hibernatepostgres.model.User;
import com.example.hibernatepostgres.repository.UserRepository;
import com.example.hibernatepostgres.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
//@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List getUserDetails() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List getUserDetailsByCriteria() {
        return userRepository.getUserDetailsByCriteria();
    }

    @CacheEvict(allEntries = true)
    public void clearCache(){}

    @Override
    @Cacheable(value = "users")
    public User getUserBYId(int id) {
        this.clearCache();
        System.out.println(String.format("get user by id %d", id));
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserInCache(Class<?> theClass, Integer id) {
        int i = 5;
        while (i > 0) {
            Session session = entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
            User user = session.load(User.class, id);
            System.out.println(user.getEmail());
//            session.evict(user);
            i--;
        }
        return null;
    }
}
