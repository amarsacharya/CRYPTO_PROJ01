package com.cryptoportfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PortfolioDto {
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String name;
    
    @Size(max = 500)
    private String description;
    
    private BigDecimal totalValue;
    private BigDecimal totalProfitLoss;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<HoldingDto> holdings;

    // Constructors
    public PortfolioDto() {}

    public PortfolioDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }

    public BigDecimal getTotalProfitLoss() { return totalProfitLoss; }
    public void setTotalProfitLoss(BigDecimal totalProfitLoss) { this.totalProfitLoss = totalProfitLoss; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<HoldingDto> getHoldings() { return holdings; }
    public void setHoldings(List<HoldingDto> holdings) { this.holdings = holdings; }
}