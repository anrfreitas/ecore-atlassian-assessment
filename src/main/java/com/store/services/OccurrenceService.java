package com.store.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.store.entities.Occurrence;
import com.store.enums.OrderBy;
import com.store.repositories.OccurrenceRepository;

import net.minidev.json.JSONObject;

@Service
public class OccurrenceService {

    @Autowired
    private OccurrenceRepository oRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public Page<Occurrence> listAll(Integer limit, Integer page, OrderBy orderBy) {
        Sort sort;
        if (orderBy == OrderBy.ASC) {
            sort = Sort.by("id").ascending();
        }
        else sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        return oRepository.findAll(pageable);
    }

    public void generateNewOcurrence() {
        // The following format is acceptable by Timestamp.valueOf() function
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        /*
         * Decided to play with JSONObject intentionally
         * because I wanted to play with trycatch block in the consumer :)
         */
        JSONObject obj = new JSONObject();
        obj.put("dt", formatter.format(date));
        rabbitTemplate.convertAndSend(this.queue.getName(), obj);
    }
}
