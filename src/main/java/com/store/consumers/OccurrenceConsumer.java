package com.store.consumers;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.store.entities.Occurrence;
import com.store.repositories.OccurrenceRepository;

import net.minidev.json.JSONObject;

@Component
public class OccurrenceConsumer {

    @Autowired
    private OccurrenceRepository oRepository;

    private static final Logger LOGGER =
        LoggerFactory.getLogger(OccurrenceConsumer.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload JSONObject payload) {
        String output = oRepository.save(this.getOcurrence(payload)).toString();
        LOGGER.info(output);
    }

    private Occurrence getOcurrence(JSONObject payload) {
        try {
            Timestamp createdAt = Timestamp.valueOf(payload.get("dt").toString());
            return new Occurrence(createdAt);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage().toString());
        }

        return new Occurrence();
    }
}
