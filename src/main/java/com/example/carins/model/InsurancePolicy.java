package com.example.carins.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull; //import pentru validare la API @NotNull

@Entity
@Table(name = "insurancepolicy")
public class InsurancePolicy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Car car;

    private String provider;


    private LocalDate startDate;

    //ne asiguram ca toate asigurarile au un end date cerinta 1
    @NotNull(message = "End date is Required") //validare la APi cu mesaj daca se incearca crearea fara end date a unei asigurari
    @Column( nullable = false) //validare si pentru baza de date
    private LocalDate endDate; // nullable == open-ended

    public InsurancePolicy() {}
    public InsurancePolicy(Car car, String provider, LocalDate startDate, LocalDate endDate) {
        this.car = car; this.provider = provider; this.startDate = startDate; this.endDate = endDate;
    }

    public Long getId() { return id; }
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public void setId(Long id) {
        this.id = id;
    }
}
