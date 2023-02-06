package com.store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.store.entities.Occurrence;
import com.store.enums.OrderBy;
import com.store.repositories.OccurrenceRepository;

@Service
public class OccurrenceService {

    @Autowired
    private OccurrenceRepository oRepository;

    public Page<Occurrence> listAll(Integer limit, Integer page, OrderBy orderBy) {
        Sort sort;
        if (orderBy == OrderBy.ASC) {
            sort = Sort.by("id").ascending();
        }
        else sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        return oRepository.findAll(pageable);
    }
}
