package com.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.store.entities.Occurrence;
import com.store.enums.OrderBy;
import com.store.services.OccurrenceService;

@RestController
@RequestMapping("/occurrence")
public class OccurrenceController {

    @Autowired
    private OccurrenceService occurrenceService;

    @GetMapping(
        value = "",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Page<Occurrence> list(
        @RequestParam(name = "limit") Integer limit,
        @RequestParam(name = "page") Integer page,
        @RequestParam(name = "orderBy") String orderBy
    ) {
        return occurrenceService.listAll(limit, page, getOrderByOption(orderBy));
    }

    private OrderBy getOrderByOption(String key) {
        if (key.toLowerCase().equals("asc")) return OrderBy.ASC;
        return OrderBy.DESC;
    }
}