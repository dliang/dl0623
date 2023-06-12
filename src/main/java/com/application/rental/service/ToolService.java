package com.application.rental.service;

import com.application.rental.model.Tool;
import com.application.rental.repository.ToolDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    private ToolDAO toolDAO;

    public ToolService(ToolDAO toolDAO) {
        this.toolDAO = toolDAO;
    }

    public List<Tool> getAllTools() {
        return toolDAO.findAllTools();
    }

}
