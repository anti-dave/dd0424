package com.example.hardwaretoolinginventory.repository;

import com.example.hardwaretoolinginventory.models.Tool;

public interface ToolRepository {
    Tool getTool(String toolCode);
}
