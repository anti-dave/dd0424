package com.example.hardwaretoolinginventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hardwaretoolinginventory.enums.Brand;
import com.example.hardwaretoolinginventory.enums.ToolType;
import com.example.hardwaretoolinginventory.exception.CheckoutValidationException;
import com.example.hardwaretoolinginventory.models.RentalAgreement;
import com.example.hardwaretoolinginventory.models.Tool;
import com.example.hardwaretoolinginventory.repository.ToolRepository;
import com.example.hardwaretoolinginventory.service.CheckoutService;
import com.example.hardwaretoolinginventory.service.CheckoutServiceImpl;

@ExtendWith(MockitoExtension.class)
class HardwareToolingInventoryApplicationTests {

	@Mock
	ToolRepository toolRepository;
	CheckoutService checkoutService;

	@BeforeEach
	void setup() {
		checkoutService = new CheckoutServiceImpl(toolRepository);
	}

	// Extra Validation scenarios
	@Test
	void rentalDayCountIsNotGreaterThan0() {
		Exception exception = assertThrows(CheckoutValidationException.class, () -> {
			checkoutService.checkout("JAKR", 0, 100, LocalDate.of(2015, 9, 3));
		});
	
		String expectedMessage = "Rental day count is not 1 or greater";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void discountPctNegativeOneExceptionCase() {
		Exception exception = assertThrows(CheckoutValidationException.class, () -> {
			checkoutService.checkout("JAKR", 5, -1, LocalDate.of(2015, 9, 3));
		});
	
		String expectedMessage = "Discount percent is not in the range 0-100";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	// Prompt scenarios
	@Test
	void scenario1_discountPctOver1oneHundredExceptionCase() {
		Exception exception = assertThrows(CheckoutValidationException.class, () -> {
			checkoutService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
		});
	
		String expectedMessage = "Discount percent is not in the range 0-100";
		String actualMessage = exception.getMessage();
	
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void scenario2_LadderJuly4th() {
		Mockito.when(toolRepository.getTool("LADW")).thenReturn(new Tool("LADW", ToolType.LADDER, Brand.Werner));
		RentalAgreement rentalAgreement = agreementScenario("LADW", 3, 10, LocalDate.of(2020, 7, 2));
		
		assertEquals("LADW", rentalAgreement.getToolCode());
		assertEquals("Ladder", rentalAgreement.getToolType());
		assertEquals(3, rentalAgreement.getRentalDays());
		assertEquals(LocalDate.of(2020, 7, 2), rentalAgreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 5), rentalAgreement.getDueDate());
		assertEquals(BigDecimal.valueOf(1.99), rentalAgreement.getDailyRentalCharge());
		assertEquals(2, rentalAgreement.getChargeDays());
		assertEquals(BigDecimal.valueOf(3.98), rentalAgreement.getPreDiscountCharge());
		assertEquals(10, rentalAgreement.getDiscountPercent());
		assertEquals(BigDecimal.valueOf(0.40).setScale(2, RoundingMode.CEILING), rentalAgreement.getDiscountAmount());
		assertEquals(BigDecimal.valueOf(3.58), rentalAgreement.getFinalCharge());
	}
	
	@Test
	void scenario3_ChainsawJuly4th() {
		Mockito.when(toolRepository.getTool("CHNS")).thenReturn(new Tool("CHNS", ToolType.CHAINSAW, Brand.Stihl));
		RentalAgreement rentalAgreement = agreementScenario("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
		
		assertEquals("CHNS", rentalAgreement.getToolCode());
		assertEquals("Chainsaw", rentalAgreement.getToolType());
		assertEquals(5, rentalAgreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 7, 2), rentalAgreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 7), rentalAgreement.getDueDate());
		assertEquals(BigDecimal.valueOf(1.49), rentalAgreement.getDailyRentalCharge());
		assertEquals(3, rentalAgreement.getChargeDays());
		assertEquals(BigDecimal.valueOf(4.47), rentalAgreement.getPreDiscountCharge());
		assertEquals(25, rentalAgreement.getDiscountPercent());
		assertEquals(BigDecimal.valueOf(1.12), rentalAgreement.getDiscountAmount());
		assertEquals(BigDecimal.valueOf(3.35), rentalAgreement.getFinalCharge());
	}

	@Test
	void scenario4_JackHammerLaborDayZeroDiscount() {
		Mockito.when(toolRepository.getTool("JAKD")).thenReturn(new Tool("JAKD", ToolType.JACKHAMMER, Brand.DeWalt));
		RentalAgreement rentalAgreement = agreementScenario("JAKD", 6, 0, LocalDate.of(2015, 9, 3));

		assertEquals("JAKD", rentalAgreement.getToolCode());
		assertEquals("Jackhammer", rentalAgreement.getToolType());
		assertEquals(3, rentalAgreement.getChargeDays());
		assertEquals(LocalDate.of(2015, 9, 3), rentalAgreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 9, 9), rentalAgreement.getDueDate());
		assertEquals(BigDecimal.valueOf(2.99), rentalAgreement.getDailyRentalCharge());
		assertEquals(6, rentalAgreement.getRentalDays());
		assertEquals(BigDecimal.valueOf(8.97), rentalAgreement.getPreDiscountCharge());
		assertEquals(0, rentalAgreement.getDiscountPercent());
		assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.CEILING), rentalAgreement.getDiscountAmount());
		assertEquals(BigDecimal.valueOf(8.97), rentalAgreement.getFinalCharge());
	}

	@Test
	void scenario5_JackHammerJuly4thZeroDiscount() {
		Mockito.when(toolRepository.getTool("JAKR")).thenReturn(new Tool("JAKR", ToolType.JACKHAMMER, Brand.Ridgid));
		RentalAgreement rentalAgreement = agreementScenario("JAKR", 9, 0, LocalDate.of(2015, 7, 2));

		assertEquals("JAKR", rentalAgreement.getToolCode());
		assertEquals("Jackhammer", rentalAgreement.getToolType());
		assertEquals(5, rentalAgreement.getChargeDays());
		assertEquals(LocalDate.of(2015, 7, 2), rentalAgreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 11), rentalAgreement.getDueDate());
		assertEquals(BigDecimal.valueOf(2.99), rentalAgreement.getDailyRentalCharge());
		assertEquals(9, rentalAgreement.getRentalDays());
		assertEquals(BigDecimal.valueOf(14.95), rentalAgreement.getPreDiscountCharge());
		assertEquals(0, rentalAgreement.getDiscountPercent());
		assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.CEILING), rentalAgreement.getDiscountAmount());
		assertEquals(BigDecimal.valueOf(14.95), rentalAgreement.getFinalCharge());
	}

	@Test
	void scenario6_JackHammerJuly4th() {
		Mockito.when(toolRepository.getTool("JAKR"))
			.thenReturn(new Tool("JAKR", ToolType.JACKHAMMER, Brand.Ridgid));
		RentalAgreement rentalAgreement = agreementScenario("JAKR", 4, 50, LocalDate.of(2020, 7, 2));

		assertEquals("JAKR", rentalAgreement.getToolCode());
		assertEquals("Jackhammer", rentalAgreement.getToolType());
		assertEquals(1, rentalAgreement.getChargeDays());
		assertEquals(LocalDate.of(2020, 7, 2), rentalAgreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 6), rentalAgreement.getDueDate());
		assertEquals(BigDecimal.valueOf(2.99), rentalAgreement.getDailyRentalCharge());
		assertEquals(4, rentalAgreement.getRentalDays());
		assertEquals(BigDecimal.valueOf(2.99), rentalAgreement.getPreDiscountCharge());
		assertEquals(50, rentalAgreement.getDiscountPercent());
		assertEquals(BigDecimal.valueOf(1.50).setScale(2, RoundingMode.CEILING), rentalAgreement.getDiscountAmount());
		assertEquals(BigDecimal.valueOf(1.49), rentalAgreement.getFinalCharge());
	}

	private RentalAgreement agreementScenario(String toolCode, Integer rentalDayCount, Integer discountPct, LocalDate checkoutDate) {
		RentalAgreement rentalAgreement= null;
		try {
			rentalAgreement = checkoutService.checkout(toolCode, rentalDayCount, discountPct, checkoutDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rentalAgreement;
	}
}
