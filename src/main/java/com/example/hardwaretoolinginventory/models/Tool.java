package com.example.hardwaretoolinginventory.models;

import com.example.hardwaretoolinginventory.enums.Brand;
import com.example.hardwaretoolinginventory.enums.ToolType;

public class Tool {
    private final String toolCode;
    private final ToolType toolType;
    private final Brand brand;

    public Tool(String toolCode, ToolType toolType, Brand brand) {
        this.toolCode = toolCode;
        this.brand = brand;
        this.toolType = toolType;
    }

    public String getToolCode() {
        return toolCode;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public Brand getBrand() {
        return brand;
    }
}
