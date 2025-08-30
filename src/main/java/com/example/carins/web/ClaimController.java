package com.example.carins.web;


import com.example.carins.model.Claim;
import com.example.carins.service.ClaimService;
import com.example.carins.web.dto.ClaimDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    //cream un claim
    @PostMapping("/{carId}/claims")
    public ResponseEntity<Claim> createClaim(
            @PathVariable Long carId,
            @Valid @RequestBody ClaimDto dto) {

        Claim savedClaim = claimService.createClaim(carId, dto);

        return ResponseEntity
                .created(URI.create("/api/cars/" + carId + "/claims/" + savedClaim.getId()))
                .body(savedClaim);
    }


    //vedem toate claim urile unei masini dupa id
    @GetMapping("/{carId}/claims")
    public ResponseEntity<List<Claim>> getClaimsForCar(@PathVariable Long carId) {
        List<Claim> claims = claimService.getClaimsForCar(carId);
        if (claims.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(claims);
    }
}
