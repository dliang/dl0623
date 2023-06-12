package com.application.rental.repository;

import com.application.rental.model.RentalFee;
import com.application.rental.model.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RentalFeeDAO extends JpaRepository<RentalFee, UUID> {
    @Query("SELECT r FROM RentalFee as r WHERE r.toolType = :toolType")
    RentalFee findByToolType(@Param("toolType") ToolType toolType);
}
