package com.koerber.pharma.koerber_pharma_challenge.hospital.dto;

public class PatientSearchRequest {

    private String name;
    private Integer minAge;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }
}
