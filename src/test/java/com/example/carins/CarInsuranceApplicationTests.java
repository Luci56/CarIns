package com.example.carins;

import com.example.carins.model.Car;
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
    CarService service;

    @Autowired
    InsurancePolicyService insuranceService;

    @Test
    void insuranceValidityBasic() {
        assertTrue(service.isInsuranceValid(12L, LocalDate.parse("2024-06-01")));
        assertTrue(service.isInsuranceValid(12L, LocalDate.parse("2025-06-01")));
        assertFalse(service.isInsuranceValid(13L, LocalDate.parse("2025-02-01")));

    }

    @Test
    void createPolicy_withValidEndDate_succeeds(){


      //cream o asigurare noua
        InsurancePolicyDto dto = new InsurancePolicyDto(null, 12L, "TestProvider",
                LocalDate.of(2025,1,1), LocalDate.of(2025,12,31));

        //cream asigurare prin service
        InsurancePolicyDto saved = insuranceService.createPolicy(dto);

        //ne asiguram ca id ul nu e null
        assertNotNull(saved.id());

        //ne asiguram ca provider si car id sunt ok
        assertEquals("TestProvider", saved.provider());
        assertEquals(12L, saved.carId());


    }

    @Test
    void createPolicy_withoutEndDate(){
        InsurancePolicyDto dto = new InsurancePolicyDto(null,12L,"TestProvider",
                LocalDate.of(2025,1,1), null);

        //ne asteptam sa primim un cod 400
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,() -> insuranceService.createPolicy(dto));

        assertEquals(400,ex.getStatusCode());
        assertTrue(ex.getReason().contains("End date is required"));


    }
}
