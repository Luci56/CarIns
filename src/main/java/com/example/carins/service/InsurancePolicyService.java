package com.example.carins.service;

import com.example.carins.mapper.InsurancePolicyMapper;
import com.example.carins.model.Car;
import com.example.carins.model.InsurancePolicy;
import com.example.carins.repo.CarRepository;
import com.example.carins.repo.InsurancePolicyRepository;
import com.example.carins.web.dto.InsurancePolicyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsurancePolicyService {

    //Clasa creata pentru a separa logica de business de restul

    private static final Logger log = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final InsurancePolicyRepository policyRepository;
    private final CarRepository carRepository;
    private final InsurancePolicyMapper mapper;

    public InsurancePolicyService(InsurancePolicyRepository insurancePolicyRepository, CarRepository carRepository, InsurancePolicyMapper mapper) {
        this.carRepository = carRepository;
        this.policyRepository = insurancePolicyRepository;
        this.mapper = mapper;
    }

    //cream o polita noua cu validare de end date
    public InsurancePolicyDto createPolicy(InsurancePolicyDto dto) {
        //verificam ca end date nu e null
        if (dto.endDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date is required");
        }

        if (dto.startDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date is required");
        }

        //Gasim masina al carei asigurare este asigurarea data
        Car car = carRepository.findById(dto.carId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));

        log.info("Saving policy: car={}, provider={}, start={}, end={}",
                car.getId(), dto.provider(), dto.startDate(), dto.endDate());
        //returnam asigurarea valida cu end date
        InsurancePolicy saved = policyRepository.save(mapper.toEntity(dto, car));
        return mapper.toDto(saved);
    }

    //dam update la o polita noua cu validare de end date
    public InsurancePolicyDto updatePolicy(Long policyId, InsurancePolicyDto dto) {


        if (dto.endDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date is required");
        }


        //preluam asigurarea existenta dupa id care ni se da ca si parametru
        InsurancePolicy existing = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Insurance wasn t found"));

        //preluam masina a carei asigurare este
        Car car = carRepository.findById(dto.carId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car wasn t found"));


        existing.setCar(car);
        existing.setProvider(dto.provider());
        existing.setStartDate(dto.startDate());
        existing.setEndDate(dto.endDate());

        InsurancePolicy saved = policyRepository.save(existing);
        return mapper.toDto(saved);


    }

    //listam asigurarile
    public List<InsurancePolicyDto> listPolices() {
        List<InsurancePolicyDto> result = policyRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return result;
    }

    //verificam daca o masina are asigurarea valida la o anumita data
    public boolean isInsuranceValid(Long carID, LocalDate date) {
        if (carID == null || date == null)
            return false;
        return policyRepository.existsActiveOnDate(carID, date);
    }

    //cautam asigurarile fara end date si incercam sa punem un an in plus pt cerinta 1
    public void fixOpenEndedPolicies() {

        //salvam intr o lista toate asigurarile fara end date
        List<InsurancePolicy> policiesEndDateNull = policyRepository.findAll().stream()
                .filter(policy -> policy.getEndDate() == null)
                .collect(Collectors.toList());

        //la fiecare asigurare fara end date adaugam un an in plus incepand de la start date
        for (InsurancePolicy policy : policiesEndDateNull) {
            policy.setEndDate(policy.getStartDate().plusYears(1));
            policyRepository.save(policy);
        }


    }

}
