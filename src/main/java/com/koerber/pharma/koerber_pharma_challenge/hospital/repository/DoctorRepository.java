package com.koerber.pharma.koerber_pharma_challenge.hospital.repository;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
