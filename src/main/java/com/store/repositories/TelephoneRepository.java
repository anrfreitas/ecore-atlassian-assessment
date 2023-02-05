package com.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.entities.Telephone;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone, Long> {}