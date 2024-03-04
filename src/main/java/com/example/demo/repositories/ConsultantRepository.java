package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ConsultantEntity;

public interface ConsultantRepository extends JpaRepository<ConsultantEntity, Long> {
}