package com.example.hibernatepostgresrabitmq;

import com.example.hibernatepostgresrabitmq.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableCaching
@EnableScheduling
public class HibernatepostgresrabitmqApplication implements CommandLineRunner {

    public static final String EXCHANGE_NAME = "tiptx";
    public static final String DEFAULT_PARSING_QUEUE = "default_parser_q";
    public static final String ROUTING_KEY = "tips";

    @Bean
    public TopicExchange tipsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue defaultParsingQueue() {
        return new Queue(DEFAULT_PARSING_QUEUE);
    }

    @Bean
    public Binding queueToExchangeBinding() {
        return BindingBuilder.bind(defaultParsingQueue())
                .to(tipsExchange())
                .with(ROUTING_KEY);
    }

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
