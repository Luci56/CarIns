package com.example.carins.service;

import com.example.carins.model.Car;
import com.example.carins.repo.CarRepository;
import com.example.carins.repo.ClaimRepository;
import com.example.carins.repo.InsurancePolicyRepository;
import com.example.carins.web.dto.CarHistoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//pentru task 2 preluam istoricul masinii
@Service
public class CarHistoryService {

    private final CarRepository carRepository;
    private final InsurancePolicyRepository policyRepository;
    private final ClaimRepository claimRepository;

    public CarHistoryService(CarRepository carRepository,
                             InsurancePolicyRepository policyRepository,
                             ClaimRepository claimRepository) {
        this.carRepository = carRepository;
        this.policyRepository = policyRepository;
        this.claimRepository = claimRepository;
    }

    // Returneaza istoricul complet al unei masini
    public List<CarHistoryDto> getCarHistory(Long carId) {
        // Verificam daca masina exista
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));

        //facem o lista de istoric de masini dto care are practic eventDate eventType si details
        List<CarHistoryDto> history = new ArrayList<>();

        // Adaugam toate asigurarile asociate masinii in istoric ca event type asigurare
        policyRepository.findByCarIdOrderByStartDateAsc(carId)
                .forEach(policy -> {
                    String details = String.format("Policy from %s to %s, Provider: %s",
                            policy.getStartDate(),
                            policy.getEndDate(),
                            policy.getProvider() != null ? policy.getProvider() : "Unknown");
                    history.add(new CarHistoryDto(policy.getStartDate(), "POLICY", details));
                });

        // Adaugam toate claim asociate masinii ca event type Claim
        claimRepository.findByCarIdOrderByClaimDateAsc(carId)
                .forEach(claim -> {
                    String details = String.format("Claim on %s, Amount: %s, Description: %s",
                            claim.getClaimDate(),
                            claim.getAmount(),
                            claim.getDescription());
                    history.add(new CarHistoryDto(claim.getClaimDate(), "CLAIM", details));
                });

        // Sortam lista cronologic dupa data evenimentului
        history.sort(Comparator.comparing(CarHistoryDto::getEventDate));

        return history;
    }
}