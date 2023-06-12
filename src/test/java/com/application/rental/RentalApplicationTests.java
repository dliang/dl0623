package com.application.rental;

import com.application.rental.exception.BadRequestException;
import com.application.rental.service.RentalService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

@SpringBootTest
@Testcontainers
class RentalApplicationTests {

	@Autowired
	private RentalService rentalService;

	@Container
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
			.withUsername("postgres")
			.withPassword("postgres");

	@DynamicPropertySource
	static void postgreSQLProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Test
	public void testRentalCase_1() {
		// JAKR, 9/3/15, 5, 101
		Exception exception = Assert.assertThrows(BadRequestException.class, () -> rentalService.checkout(
				"JAKR", 5, 101, LocalDate.parse("2015-09-03")));

		Assertions.assertTrue(exception.getMessage().contains("Discount Percent value of: 101 must be between 0 - 100"));
	}

	@Test
	public void testRentalCase_2() {
		// LADW, 7/2/20, 3, 10%
		var rentalAgreement = rentalService.checkout(
				"LADW", 3, 10, LocalDate.parse("2020-07-02"));
		Assertions.assertEquals("LADW", rentalAgreement.getToolCode());
		Assertions.assertEquals("Ladder", rentalAgreement.getToolType());
		Assertions.assertEquals("Werner", rentalAgreement.getToolBrand());
		Assertions.assertEquals(3, rentalAgreement.getRentalDays());
		Assertions.assertEquals(LocalDate.parse("2020-07-02"), rentalAgreement.getCheckoutDate());
		Assertions.assertEquals(LocalDate.parse("2020-07-05"), rentalAgreement.getDueDate());
		Assertions.assertEquals(1.99, rentalAgreement.getDailyRentalCharge());
		Assertions.assertEquals(2, rentalAgreement.getChargeDays());
		Assertions.assertEquals(3.98, rentalAgreement.getPrediscountCharge());
		Assertions.assertEquals(10, rentalAgreement.getDiscountPercent());
		Assertions.assertEquals(0.4, rentalAgreement.getDiscountAmount());
		Assertions.assertEquals(3.58, rentalAgreement.getFinalCharge());
	}


	@Test
	public void testRentalCase_3() {
		// CHNS, 7/2/15, 5, 25%
		var rentalAgreement = rentalService.checkout(
				"CHNS", 5, 25, LocalDate.parse("2015-07-02"));
		Assertions.assertEquals("CHNS", rentalAgreement.getToolCode());
		Assertions.assertEquals("Chainsaw", rentalAgreement.getToolType());
		Assertions.assertEquals("Stihl", rentalAgreement.getToolBrand());
		Assertions.assertEquals(5, rentalAgreement.getRentalDays());
		Assertions.assertEquals(LocalDate.parse("2015-07-02"), rentalAgreement.getCheckoutDate());
		Assertions.assertEquals(LocalDate.parse("2015-07-07"), rentalAgreement.getDueDate());
		Assertions.assertEquals(1.46, rentalAgreement.getDailyRentalCharge());
		Assertions.assertEquals(3, rentalAgreement.getChargeDays());
		Assertions.assertEquals(4.38, rentalAgreement.getPrediscountCharge());
		Assertions.assertEquals(25, rentalAgreement.getDiscountPercent());
		Assertions.assertEquals(1.1, rentalAgreement.getDiscountAmount());
		Assertions.assertEquals(3.28, rentalAgreement.getFinalCharge());
	}

	@Test
	public void testRentalCase_4() {
		// JAKD, 9/3/15, 6, 0%
		var rentalAgreement = rentalService.checkout(
				"JAKD", 6, 0, LocalDate.parse("2015-09-03"));
		Assertions.assertEquals("JAKD", rentalAgreement.getToolCode());
		Assertions.assertEquals("Jackhammer", rentalAgreement.getToolType());
		Assertions.assertEquals("DeWalt", rentalAgreement.getToolBrand());
		Assertions.assertEquals(6, rentalAgreement.getRentalDays());
		Assertions.assertEquals(LocalDate.parse("2015-09-03"), rentalAgreement.getCheckoutDate());
		Assertions.assertEquals(LocalDate.parse("2015-09-09"), rentalAgreement.getDueDate());
		Assertions.assertEquals(2.99, rentalAgreement.getDailyRentalCharge());
		Assertions.assertEquals(3, rentalAgreement.getChargeDays());
		Assertions.assertEquals(8.97, rentalAgreement.getPrediscountCharge());
		Assertions.assertEquals(0, rentalAgreement.getDiscountPercent());
		Assertions.assertEquals(0, rentalAgreement.getDiscountAmount());
		Assertions.assertEquals(8.97, rentalAgreement.getFinalCharge());
	}

	@Test
	public void testRentalCase_5() {
		// JAKR, 7/2/15, 9, 0%
		var rentalAgreement = rentalService.checkout(
				"JAKR", 9, 0, LocalDate.parse("2015-07-02"));
		Assertions.assertEquals("JAKR", rentalAgreement.getToolCode());
		Assertions.assertEquals("Jackhammer", rentalAgreement.getToolType());
		Assertions.assertEquals("Rigid", rentalAgreement.getToolBrand());
		Assertions.assertEquals(9, rentalAgreement.getRentalDays());
		Assertions.assertEquals(LocalDate.parse("2015-07-02"), rentalAgreement.getCheckoutDate());
		Assertions.assertEquals(LocalDate.parse("2015-07-11"), rentalAgreement.getDueDate());
		Assertions.assertEquals(2.99, rentalAgreement.getDailyRentalCharge());
		Assertions.assertEquals(6, rentalAgreement.getChargeDays());
		Assertions.assertEquals(17.94, rentalAgreement.getPrediscountCharge());
		Assertions.assertEquals(0, rentalAgreement.getDiscountPercent());
		Assertions.assertEquals(0, rentalAgreement.getDiscountAmount());
		Assertions.assertEquals(17.94, rentalAgreement.getFinalCharge());
	}

	@Test
	public void testRentalCase_6() {
		// JAKR, 7/2/20, 4, 50%
		var rentalAgreement = rentalService.checkout(
				"JAKR", 4, 50, LocalDate.parse("2020-07-02"));
		Assertions.assertEquals("JAKR", rentalAgreement.getToolCode());
		Assertions.assertEquals("Jackhammer", rentalAgreement.getToolType());
		Assertions.assertEquals("Rigid", rentalAgreement.getToolBrand());
		Assertions.assertEquals(4, rentalAgreement.getRentalDays());
		Assertions.assertEquals(LocalDate.parse("2020-07-02"), rentalAgreement.getCheckoutDate());
		Assertions.assertEquals(LocalDate.parse("2020-07-06"), rentalAgreement.getDueDate());
		Assertions.assertEquals(2.99, rentalAgreement.getDailyRentalCharge());
		Assertions.assertEquals(1, rentalAgreement.getChargeDays());
		Assertions.assertEquals(2.99, rentalAgreement.getPrediscountCharge());
		Assertions.assertEquals(50, rentalAgreement.getDiscountPercent());
		Assertions.assertEquals(1.5, rentalAgreement.getDiscountAmount());
		Assertions.assertEquals(1.49, rentalAgreement.getFinalCharge());
	}

}
