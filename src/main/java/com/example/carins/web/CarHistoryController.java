package com.example.carins.web;

import com.example.carins.service.CarHistoryService;
import com.example.carins.web.dto.CarHistoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarHistoryController {

    private final CarHistoryService historyService;

    public CarHistoryController(CarHistoryService historyService) {
        this.historyService = historyService;
    }

    // Endpoint GET pentru istoricul masinii practic apel;ez meotda din service
    @GetMapping("/{carId}/history")
    public ResponseEntity<List<CarHistoryDto>> getCarHistory(@PathVariable Long carId) {
        // Apelez service-ul pentru a obtine istoricul
        List<CarHistoryDto> history = historyService.getCarHistory(carId);

        // Returnez lista cu status 200 OK
        return ResponseEntity.ok(history);
    }
}
