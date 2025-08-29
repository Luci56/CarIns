package com.example.carins.mapper;


import com.example.carins.model.InsurancePolicy;
import com.example.carins.web.dto.InsurancePolicyDto;
import org.springframework.stereotype.Component;

// Clasa mapper se ocup cu conversia din entitate in dto

@Component
public class InsurancePolicyMapper {

    // Convertim entitatea în DTO
    public InsurancePolicyDto toDto(InsurancePolicy policy) {
        return new InsurancePolicyDto(
                policy.getId(),
                policy.getCar().getId(),
                policy.getProvider(),
                policy.getStartDate(),
                policy.getEndDate()
        );
    }

    // Convertim DTO-ul în entitate
    public InsurancePolicy toEntity(InsurancePolicyDto dto, com.example.carins.model.Car car) {
        InsurancePolicy policy = new InsurancePolicy();
        policy.setId(dto.id());
        policy.setCar(car);
        policy.setProvider(dto.provider());
        policy.setStartDate(dto.startDate());
        policy.setEndDate(dto.endDate());
        return policy;
    }
}

