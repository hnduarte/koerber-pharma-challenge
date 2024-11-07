package com.koerber.pharma.koerber_pharma_challenge.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Schema(description = "Patient entity representing a hospital patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the patient", example = "1")
    private Long id;
    @Schema(description = "Full name of the patient", example = "John Doe")
    private String name;
    @Schema(description = "Age of the patient", example = "30")
    private int age;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Consult> consults = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Pathology> pathologies = new ArrayList<>();

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Consult> getConsults() {
        return consults;
    }

    public void setConsults(List<Consult> consults) {
        this.consults = consults;
    }

    public List<Pathology> getPathologies() {
        return pathologies;
    }

    public void setPathologies(List<Pathology> pathologies) {
        this.pathologies = pathologies;
    }
}
