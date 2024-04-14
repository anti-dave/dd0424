package com.example.hardwaretoolinginventory.repository;

import java.util.List;
import java.util.Optional;

import com.example.hardwaretoolinginventory.models.Tool;

public class ToolRepositoryImpl implements ToolRepository {

    // This would be fetched from DAO in a live app.
    private final List<Tool> toolsFromDao;

    public ToolRepositoryImpl(List<Tool> tools) {
        this.toolsFromDao = tools;
    }

    @Override
    public Tool getTool(String toolCode) {
        Optional<Tool> tool = toolsFromDao.stream()
            .filter(x -> x.getToolCode()
            .equals(toolCode))
            .findFirst();

        return tool.isPresent() ? tool.get() : null;
    }
}
