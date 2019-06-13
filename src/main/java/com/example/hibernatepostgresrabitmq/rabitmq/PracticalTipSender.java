package com.example.hibernatepostgresrabitmq.rabitmq;

import com.example.hibernatepostgresrabitmq.HibernatepostgresrabitmqApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
                "Always use Immutable classes in java",
                1,
                false
        );

        rabbitTemplate.convertAndSend(
                HibernatepostgresrabitmqApplication.EXCHANGE_NAME,
                HibernatepostgresrabitmqApplication.ROUTING_KEY,
                tip
        );

        log.info("Practical Tip sent");
    }
}
