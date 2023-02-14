package com.store.consumers.events;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.consumers.OccurrenceConsumer;
import com.store.entities.Occurrence;
import com.store.repositories.OccurrenceRepository;

import net.minidev.json.JSONObject;

@Component
public class NewOccurrenceEvent {

    @Autowired
    private OccurrenceRepository oRepository;

    private static final Logger LOGGER =
        LoggerFactory.getLogger(OccurrenceConsumer.class);

    public void execute(JSONObject payload) {
        LOGGER.info("occurences.new - Event received...");
        oRepository.save(this.getOcurrence(payload)).toString();
        LOGGER.info("New occcurrence created!");
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
