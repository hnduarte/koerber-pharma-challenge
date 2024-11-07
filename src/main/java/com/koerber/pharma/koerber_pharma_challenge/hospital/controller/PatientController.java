package com.koerber.pharma.koerber_pharma_challenge.hospital.controller;

import com.koerber.pharma.koerber_pharma_challenge.hospital.dto.PatientSearchRequest;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Consult;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Patient;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.ConsultService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PathologyService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * The type Patient controller.
 */
@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patient", description = "Operations related to patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ConsultService consultService;

    @Autowired
    private PathologyService pathologyService;

    /**
     * Gets filtered patients.
     *
     * @param searchRequest the search request
     * @param page          the page
     * @param size          the size
     * @param sort          the sort
     * @return the filtered patients
     */
    @PostMapping("/search")
    public ResponseEntity<List<Patient>> getFilteredPatients(
            @RequestBody PatientSearchRequest searchRequest,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "age,asc") String[] sort) {

        Sort sorting = Sort.unsorted();
        if (sort.length > 0 && sort[0].contains(",")) {
            String[] sortParams = sort[0].split(",");
            sorting = Sort.by(Sort.Direction.fromString(sortParams[1].trim()), sortParams[0].trim());
        }

        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Patient> patients = patientService.getFilteredPatients(searchRequest.getName(), searchRequest.getMinAge(), pageable);
        return ResponseEntity.ok(patients.getContent());
    }

    /**
     * Gets patient consults and symptoms.
     *
     * @param id the id
     * @return the patient consults and symptoms
     */
    @GetMapping("/{id}/consults-and-symptoms")
    public ResponseEntity<Map<String, Object>> getPatientConsultsAndSymptoms(@PathVariable("id") Long id) {
        Optional<Map<String, Object>> response = patientService.getPatientConsultsAndSymptoms(id);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get all patients list.
     *
     * @return the list
     */
    @GetMapping("/all")
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    /**
     * Gets patient by id.
     *
     * @param id the id
     * @return the patient by id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Patient by ID", description = "Retrieve detailed information about a specific patient by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patient"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<Patient> getPatientById(@Parameter(description = "ID of the patient to retrieve") @PathVariable("id") Long id){
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Create patient response entity.
     *
     * @param patient the patient
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        Patient newPatient = patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
    }

    /**
     * Update patient response entity.
     *
     * @param id      the id
     * @param patient the patient
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        patient.setId(id);
        Patient updatedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    /**
     * Delete patient response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Gets patient consults.
     *
     * @param id the id
     * @return the patient consults
     */
    @GetMapping("/{id}/consults")
    public ResponseEntity<List<Consult>> getPatientConsults(@PathVariable("id") Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Consult> consults = consultService.getConsultsByPatientId(id);
        return ResponseEntity.ok(consults);
    }
}
