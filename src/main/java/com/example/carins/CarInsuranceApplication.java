package com.example.carins;

import com.example.carins.service.InsurancePolicyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarInsuranceApplication {
    public static void main(String[] args) {

        SpringApplication.run(CarInsuranceApplication.class, args);
    }
}