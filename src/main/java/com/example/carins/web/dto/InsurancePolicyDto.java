package com.example.carins.web.dto;


import java.time.LocalDate;

// Am creat acest record DTO pentru a transmite datele despre asigurare între API și Service
// fără a expune direct entitățile JPA, ceea ce este o practică bună pentru producție.
// Rețineți că stocăm doar ID-ul mașinii și nu obiectul Car complet.
public record InsurancePolicyDto(
        Long id,
        Long carId,
        String provider,
        LocalDate startDate,
        LocalDate endDate
) {
}
