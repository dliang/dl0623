package com.application.rental.repository;

import com.application.rental.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ToolDAO extends JpaRepository<Tool, UUID> {
    @Query("SELECT t FROM Tool t")
    List<Tool> findAllTools();

    @Query("SELECT t FROM Tool t WHERE t.toolCode = ?1")
    Tool findToolByToolCode(String toolCode);
}
