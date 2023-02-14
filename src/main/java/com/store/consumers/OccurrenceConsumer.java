package com.store.consumers;

import java.nio.charset.StandardCharsets;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.store.consumers.events.NewOccurrenceEvent;

import net.minidev.json.JSONObject;

@Component
public class OccurrenceConsumer {

    @Autowired
    private NewOccurrenceEvent newOccurrenceEvent;

    private static final Logger LOGGER =
        LoggerFactory.getLogger(OccurrenceConsumer.class);

    @RabbitListener(queues = "${spring.rabbitmq.occurences_queue}")
    public void listen(Message message, @Payload JSONObject payload) {
        LOGGER.info("occurences.# - Event received...");

        String routingKey = message.getMessageProperties().getReceivedRoutingKey();

        switch (routingKey) {
            case "occurences.new": newOccurrenceEvent.execute(payload);
        }
    }
}
