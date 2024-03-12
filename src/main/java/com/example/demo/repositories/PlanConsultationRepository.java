package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.PlanConsultationEntity;

public interface PlanConsultationRepository extends JpaRepository<PlanConsultationEntity,Long>{
}
