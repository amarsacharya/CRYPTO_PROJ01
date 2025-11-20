package com.cryptoportfolio.controller;

import com.cryptoportfolio.dto.PortfolioDto;
import com.cryptoportfolio.service.PortfolioService;
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
@RequestMapping("/api/portfolios")

@Tag(name = "Portfolio", description = "Portfolio management APIs")
@SecurityRequirement(name = "bearerAuth")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    @Operation(summary = "Get user portfolios", description = "Get all portfolios for the authenticated user")
    public ResponseEntity<List<PortfolioDto>> getUserPortfolios() {
        List<PortfolioDto> portfolios = portfolioService.getUserPortfolios();
        return ResponseEntity.ok(portfolios);
    }

    @PostMapping
    @Operation(summary = "Create portfolio", description = "Create a new portfolio")
    public ResponseEntity<PortfolioDto> createPortfolio(@Valid @RequestBody PortfolioDto portfolioDto) {
        PortfolioDto createdPortfolio = portfolioService.createPortfolio(portfolioDto);
        return ResponseEntity.ok(createdPortfolio);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get portfolio by ID", description = "Get a specific portfolio by its ID")
    public ResponseEntity<PortfolioDto> getPortfolioById(@PathVariable Long id) {
        PortfolioDto portfolio = portfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update portfolio", description = "Update an existing portfolio")
    public ResponseEntity<PortfolioDto> updatePortfolio(@PathVariable Long id, @Valid @RequestBody PortfolioDto portfolioDto) {
        PortfolioDto updatedPortfolio = portfolioService.updatePortfolio(id, portfolioDto);
        return ResponseEntity.ok(updatedPortfolio);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete portfolio", description = "Delete a portfolio")
    public ResponseEntity<?> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.ok("Portfolio deleted successfully");
    }
}