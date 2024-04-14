package com.example.hardwaretoolinginventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.hardwaretoolinginventory.enums.Brand;
import com.example.hardwaretoolinginventory.enums.ToolType;
import com.example.hardwaretoolinginventory.models.RentalAgreement;
import com.example.hardwaretoolinginventory.models.Tool;
import com.example.hardwaretoolinginventory.repository.ToolRepository;
import com.example.hardwaretoolinginventory.repository.ToolRepositoryImpl;
import com.example.hardwaretoolinginventory.service.CheckoutService;
import com.example.hardwaretoolinginventory.service.CheckoutServiceImpl;

public class CheckoutDemo {

	private final static List<Tool> tools = new ArrayList<>(Arrays.asList(
		new Tool("CHNS", ToolType.CHAINSAW, Brand.Stihl), 
		new Tool("LADW", ToolType.LADDER, Brand.Werner), 
		new Tool("JAKD", ToolType.JACKHAMMER, Brand.DeWalt), 
		new Tool("JAKR", ToolType.JACKHAMMER, Brand.Ridgid)));

	static ToolRepository toolRepository = new ToolRepositoryImpl(tools);
	static CheckoutService checkoutService = new CheckoutServiceImpl(toolRepository);

	public static void main(String[] args) {
		// Prompt Scenarios
		checkoutAndPrint("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
		checkoutAndPrint("LADW", 3, 10, LocalDate.of(2020, 7, 2));
		checkoutAndPrint("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
		checkoutAndPrint("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
		checkoutAndPrint("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
		checkoutAndPrint("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
		// Additional Scenarios
		checkoutAndPrint("JAKR", 500, 0, LocalDate.of(2015, 9, 3));
		checkoutAndPrint("JAKR", 0, 101, LocalDate.of(2015, 9, 3));
	}

	private static void checkoutAndPrint(String toolCode, Integer rentalDayCount, Integer discountPct, LocalDate checkoutDate) {
		try {
			RentalAgreement rentalAgreement = checkoutService.checkout(toolCode, rentalDayCount, discountPct, checkoutDate);
			rentalAgreement.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
