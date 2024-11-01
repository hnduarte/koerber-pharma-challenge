package com.koerber.pharma.koerber_pharma_challenge.hospital.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Consult> consults = new ArrayList<>();

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
}
