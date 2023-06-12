package com.application.rental.controller;

import com.application.rental.model.RentalAgreement;
import com.application.rental.service.RentalService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/rental")
public class RentalController extends AbstractController {

    private RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/checkout")
    public RentalAgreement checkout(
            @RequestParam String toolCode,
            @RequestParam int rentalDayCount,
            @RequestParam int discountPercent,
            @Schema(example = "06/20/23", format = "MM/dd/yy") @RequestParam String checkoutDate
    ) {
        var dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return runWithErrorhandling(() -> rentalService.checkout(toolCode, rentalDayCount, discountPercent, LocalDate.parse(checkoutDate, dateFormatter)));
    }
}
