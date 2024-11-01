package com.koerber.pharma.koerber_pharma_challenge.hospital.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Pathology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "pathology", cascade = CascadeType.ALL)
    private List<Symptom> symptoms = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
}
