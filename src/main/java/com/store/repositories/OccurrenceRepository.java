package com.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.entities.Occurrence;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {}