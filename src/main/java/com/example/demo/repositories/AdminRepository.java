package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.example.demo.entities.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

	Optional<AdminEntity> findByEmail(String email);

	AdminEntity findById(long id);
}