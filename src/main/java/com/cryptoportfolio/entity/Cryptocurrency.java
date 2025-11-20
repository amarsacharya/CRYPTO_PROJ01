package com.cryptoportfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cryptocurrencies")
public class Cryptocurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String symbol;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    @Column(precision = 20, scale = 8)
    private BigDecimal currentPrice;

    @Column(name = "price_change_24h", precision = 10, scale = 2)
    private BigDecimal priceChange24h;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "cryptocurrency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Holding> holdings;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    // Constructors
    public Cryptocurrency() {}

    public Cryptocurrency(String symbol, String name, BigDecimal currentPrice) {
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

    public List<Holding> getHoldings() { return holdings; }
    public void setHoldings(List<Holding> holdings) { this.holdings = holdings; }
}