package com.example.carins;

import com.example.carins.model.Car;
import com.example.carins.model.Owner;
import com.example.carins.repo.CarRepository;
import com.example.carins.repo.OwnerRepository;
import com.example.carins.service.CarService;
import com.example.carins.service.InsurancePolicyService;
import com.example.carins.web.dto.InsurancePolicyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CarInsuranceApplicationTests {


    @Autowired
    CarService carService;

    @Autowired
    InsurancePolicyService insuranceService;


    //testam sa vedem daca putem crea o asigurare fara end date
    @Test
    void createPolicy_withoutEndDate() {
        InsurancePolicyDto dto = new InsurancePolicyDto(null, 1L, "TestProvider",
                LocalDate.of(2025, 1, 1), null);

        //ne asteptam sa primim un cod 400
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> insuranceService.createPolicy(dto));

        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("End date is required"));


    }

    //teste pentru verificarea asigurarii
    @Test
    void testValidInsurance() {
        // veriifcam o data valida din intervalul primei asigurarii masinii cu id 1
        boolean valid1 = carService.isInsuranceValid(1L, "2024-06-01");
        // veriifcam o data valida din intervalul cel de al doilea a asigurarii masinii cu id 1
        boolean valid2 = carService.isInsuranceValid(1L, "2025-06-01");

        assertTrue(valid1, "Asigurarea din 2024 ar trebui sa fie valida");
        assertTrue(valid2, "Asigurarea din 2025 ar trebui să fie valida");
    }

    //verificam daca afiseaza bine pentru dati inafara intervalului asigurarii
    @Test
    void testInvalidInsurance() {
        // masina cu id 2 are asigurarea doar intre 2025-03-01 și 2025-09-30
        boolean validBefore = carService.isInsuranceValid(2L, "2025-02-01"); // data inainte datei start
        boolean validAfter = carService.isInsuranceValid(2L, "2025-10-01");  // data de dupa end date

        assertFalse(validBefore, "Asiguarrea inainte de start nu ar trebui sa fie valida");
        assertFalse(validAfter, "Asigurarea dupa end nu ar trebui sa fie valida");
    }

    //teste pentru validari
    @Test
    void testCarNotFound() {
        // masina cu id 100 nu exista deci ar trebui sa dea 404
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> carService.isInsuranceValid(100L, "2025-06-01"));
        //verifiacm daca da 404 si Car with given Id doesn t exists in data base motivul poe care l am dat in car service
        assertEquals(404, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("Car with given Id doesn t exists in data base"));
    }

    @Test
    void testInvalidDateFormat() {
        // Data invalida ca e cu / in loc de - ar trebui sa primim 400
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> carService.isInsuranceValid(1L, "2025/06/01"));

        //mergem pe premiza ca primim 400 si motivul invalid date format
        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("Invalid date format"));
    }

    @Test
    void testImpossibleDate() {
        // Data imposibila mai mare de anul 2050
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> carService.isInsuranceValid(1L, "2050-01-01"));

        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("The date given is imposible"));
    }

}


