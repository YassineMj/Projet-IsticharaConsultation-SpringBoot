package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.CategorieEntity;

public interface CategorieRepository extends JpaRepository<CategorieEntity, Long> {
	List<CategorieEntity> findByDomaineIdDomaine(String idDomaine);
}
