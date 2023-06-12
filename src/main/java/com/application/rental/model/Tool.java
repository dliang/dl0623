package com.application.rental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tool")
public class Tool {

    @Id
    @GeneratedValue
    private UUID id;
    private String toolCode;
    @Enumerated(EnumType.STRING)
    private ToolType toolType;
    private String brand;

    public Tool() {
        super();
    }

    public Tool(UUID id, String toolCode, ToolType toolType, String brand) {
        this.id = id;
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
    }
}
