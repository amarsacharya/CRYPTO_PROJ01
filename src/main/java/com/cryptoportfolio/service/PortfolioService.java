package com.cryptoportfolio.service;

import com.cryptoportfolio.dto.PortfolioDto;
import com.cryptoportfolio.entity.Portfolio;
import com.cryptoportfolio.entity.User;
import com.cryptoportfolio.repository.PortfolioRepository;
import com.cryptoportfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PortfolioDto> getUserPortfolios() {
        User currentUser = getCurrentUser();
        List<Portfolio> portfolios = portfolioRepository.findByUser(currentUser);
        return portfolios.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PortfolioDto createPortfolio(PortfolioDto portfolioDto) {
        User currentUser = getCurrentUser();
        Portfolio portfolio = new Portfolio(portfolioDto.getName(), portfolioDto.getDescription(), currentUser);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return convertToDto(savedPortfolio);
    }

    public PortfolioDto getPortfolioById(Long id) {
        User currentUser = getCurrentUser();
        Portfolio portfolio = portfolioRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return convertToDto(portfolio);
    }

    public PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto) {
        User currentUser = getCurrentUser();
        Portfolio portfolio = portfolioRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        
        portfolio.setName(portfolioDto.getName());
        portfolio.setDescription(portfolioDto.getDescription());
        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
        return convertToDto(updatedPortfolio);
    }

    public void deletePortfolio(Long id) {
        User currentUser = getCurrentUser();
        Portfolio portfolio = portfolioRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        portfolioRepository.delete(portfolio);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private PortfolioDto convertToDto(Portfolio portfolio) {
        PortfolioDto dto = new PortfolioDto();
        dto.setId(portfolio.getId());
        dto.setName(portfolio.getName());
        dto.setDescription(portfolio.getDescription());
        dto.setTotalValue(portfolio.getTotalValue());
        dto.setTotalProfitLoss(portfolio.getTotalProfitLoss());
        dto.setCreatedAt(portfolio.getCreatedAt());
        dto.setUpdatedAt(portfolio.getUpdatedAt());
        return dto;
    }
}