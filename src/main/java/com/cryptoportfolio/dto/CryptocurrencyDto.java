package com.cryptoportfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptocurrencyDto {
    private Long id;
    
    @NotBlank
    private String symbol;
    
    @NotBlank
    private String name;
    
    @NotNull
    @Positive
    private BigDecimal currentPrice;
    
    private BigDecimal priceChange24h;
    private LocalDateTime lastUpdated;

    // Constructors
    public CryptocurrencyDto() {}

    public CryptocurrencyDto(String symbol, String name, BigDecimal currentPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

    public BigDecimal getPriceChange24h() { return priceChange24h; }
    public void setPriceChange24h(BigDecimal priceChange24h) { this.priceChange24h = priceChange24h; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}