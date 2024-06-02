package com.example.demo.requests;

import java.time.LocalDateTime;

public class ActivityConsultantRequest {

	private String action;
    private String description;
    private LocalDateTime date;
    private long idConsultant;
    
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public long getIdConsultant() {
		return idConsultant;
	}
	public void setIdConsultant(long idConsultant) {
		this.idConsultant = idConsultant;
	}
    
}
