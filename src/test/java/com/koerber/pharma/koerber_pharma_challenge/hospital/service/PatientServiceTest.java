package com.koerber.pharma.koerber_pharma_challenge.hospital.service;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Patient;
import com.koerber.pharma.koerber_pharma_challenge.hospital.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for Patient service.
 */
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Henrique Duarte");
        patient.setAge(29);
    }

    /**
     * Test get all patients.
     */
    @Test
    void testGetAllPatients() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient));

        List<Patient> patients = patientService.getAllPatients();
        assertEquals(1, patients.size());
        verify(patientRepository, times(1)).findAll();
    }

    /**
     * Test get patient by id.
     */
    @Test
    void testGetPatientById() {
        Patient anotherPatient = new Patient();
        anotherPatient.setId(2L);
        anotherPatient.setName("Ana Maria");
        anotherPatient.setAge(44);
        when(patientRepository.findById(2L)).thenReturn(Optional.of(anotherPatient));

        Optional<Patient> foundPatient = patientService.getPatientById(2L);
        assertTrue(foundPatient.isPresent());
        assertEquals("Ana Maria", foundPatient.get().getName());
        assertEquals(44, foundPatient.get().getAge());
        verify(patientRepository, times(1)).findById(2L);
    }

    /**
     * Test save patient.
     */
    @Test
    void testSavePatient() {
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient savedPatient = patientService.savePatient(patient);
        assertNotNull(savedPatient);
        assertEquals("Henrique Duarte", savedPatient.getName());
        assertEquals(29, savedPatient.getAge());
        verify(patientRepository, times(1)).save(patient);
    }

    /**
     * Test delete patient.
     */
    @Test
    void testDeletePatient() {
        patientService.deletePatient(1L);
        verify(patientRepository, times(1)).deleteById(1L);
    }
}
