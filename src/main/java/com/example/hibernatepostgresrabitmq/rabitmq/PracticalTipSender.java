package com.example.hibernatepostgresrabitmq.rabitmq;

import com.example.hibernatepostgresrabitmq.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PracticalTipSender {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate rabbitTemplate;

    public PracticalTipSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 3000L)
    public void sendPracticalTip() {
        PracticalTipMessage tip = new PracticalTipMessage(
                "Hello there!",
                new Random().nextInt(50),
                false
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                tip
        );

        log.info("Practical Tip sent");
    }
}
