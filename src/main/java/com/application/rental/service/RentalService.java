package com.application.rental.service;

import com.application.rental.exception.BadRequestException;
import com.application.rental.exception.NotFoundException;
import com.application.rental.model.RentalAgreement;
import com.application.rental.model.RentalFee;
import com.application.rental.repository.RentalFeeDAO;
import com.application.rental.repository.ToolDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@Service
public class RentalService {

    private static final Logger logger = LoggerFactory.getLogger(RentalService.class);

    private RentalFeeDAO rentalFeeDAO;
    private ToolDAO toolDAO;

    public RentalService(
            RentalFeeDAO rentalFeeDAO,
            ToolDAO toolDAO
    ) {
        this.rentalFeeDAO = rentalFeeDAO;
        this.toolDAO = toolDAO;
    }

    public RentalAgreement checkout(
            String toolCode,
            int rentalDayCount,
            int discountPercent,
            LocalDate checkoutDate
    ) {
        if (rentalDayCount < 1) {
            throw new BadRequestException("Rental Day Count value of: " + rentalDayCount + " must be 1 or greater");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new BadRequestException("Discount Percent value of: " + discountPercent + " must be between 0 - 100");
        }
        var tool = toolDAO.findToolByToolCode(toolCode);
        if (tool == null) {
            throw new NotFoundException("Tool not found for code: " + toolCode);
        }

        var rentalFee = rentalFeeDAO.findByToolType(tool.getToolType());
        if (rentalFee == null) {
            throw new NotFoundException("Rental Fee not found for tool type: " + tool.getToolType());
        }

        var rentalAgreement = RentalAgreement.builder();
        var chargeDays = calculateChargeDays(checkoutDate, rentalDayCount, rentalFee);
        var chargeAmount = calculateChargeAmount(chargeDays, rentalFee.getDailyCharge());
        var discountAmount = calculateDiscountAmount(chargeAmount, discountPercent);
        rentalAgreement.toolCode(toolCode)
                .toolType(tool.getToolType().name())
                .toolBrand(tool.getBrand())
                .rentalDays(rentalDayCount)
                .checkoutDate(checkoutDate)
                .dueDate(calculateDueDate(checkoutDate, rentalDayCount))
                .dailyRentalCharge(rentalFee.getDailyCharge())
                .chargeDays(chargeDays)
                .prediscountCharge(chargeAmount)
                .discountPercent(discountPercent)
                .discountAmount(discountAmount)
                .finalCharge(roundToNearestCent(chargeAmount - discountAmount));

        var agreement = rentalAgreement.build();
        agreement.print();
        return rentalAgreement.build();
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDayCount) {
        return checkoutDate.plusDays(rentalDayCount);
    }

    private int calculateChargeDays(
            LocalDate checkoutDate,
            int rentalDayCount,
            RentalFee rentalFee) {

        int chargeDays = 0;
        var currentDate = checkoutDate;
        for (int i = 0; i < rentalDayCount; i++) {
            if (isHoliday(currentDate)) {
                if (rentalFee.isHolidayCharge()) {
                    chargeDays++;
                }
            } else if (isWeekend(currentDate)) {
                if (rentalFee.isWeekendCharge()) {
                    chargeDays++;
                }
            } else if (!isWeekend(currentDate)) {
                if (rentalFee.isWeekdayCharge()) {
                    chargeDays++;
                }
            }


            currentDate = currentDate.plusDays(1);
        }

        return chargeDays;
    }

    private double roundToNearestCent(double value) {
        var bigDecimal = new BigDecimal(Double.toString(value));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    private double calculateDiscountAmount(double chargeAmount, int discountPercent) {
        return roundToNearestCent((chargeAmount * discountPercent) / 100.0);
    }

    private double calculateChargeAmount(int chargeDays, double chargeFee) {
        return chargeDays * chargeFee;
    }

    private boolean isHoliday(LocalDate date) {

        // July 4th (if saturday, use Friday. If Sunday, use Monday)
        var currentYearJulyForth = LocalDate.of(date.getYear(), Month.JULY, 4);
        if (currentYearJulyForth.getDayOfWeek() == DayOfWeek.SATURDAY) {
            currentYearJulyForth = currentYearJulyForth.minusDays(1);
        } else if (currentYearJulyForth.getDayOfWeek() == DayOfWeek.SUNDAY) {
            currentYearJulyForth = currentYearJulyForth.plusDays(1);
        }

        // Labor Day - First monday in september
        var currentYearLaborDay = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1);
        while (currentYearLaborDay.getDayOfWeek() != DayOfWeek.MONDAY) {
            currentYearLaborDay = currentYearLaborDay.plusDays(1);
        }

        return date.isEqual(currentYearJulyForth) || date.isEqual(currentYearLaborDay);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
