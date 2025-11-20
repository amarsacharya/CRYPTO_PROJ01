package com.cryptoportfolio.service;

import com.cryptoportfolio.dto.CryptocurrencyDto;
import com.cryptoportfolio.entity.Cryptocurrency;
import com.cryptoportfolio.entity.Holding;
import com.cryptoportfolio.repository.CryptocurrencyRepository;
import com.cryptoportfolio.repository.HoldingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CryptocurrencyService {

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    public List<CryptocurrencyDto> getAllCryptocurrencies() {
        List<Cryptocurrency> cryptocurrencies = cryptocurrencyRepository.findAll();
        return cryptocurrencies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CryptocurrencyDto getCryptocurrencyById(Long id) {
        Cryptocurrency cryptocurrency = cryptocurrencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cryptocurrency not found"));
        return convertToDto(cryptocurrency);
    }

    public CryptocurrencyDto createCryptocurrency(CryptocurrencyDto cryptocurrencyDto) {
        if (cryptocurrencyRepository.existsBySymbol(cryptocurrencyDto.getSymbol())) {
            throw new RuntimeException("Cryptocurrency with symbol " + cryptocurrencyDto.getSymbol() + " already exists");
        }

        Cryptocurrency cryptocurrency = new Cryptocurrency(
                cryptocurrencyDto.getSymbol(),
                cryptocurrencyDto.getName(),
                cryptocurrencyDto.getCurrentPrice()
        );
        
        Cryptocurrency savedCryptocurrency = cryptocurrencyRepository.save(cryptocurrency);
        return convertToDto(savedCryptocurrency);
    }

    // Scheduled task to update prices every 5 minutes
    @Scheduled(fixedRate = 300000) // 5 minutes = 300,000 milliseconds
    public void updateCryptocurrencyPrices() {
        System.out.println("Updating cryptocurrency prices...");
        List<Cryptocurrency> cryptocurrencies = cryptocurrencyRepository.findAll();
        Random random = new Random();

        for (Cryptocurrency crypto : cryptocurrencies) {
            // Simulate price changes between -10% to +10%
            double changePercent = (random.nextDouble() - 0.5) * 0.2; // -0.1 to 0.1
            BigDecimal currentPrice = crypto.getCurrentPrice();
            BigDecimal priceChange = currentPrice.multiply(BigDecimal.valueOf(changePercent));
            BigDecimal newPrice = currentPrice.add(priceChange);
            
            // Ensure price doesn't go below 0.01
            if (newPrice.compareTo(BigDecimal.valueOf(0.01)) < 0) {
                newPrice = BigDecimal.valueOf(0.01);
            }
            
            crypto.setCurrentPrice(newPrice.setScale(8, RoundingMode.HALF_UP));
            crypto.setPriceChange24h(priceChange.setScale(2, RoundingMode.HALF_UP));
            cryptocurrencyRepository.save(crypto);

            // Update all holdings for this cryptocurrency
            updateHoldingsForCryptocurrency(crypto);
        }
        System.out.println("Cryptocurrency prices updated successfully");
    }

    private void updateHoldingsForCryptocurrency(Cryptocurrency cryptocurrency) {
        List<Holding> holdings = cryptocurrency.getHoldings();
        for (Holding holding : holdings) {
            BigDecimal currentValue = cryptocurrency.getCurrentPrice().multiply(holding.getQuantity());
            BigDecimal purchaseValue = holding.getPurchasePrice().multiply(holding.getQuantity());
            BigDecimal profitLoss = currentValue.subtract(purchaseValue);
            
            holding.setCurrentValue(currentValue);
            holding.setProfitLoss(profitLoss);
            holdingRepository.save(holding);
        }
    }

    private CryptocurrencyDto convertToDto(Cryptocurrency cryptocurrency) {
        CryptocurrencyDto dto = new CryptocurrencyDto();
        dto.setId(cryptocurrency.getId());
        dto.setSymbol(cryptocurrency.getSymbol());
        dto.setName(cryptocurrency.getName());
        dto.setCurrentPrice(cryptocurrency.getCurrentPrice());
        dto.setPriceChange24h(cryptocurrency.getPriceChange24h());
        dto.setLastUpdated(cryptocurrency.getLastUpdated());
        return dto;
    }
}