package com.example.carins;

import com.example.carins.service.InsurancePolicyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
//adnotare pentru cron activeaza task urile programate practic spune springului sa cvaute metode cu adnotarea @Scheduled
@SpringBootApplication
public class CarInsuranceApplication {
    public static void main(String[] args) {

        SpringApplication.run(CarInsuranceApplication.class, args);
    }
}