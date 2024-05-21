package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.example.demo.entities.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

	Optional<AdminEntity> findByEmail(String email);

	AdminEntity findById(long id);
	
	@Query("SELECT a.nomComplet FROM AdminEntity a WHERE a.idFireBase = :idFireBase")
    String findNomCompletByIdFireBase(@Param("idFireBase") String idFireBase);
	

}