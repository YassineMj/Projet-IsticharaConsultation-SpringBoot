package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class IsticharaConsultationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsticharaConsultationApplication.class, args);
	}

}
