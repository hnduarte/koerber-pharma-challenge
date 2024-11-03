package com.koerber.pharma.koerber_pharma_challenge.hospital.controller;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Pathology;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Patient;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Symptom;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PathologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Pathology controller.
 */
@RestController
@RequestMapping("/api/patients/{patientId}/pathologies")
public class PathologyController {

    @Autowired
    private PathologyService pathologyService;

    /**
     * Gets pathologies by patient id.
     *
     * @param patientId the patient id
     * @return the pathologies by patient id
     */
    @GetMapping
    public ResponseEntity<List<Pathology>> getPathologiesByPatientId(@PathVariable("patientId") Long patientId) {
        List<Pathology> pathologies = pathologyService.getPathologiesByPatientId(patientId);
        return ResponseEntity.ok(pathologies);
    }

    /**
     * Add pathology response entity.
     *
     * @param patientId the patient id
     * @param pathology the pathology
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Pathology> addPathology(@PathVariable("patientId") Long patientId, @RequestBody Pathology pathology) {
        Patient patient = new Patient();
        patient.setId(patientId);
        pathology.setPatient(patient);

        // Check if pathology has symptoms
        if (pathology.getSymptoms() != null) {
            for (Symptom symptom : pathology.getSymptoms()) {
                symptom.setPathology(pathology);
            }
        }

        Pathology savedPathology = pathologyService.addPathology(pathology);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPathology);
    }

    /**
     * Delete pathology response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePathology(@PathVariable("id") Long id) {
        pathologyService.deletePathology(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
