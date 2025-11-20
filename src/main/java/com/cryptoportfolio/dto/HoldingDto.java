package com.cryptoportfolio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoldingDto {
    private Long id;
    
    @NotNull
    @Positive
    private BigDecimal quantity;
    
    @NotNull
    @Positive
    private BigDecimal purchasePrice;
    
    private BigDecimal currentValue;
    private BigDecimal profitLoss;
    private LocalDateTime purchaseDate;
    private LocalDateTime updatedAt;
    private Long portfolioId;
    private Long cryptocurrencyId;
    private String cryptocurrencySymbol;
    private String cryptocurrencyName;
    private BigDecimal cryptocurrencyCurrentPrice;

    // Constructors
    public HoldingDto() {}

    public HoldingDto(BigDecimal quantity, BigDecimal purchasePrice, Long portfolioId, Long cryptocurrencyId) {
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.portfolioId = portfolioId;
        this.cryptocurrencyId = cryptocurrencyId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }

    public BigDecimal getCurrentValue() { return currentValue; }
    public void setCurrentValue(BigDecimal currentValue) { this.currentValue = currentValue; }

    public BigDecimal getProfitLoss() { return profitLoss; }
    public void setProfitLoss(BigDecimal profitLoss) { this.profitLoss = profitLoss; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }

    public Long getCryptocurrencyId() { return cryptocurrencyId; }
    public void setCryptocurrencyId(Long cryptocurrencyId) { this.cryptocurrencyId = cryptocurrencyId; }

    public String getCryptocurrencySymbol() { return cryptocurrencySymbol; }
    public void setCryptocurrencySymbol(String cryptocurrencySymbol) { this.cryptocurrencySymbol = cryptocurrencySymbol; }

    public String getCryptocurrencyName() { return cryptocurrencyName; }
    public void setCryptocurrencyName(String cryptocurrencyName) { this.cryptocurrencyName = cryptocurrencyName; }

    public BigDecimal getCryptocurrencyCurrentPrice() { return cryptocurrencyCurrentPrice; }
    public void setCryptocurrencyCurrentPrice(BigDecimal cryptocurrencyCurrentPrice) { this.cryptocurrencyCurrentPrice = cryptocurrencyCurrentPrice; }
}