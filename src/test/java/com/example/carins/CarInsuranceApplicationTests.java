package com.example.carins;

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

    @Test
    void insuranceValidityBasic() {
        assertTrue(carService.isInsuranceValid(12L, "2024-06-01"));
        assertTrue(carService.isInsuranceValid(12L, "2025-06-01"));
        assertFalse(carService.isInsuranceValid(13L, "2025-02-01"));

    }

    @Test
    void createPolicy_withValidEndDate_succeeds() {


        //cream o asigurare noua
        InsurancePolicyDto dto = new InsurancePolicyDto(null, 12L, "TestProvider",
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31));

        //cream asigurare prin service
        InsurancePolicyDto saved = insuranceService.createPolicy(dto);

        //ne asiguram ca id ul nu e null
        assertNotNull(saved.id());

        //ne asiguram ca provider si car id sunt ok
        assertEquals("TestProvider", saved.provider());
        assertEquals(12L, saved.carId());


    }

    @Test
    void createPolicy_withoutEndDate() {
        InsurancePolicyDto dto = new InsurancePolicyDto(null, 12L, "TestProvider",
                LocalDate.of(2025, 1, 1), null);

        //ne asteptam sa primim un cod 400
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> insuranceService.createPolicy(dto));

        assertEquals(400, ex.getStatusCode());
        assertTrue(ex.getReason().contains("End date is required"));


    }

    //teste pentru verificarea asigurarii
    @Test
    void testValidInsurance() {
        // Car ID 1 are asigurarea între 2024-01-01 și 2024-12-31 și 2025-01-01 - 2025-12-31
        boolean valid1 = carService.isInsuranceValid(1L, "2024-06-01");
        boolean valid2 = carService.isInsuranceValid(1L, "2025-06-01");

        assertTrue(valid1, "Asigurarea din 2024 ar trebui sa fie valida");
        assertTrue(valid2, "Asigurarea din 2025 ar trebui sa fie valida");
    }

    @Test
    void testInvalidInsurance() {
        // Car ID 2 are asigurarea doar între 2025-03-01 și 2025-09-30
        boolean validBefore = carService.isInsuranceValid(2L, "2025-02-01"); // inainte de start
        boolean validAfter = carService.isInsuranceValid(2L, "2025-10-01");  // dupa end

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


