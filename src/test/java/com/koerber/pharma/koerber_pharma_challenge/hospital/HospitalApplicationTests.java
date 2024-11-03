package com.koerber.pharma.koerber_pharma_challenge.hospital;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Hospital application tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HospitalApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * Test get all patients' performance.
	 */
	@Test
	void testGetAllPatientsPerformance() {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 1000; i++) {  // Simulate 1000 requests
			ResponseEntity<String> response = restTemplate.getForEntity("/api/patients/all", String.class);
			assertEquals(200, response.getStatusCode().value());
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Performance test completed in " + (endTime - startTime) + " ms");
	}

}
