package com.example.hardwaretoolinginventory.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.example.hardwaretoolinginventory.enums.ToolType;
import com.example.hardwaretoolinginventory.exception.CheckoutValidationException;
import com.example.hardwaretoolinginventory.models.RentalAgreement;
import com.example.hardwaretoolinginventory.models.Tool;
import com.example.hardwaretoolinginventory.repository.ToolRepository;
import com.example.hardwaretoolinginventory.utils.DateUtils;

public class CheckoutServiceImpl implements CheckoutService {

    ToolRepository toolRepository;

    public CheckoutServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    /*
     * Calculating the RentalAgreement for a given tool identified by code and 
     * calculated according to rental parameters.
     * 
     * @param toolCode natural key for Tools
     * @param rentalDayCount count of days to rent starting from day after checkout date
     * @param discountPct percentage discount
     * @param checkoutDate date of initial rental checkout
     * @return RentalAgreement for given tool
     * @throws CheckoutValidationException if rentalDayCount & discountPct are invalid
     */
    @Override
    public RentalAgreement checkout(String toolCode, Integer rentalDayCount, Integer discountPct, LocalDate checkoutDate) throws CheckoutValidationException {
        preCheckoutValidation(rentalDayCount, discountPct);

        Tool tool = toolRepository.getTool(toolCode);

        LocalDate dueDate = checkoutDate.plusDays(rentalDayCount);
        Integer chargeDays = calculateChargeDays(tool.getToolType(), checkoutDate, dueDate);

        BigDecimal  preDiscountCharge = new BigDecimal(chargeDays)
            .multiply(tool.getToolType().getDailyCharge())
            .setScale(2, RoundingMode.CEILING);

        BigDecimal discountAmount = preDiscountCharge.multiply(new BigDecimal(discountPct).divide(new BigDecimal(100)))
            .setScale(2, RoundingMode.CEILING);

        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        return new RentalAgreement(toolCode, tool.getToolType().getName(), rentalDayCount, checkoutDate,
            dueDate, tool.getToolType().getDailyCharge(), chargeDays, preDiscountCharge, discountPct, 
            discountAmount, finalCharge);
    }

    /*
     * Validate if Rental day count is not 1 or greater, or Discount percent 
     * is not in the range 0-100
     * 
     * @param rentalDayCount count of days to rent starting from day after checkout date
     * @param discountPct percentage discount
     * @throws CheckoutValidationException if rentalDayCount is not 1 or greater or discountPct is not in the range 0-100 
     */
    private void preCheckoutValidation(Integer rentalDayCount, Integer discountPct) throws CheckoutValidationException {
        if(rentalDayCount < 1) {
            throw new CheckoutValidationException("Rental day count is not 1 or greater");
        }
        if(discountPct < 0  || discountPct > 100) {
            throw new CheckoutValidationException("Discount percent is not in the range 0-100");
        }
    }

    /*
     * Calculating the number of chargeable days based on the ToolType 
     * parameters provided that indicate if chargeable on weekday, 
     * weekend, or holidays. 
     * 
     * @param toolType Type of Tool, with chargeable day parameters
     * @param checkoutDate date of initial rental checkout
     * @param dueDate final date of rental 
     * @return Count of Chargeable days
     */
    private Integer calculateChargeDays(ToolType toolType, LocalDate checkoutDate, LocalDate dueDate) {
        LocalDate datePointer = LocalDate.of(checkoutDate.getYear(), checkoutDate.getMonth(), checkoutDate.getDayOfMonth()).plusDays(1);
        Integer chargeDays = 0;

        while(datePointer.isBefore(dueDate.plusDays(1))) {
            Boolean isWeekend = DateUtils.isWeekend(datePointer);
            Boolean isHoliday = DateUtils.isHoliday(datePointer);
            
            if(isWeekend && toolType.isWeekendCharged())
                    chargeDays++;

            else if(isHoliday && toolType.isHolidayCharged())
                    chargeDays++;

            else if(!isWeekend && !isHoliday && toolType.isWeekdayCharged())
                chargeDays++;

            datePointer = datePointer.plusDays(1);
        }

        return chargeDays;
    }
}
