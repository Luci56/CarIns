package com.example.carins.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

//Entitate pentru a doua cerinta pentru a inregistra o colecatre de asigurare
@Entity
@Table(name = "claim")//punem tabel in baza de date ca e entitate
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID unic generat automat

    // Relatie ManyToOne cu Car un claim apartine unei masini
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnore
    // Evitam serializarea lui car pentru JSON, deoarece Claim are un Car
    // iar Car are un Owner si ar putea cauza erori de serializare Lazy proxies
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

    public Claim() {

    }

    public Claim(Car car, LocalDate claimDate, String description, BigDecimal amount) {
        this.car = car;
        this.claimDate = claimDate;
        this.description = description;
        this.amount = amount;
    }

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

