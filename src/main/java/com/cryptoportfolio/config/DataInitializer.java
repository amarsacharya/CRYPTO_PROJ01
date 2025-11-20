package com.cryptoportfolio.config;

import com.cryptoportfolio.entity.Cryptocurrency;
import com.cryptoportfolio.repository.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize some sample cryptocurrencies if none exist
        if (cryptocurrencyRepository.count() == 0) {
            cryptocurrencyRepository.save(new Cryptocurrency("BTC", "Bitcoin", new BigDecimal("45000.00")));
            cryptocurrencyRepository.save(new Cryptocurrency("ETH", "Ethereum", new BigDecimal("3000.00")));
            cryptocurrencyRepository.save(new Cryptocurrency("ADA", "Cardano", new BigDecimal("0.50")));
            cryptocurrencyRepository.save(new Cryptocurrency("DOT", "Polkadot", new BigDecimal("25.00")));
            cryptocurrencyRepository.save(new Cryptocurrency("LINK", "Chainlink", new BigDecimal("15.00")));
            System.out.println("Sample cryptocurrencies initialized");
        }
    }
}