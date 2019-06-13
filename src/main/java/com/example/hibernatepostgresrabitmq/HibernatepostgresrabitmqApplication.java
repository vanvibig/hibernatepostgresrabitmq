package com.example.hibernatepostgresrabitmq;

import com.example.hibernatepostgresrabitmq.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableCaching
public class HibernatepostgresrabitmqApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HibernatepostgresrabitmqApplication.class, args);
    }

    private static Logger log = LoggerFactory.getLogger(HibernatepostgresrabitmqApplication.class);

    @Autowired
    private MusicService musicService;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void run(String... args) throws Exception {
        log.info("Spring Boot Ehcache 2 Caching Example Configuration");
        log.info("using cache manager: " + cacheManager.getClass().getName());

        musicService.clearCache();

        play("trombone");
        play("guitar");
        play("trombone");
        play("bass");
        play("trombone");
    }

    private void play(String instrument){
        log.info("Calling: " + MusicService.class.getSimpleName() + ".play(\"" + instrument + "\");");
        musicService.play(instrument);
    }

}
