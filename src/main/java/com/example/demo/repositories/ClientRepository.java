package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

}
