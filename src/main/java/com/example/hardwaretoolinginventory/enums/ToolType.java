package com.example.hardwaretoolinginventory.enums;

import java.math.BigDecimal;

public enum ToolType {
    JACKHAMMER("Jackhammer", BigDecimal.valueOf(2.99), true, false, false),
    CHAINSAW("Chainsaw",BigDecimal.valueOf( 1.49), true, false, true),
    LADDER("Ladder", BigDecimal.valueOf(1.99), true, true, false);

    private final String name;
    private final BigDecimal dailyCharge;
    private final Boolean weekdayCharge;
    private final Boolean weekendCharge;
    private final Boolean holidayCharge;

    ToolType(String displayName, BigDecimal dailyCharge, Boolean weekdayCharge, Boolean weekendCharge, Boolean holidayCharge) {
        this.name = displayName;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekdayCharged() {
        return weekdayCharge;
    }

    public boolean isWeekendCharged() {
        return weekendCharge;
    }

    public boolean isHolidayCharged() {
        return holidayCharge;
    }
}
