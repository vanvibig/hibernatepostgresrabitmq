package com.example.hibernatepostgresrabitmq.rabitmq;

import com.example.hibernatepostgresrabitmq.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class PracticalTipListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = RabbitMQConfig.DEFAULT_PARSING_QUEUE)
    public void consumeDefaultMessage(final Message message) {
        log.info("Receive message with default configuration, tip ís: {}", message.toString());
    }
}
