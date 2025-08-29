package com.example.carins.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

//Entitate pentru a doua cerinta pentru a inregistra o colecatre de asigurare
@Entity
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID unic generat automat

    // Relatie ManyToOne cu Car un claim apartine unei masini
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    // Data evenimentului pentru care se face claim
    @NotNull(message = "Claim date cannot be null")
    private LocalDate claimDate;

    // Descrierea evenimentului
    @NotBlank(message = "Description cannot be blank")
    private String description;

    // Suma ceruta pentru dauna
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    public @NotNull(message = "Amount cannot be null") @Positive(message = "Amount must be positive") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Amount cannot be null") @Positive(message = "Amount must be positive") BigDecimal amount) {
        this.amount = amount;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public @NotNull(message = "Claim date cannot be null") LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(@NotNull(message = "Claim date cannot be null") LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public @NotBlank(message = "Description cannot be blank") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description cannot be blank") String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

