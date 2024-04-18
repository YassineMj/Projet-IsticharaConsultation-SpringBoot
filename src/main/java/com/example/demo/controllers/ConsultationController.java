package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ConsultationEntity;
import com.example.demo.requests.ConsultationRequest;
import com.example.demo.services.ConsultationService;

import org.springframework.data.domain.PageRequest;




@RestController
@RequestMapping("IsticharaConsultation/api/consultation")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultationController {

	@Autowired
	private ConsultationService consultationService;

	@PostMapping("ajouter-consultation/{idConsultant}")
	public ResponseEntity<ConsultationEntity> ajouterConsultation(@PathVariable String idConsultant , @RequestBody ConsultationRequest consultation) {
	    return consultationService.ajouterConsultation(idConsultant , consultation);

	}
	
	@PutMapping("modifier-consultation/{idConsultant}")
	public ResponseEntity<ConsultationEntity> modifierConsultation(@PathVariable String idConsultant , @RequestBody ConsultationRequest consultation) {
	    return consultationService.modifierConsultation(idConsultant , consultation);

	}

	@GetMapping("/{idConsultant}")
	public ResponseEntity<ConsultationEntity> getConsultationById(@PathVariable String idConsultant) {
	    ConsultationEntity consultation = consultationService.getConsultationByIdConsultant(idConsultant);
	    if (consultation == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<>(consultation, HttpStatus.OK);
	}
	
	@GetMapping("get-consultation/{idConsultation}")
	public ResponseEntity<ConsultationEntity> getConsultationByIdConsultation(@PathVariable String idConsultation) {
	    ConsultationEntity consultation = consultationService.getConsultationByIdConsultation(idConsultation);
	    if (consultation == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<>(consultation, HttpStatus.OK);
	}
	
	@GetMapping("/domaine/{idDomaine}/specialisation/{specialisation}")
	public Page<ConsultationEntity> getConsultationsByDomaineAndSpecialisation(
	        @PathVariable String idDomaine,
	        @PathVariable String specialisation,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size) {
	    org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
	    return consultationService.getConsultationsByIdDomaineAndSpecialisation(idDomaine, specialisation, pageable);
	}
    
 }
