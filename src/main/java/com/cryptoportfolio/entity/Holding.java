package com.cryptoportfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "holdings")
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(precision = 20, scale = 8)
    private BigDecimal quantity;

    @NotNull
    @Positive
    @Column(name = "purchase_price", precision = 20, scale = 8)
    private BigDecimal purchasePrice;

    @Column(name = "current_value", precision = 20, scale = 8)
    private BigDecimal currentValue;

    @Column(name = "profit_loss", precision = 20, scale = 8)
    private BigDecimal profitLoss;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cryptocurrency_id", nullable = false)
    private Cryptocurrency cryptocurrency;

    @PrePersist
    protected void onCreate() {
        purchaseDate = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Holding() {}

    public Holding(BigDecimal quantity, BigDecimal purchasePrice, Portfolio portfolio, Cryptocurrency cryptocurrency) {
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.portfolio = portfolio;
        this.cryptocurrency = cryptocurrency;
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

    public Portfolio getPortfolio() { return portfolio; }
    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }

    public Cryptocurrency getCryptocurrency() { return cryptocurrency; }
    public void setCryptocurrency(Cryptocurrency cryptocurrency) { this.cryptocurrency = cryptocurrency; }
}