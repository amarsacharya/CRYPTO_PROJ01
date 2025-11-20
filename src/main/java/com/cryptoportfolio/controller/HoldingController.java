package com.cryptoportfolio.controller;

import com.cryptoportfolio.dto.HoldingDto;
import com.cryptoportfolio.service.HoldingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/holdings")

@Tag(name = "Holdings", description = "Holdings management APIs")
@SecurityRequirement(name = "bearerAuth")
public class HoldingController {

    @Autowired
    private HoldingService holdingService;

    @GetMapping("/portfolio/{portfolioId}")
    @Operation(summary = "Get portfolio holdings", description = "Get all holdings for a specific portfolio")
    public ResponseEntity<List<HoldingDto>> getPortfolioHoldings(@PathVariable Long portfolioId) {
        List<HoldingDto> holdings = holdingService.getPortfolioHoldings(portfolioId);
        return ResponseEntity.ok(holdings);
    }

    @PostMapping
    @Operation(summary = "Create holding", description = "Create a new holding")
    public ResponseEntity<HoldingDto> createHolding(@Valid @RequestBody HoldingDto holdingDto) {
        HoldingDto createdHolding = holdingService.createHolding(holdingDto);
        return ResponseEntity.ok(createdHolding);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update holding", description = "Update an existing holding")
    public ResponseEntity<HoldingDto> updateHolding(@PathVariable Long id, @Valid @RequestBody HoldingDto holdingDto) {
        HoldingDto updatedHolding = holdingService.updateHolding(id, holdingDto);
        return ResponseEntity.ok(updatedHolding);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete holding", description = "Delete a holding")
    public ResponseEntity<?> deleteHolding(@PathVariable Long id) {
        holdingService.deleteHolding(id);
        return ResponseEntity.ok("Holding deleted successfully");
    }
}