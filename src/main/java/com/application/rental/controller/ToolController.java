package com.application.rental.controller;

import com.application.rental.model.Tool;
import com.application.rental.service.ToolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController extends AbstractController {

    private ToolService toolService;

    ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping("/")
    public List<Tool> getTools() {
        return runWithErrorhandling(() -> toolService.getAllTools());
    }
}
