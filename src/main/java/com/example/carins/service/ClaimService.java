package com.example.carins.service;

import com.example.carins.model.Car;
import com.example.carins.model.Claim;
import com.example.carins.repo.CarRepository;
import com.example.carins.repo.ClaimRepository;
import com.example.carins.web.dto.ClaimDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClaimService {

    private final CarRepository carRepository;
    private final ClaimRepository claimRepository;

    public ClaimService(CarRepository carRepository, ClaimRepository claimRepository) {
        this.carRepository = carRepository;
        this.claimRepository = claimRepository;
    }

    // Creeaza un claim pentru o masina
    public Claim createClaim(Long carId, ClaimDto dto) {
        // Verificam daca masina exista daca nu aruncam 404
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));

        // Cream claim-ul
        Claim claim = new Claim();
        claim.setCar(car);
        claim.setClaimDate(dto.getClaimDate());
        claim.setDescription(dto.getDescription());
        claim.setAmount(dto.getAmount());

        // Salvam in baza de date
        return claimRepository.save(claim);
    }

    //folosim pentru get claimss
    public List<Claim> getClaimsForCar(Long carId) {
        return claimRepository.findByCarId(carId);
    }

}
