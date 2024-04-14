package com.example.hardwaretoolinginventory.service;

import java.time.LocalDate;

import com.example.hardwaretoolinginventory.exception.CheckoutValidationException;
import com.example.hardwaretoolinginventory.models.RentalAgreement;

public interface CheckoutService {
    RentalAgreement checkout(String toolCode, Integer rentalDayCount, Integer discountPct, LocalDate checkoutDate) throws CheckoutValidationException;
}
