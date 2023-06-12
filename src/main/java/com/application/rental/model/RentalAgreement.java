package com.application.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    @JsonFormat(pattern = "MM/dd/yy")
    private LocalDate checkoutDate;
    @JsonFormat(pattern = "MM/dd/yy")
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double prediscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;

    public void print() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        System.out.printf(
                """
                Tool code: %s
                Tool type: %s
                Tool brand: %s
                Rental days: %d
                Checkout date: %s
                Due date: %s
                Daily rental charge: $%.2f
                Charge days: %d
                Prediscount charge: $%.2f
                Discount percent: %d%%
                Discount amount: $%.2f
                Final charge: $%.2f%n
                """,
                toolCode,
                toolType,
                toolBrand,
                rentalDays,
                checkoutDate.format(formatter),
                dueDate.format(formatter),
                dailyRentalCharge,
                chargeDays,
                prediscountCharge,
                discountPercent,
                discountAmount,
                finalCharge);
    }
}
