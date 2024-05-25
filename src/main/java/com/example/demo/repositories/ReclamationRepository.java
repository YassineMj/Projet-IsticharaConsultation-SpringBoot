package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ReclamationEntity;

public interface ReclamationRepository extends JpaRepository<ReclamationEntity, Long> {

}
