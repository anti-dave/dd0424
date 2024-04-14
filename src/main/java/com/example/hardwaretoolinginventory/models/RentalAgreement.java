package com.example.hardwaretoolinginventory.models;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.NumberFormatter;

public class RentalAgreement {
    private final String toolCode;
    private final String toolType;
    private final Integer rentalDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final BigDecimal dailyRentalCharge;
    private final Integer chargeDays;
    private final BigDecimal preDiscountCharge;
    private final Integer discountPercent;
    private final BigDecimal discountAmount;
    private final BigDecimal finalCharge;

    public RentalAgreement(String toolCode, String toolType, Integer rentalDays, 
        LocalDate checkoutDate, LocalDate dueDate, BigDecimal dailyRentalCharge, 
        Integer chargeDays, BigDecimal preDiscountCharge, Integer discountPercent, 
        BigDecimal discountAmount, BigDecimal finalCharge) {
            this.toolCode = toolCode;
            this.toolType = toolType;
            this.rentalDays = rentalDays;
            this.checkoutDate = checkoutDate;
            this.dueDate = dueDate;
            this.dailyRentalCharge = dailyRentalCharge;
            this.chargeDays = chargeDays;
            this.preDiscountCharge = preDiscountCharge;
            this.discountPercent = discountPercent;
            this.discountAmount = discountAmount;
            this.finalCharge = finalCharge;
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public Integer getChargeDays() {
        return chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }


    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return String.format("Tool code: %s \n", toolCode) +
        String.format("Tool type: %s \n", toolType) +
        String.format("Rental days: %s \n", rentalDays) +
        String.format("Checkout date: %s \n", checkoutDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"))) +
        String.format("Due date: %s \n", dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"))) +
        String.format("Daily charge: $%s \n", dailyRentalCharge) +
        String.format("Chargeable days: %d \n", chargeDays) +
        String.format("Pre discount charge: $%s \n", DecimalFormat.getInstance().format(preDiscountCharge)) +
        String.format("Discount percent: %d%% \n", discountPercent) +
        String.format("Discount amount: $%s \n", DecimalFormat.getInstance().format(discountAmount)) +
        String.format("Final charge: $%s \n", DecimalFormat.getInstance().format(finalCharge));
    }

}
