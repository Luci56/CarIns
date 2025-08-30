package com.example.carins.service;

import com.example.carins.model.Car;
import com.example.carins.repo.CarRepository;
import com.example.carins.repo.InsurancePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private final CarRepository carRepository;
    private final InsurancePolicyRepository policyRepository;

    public CarService(CarRepository carRepository, InsurancePolicyRepository policyRepository) {
        this.carRepository = carRepository;
        this.policyRepository = policyRepository;
    }

    public List<Car> listCars() {
        return carRepository.findAll();
    }

    public boolean isInsuranceValid(Long carId, String dateString) {
        //facem validare ca masina exista in baza de date
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with given Id doesn t exists in data base"));

        //facem validarea parametrului date il trecem din string in localDate si aruncam exceptie daca nu e bun
        LocalDate queryDate;
        try {
            queryDate = LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }

        //validam si date imposibile
        if (queryDate.isAfter(LocalDate.of(2030, 12, 31)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date given is imposible");


        //daca nu se arunca nici o exceptie atunci returna, adevarat sau fals daca e actyiva la data sau nu
        return policyRepository.existsActiveOnDate(carId, queryDate);
    }
}

