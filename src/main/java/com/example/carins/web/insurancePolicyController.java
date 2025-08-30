package com.example.carins.web;

import com.example.carins.service.InsurancePolicyService;
import com.example.carins.web.dto.InsurancePolicyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance") //toate endpointurile vor incepe asa
public class insurancePolicyController {

    @Autowired
    private final InsurancePolicyService service;

    public insurancePolicyController(InsurancePolicyService service) {
        this.service = service;
    }

    //endpoint pentru listarea tuturor asigurarilor
    @GetMapping
    public List<InsurancePolicyDto> listAll() {
        return service.listPolices();
    }

    //endpoint care creaza asigurari
    //se valideaza obligatoriu end date retureaza err 400 bad request daca nu e
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsurancePolicyDto createPolicy(@RequestBody InsurancePolicyDto dto) {
        return service.createPolicy(dto);
    }

    //endpoint de put pentru actualizarea unei asigurari dupa id
    @PutMapping("/{id}")
    public InsurancePolicyDto updatePolicy(@PathVariable Long id, @RequestBody InsurancePolicyDto dto) {
        dto = new InsurancePolicyDto(id, dto.carId(), dto.provider(), dto.startDate(), dto.endDate());
        return service.updatePolicy(id, dto);
    }


}
