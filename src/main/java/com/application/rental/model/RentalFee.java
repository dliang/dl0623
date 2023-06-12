package com.application.rental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "rental_fee")
public class RentalFee {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ToolType toolType;
    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public RentalFee() {
        super();
    }

    public RentalFee(
            UUID id,
            ToolType toolType,
            double dailyCharge,
            boolean weekdayCharge,
            boolean weekendCharge,
            boolean holidayCharge) {
        this.id = id;
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }
}
