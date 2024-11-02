package com.koerber.pharma.koerber_pharma_challenge.hospital.controller;

import com.koerber.pharma.koerber_pharma_challenge.hospital.dto.PatientSearchRequest;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Consult;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Patient;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Symptom;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.ConsultService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PathologyService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ConsultService consultService;

    @Autowired
    private PathologyService pathologyService;

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

    @GetMapping("/{id}/consults-and-symptoms")
    public ResponseEntity<Map<String, Object>> getPatientConsultsAndSymptoms(@PathVariable("id") Long id) {
        Optional<Map<String, Object>> response = patientService.getPatientConsultsAndSymptoms(id);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id){
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        Patient newPatient = patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        patient.setId(id);
        Patient updatedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

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
