package com.example.carins.web.dto;

import java.time.LocalDate;

//am creat acest dto pentru a rezolva cerinta 3 si anume sa putem apela GET /api/cars/{carId}/insurance-valid` sa vedem daca asigurarea unei masini este valabila sau nu
public class InsuranceValidityResponse {
    private long carID;
    private LocalDate date;
    private boolean valid;

    public InsuranceValidityResponse(long carID, LocalDate date, boolean valid) {
        this.carID = carID;
        this.date = date;
        this.valid = valid;
    }

    public long getCarID() {
        return carID;
    }

    public void setCarID(long carID) {
        this.carID = carID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
