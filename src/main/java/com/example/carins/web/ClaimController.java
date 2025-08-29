package com.example.carins.web;


import com.example.carins.model.Claim;
import com.example.carins.service.ClaimService;
import com.example.carins.web.dto.ClaimDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/cars")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping("/{carId}/claims")
    public ResponseEntity<Claim> createClaim(
            @PathVariable Long carId,
            @Valid @RequestBody ClaimDto dto) {

        Claim savedClaim = claimService.createClaim(carId, dto);

        return ResponseEntity
                .created(URI.create("/api/cars/" + carId + "/claims/" + savedClaim.getId()))
                .body(savedClaim);
    }
}
