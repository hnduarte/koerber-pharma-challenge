package com.koerber.pharma.koerber_pharma_challenge.hospital.repository;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Pathology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathologyRepository extends JpaRepository<Pathology, Long> {
    List<Pathology> findByPatientId(Long patientId);
}
