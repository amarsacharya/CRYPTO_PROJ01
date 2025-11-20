package com.cryptoportfolio.service;

import com.cryptoportfolio.dto.HoldingDto;
import com.cryptoportfolio.entity.Cryptocurrency;
import com.cryptoportfolio.entity.Holding;
import com.cryptoportfolio.entity.Portfolio;
import com.cryptoportfolio.entity.User;
import com.cryptoportfolio.repository.CryptocurrencyRepository;
import com.cryptoportfolio.repository.HoldingRepository;
import com.cryptoportfolio.repository.PortfolioRepository;
import com.cryptoportfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoldingService {

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<HoldingDto> getPortfolioHoldings(Long portfolioId) {
        User currentUser = getCurrentUser();
        Portfolio portfolio = portfolioRepository.findByIdAndUser(portfolioId, currentUser)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        
        List<Holding> holdings = holdingRepository.findByPortfolio(portfolio);
        return holdings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public HoldingDto createHolding(HoldingDto holdingDto) {
        User currentUser = getCurrentUser();
        Portfolio portfolio = portfolioRepository.findByIdAndUser(holdingDto.getPortfolioId(), currentUser)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        
        Cryptocurrency cryptocurrency = cryptocurrencyRepository.findById(holdingDto.getCryptocurrencyId())
                .orElseThrow(() -> new RuntimeException("Cryptocurrency not found"));

        Holding holding = new Holding(holdingDto.getQuantity(), holdingDto.getPurchasePrice(), portfolio, cryptocurrency);
        
        // Calculate current value and profit/loss
        BigDecimal currentValue = cryptocurrency.getCurrentPrice().multiply(holding.getQuantity());
        BigDecimal purchaseValue = holding.getPurchasePrice().multiply(holding.getQuantity());
        BigDecimal profitLoss = currentValue.subtract(purchaseValue);
        
        holding.setCurrentValue(currentValue);
        holding.setProfitLoss(profitLoss);
        
        Holding savedHolding = holdingRepository.save(holding);
        updatePortfolioTotals(portfolio);
        
        return convertToDto(savedHolding);
    }

    public HoldingDto updateHolding(Long id, HoldingDto holdingDto) {
        Holding holding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found"));
        
        User currentUser = getCurrentUser();
        if (!holding.getPortfolio().getUser().equals(currentUser)) {
            throw new RuntimeException("Access denied");
        }

        holding.setQuantity(holdingDto.getQuantity());
        holding.setPurchasePrice(holdingDto.getPurchasePrice());
        
        // Recalculate values
        BigDecimal currentValue = holding.getCryptocurrency().getCurrentPrice().multiply(holding.getQuantity());
        BigDecimal purchaseValue = holding.getPurchasePrice().multiply(holding.getQuantity());
        BigDecimal profitLoss = currentValue.subtract(purchaseValue);
        
        holding.setCurrentValue(currentValue);
        holding.setProfitLoss(profitLoss);
        
        Holding updatedHolding = holdingRepository.save(holding);
        updatePortfolioTotals(holding.getPortfolio());
        
        return convertToDto(updatedHolding);
    }

    public void deleteHolding(Long id) {
        Holding holding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found"));
        
        User currentUser = getCurrentUser();
        if (!holding.getPortfolio().getUser().equals(currentUser)) {
            throw new RuntimeException("Access denied");
        }

        Portfolio portfolio = holding.getPortfolio();
        holdingRepository.delete(holding);
        updatePortfolioTotals(portfolio);
    }

    private void updatePortfolioTotals(Portfolio portfolio) {
        List<Holding> holdings = holdingRepository.findByPortfolio(portfolio);
        
        BigDecimal totalValue = holdings.stream()
                .map(Holding::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalProfitLoss = holdings.stream()
                .map(Holding::getProfitLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        portfolio.setTotalValue(totalValue);
        portfolio.setTotalProfitLoss(totalProfitLoss);
        portfolioRepository.save(portfolio);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private HoldingDto convertToDto(Holding holding) {
        HoldingDto dto = new HoldingDto();
        dto.setId(holding.getId());
        dto.setQuantity(holding.getQuantity());
        dto.setPurchasePrice(holding.getPurchasePrice());
        dto.setCurrentValue(holding.getCurrentValue());
        dto.setProfitLoss(holding.getProfitLoss());
        dto.setPurchaseDate(holding.getPurchaseDate());
        dto.setUpdatedAt(holding.getUpdatedAt());
        dto.setPortfolioId(holding.getPortfolio().getId());
        dto.setCryptocurrencyId(holding.getCryptocurrency().getId());
        dto.setCryptocurrencySymbol(holding.getCryptocurrency().getSymbol());
        dto.setCryptocurrencyName(holding.getCryptocurrency().getName());
        dto.setCryptocurrencyCurrentPrice(holding.getCryptocurrency().getCurrentPrice());
        return dto;
    }
}