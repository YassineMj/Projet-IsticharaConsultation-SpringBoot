package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ActivityConsultantEntity;
import com.example.demo.entities.ActivityLogEntity;

public interface ActivityConsultantRepository extends JpaRepository<ActivityConsultantEntity, Long> {
    List<ActivityConsultantEntity> findByConsultantId(Long consultantId);

}
