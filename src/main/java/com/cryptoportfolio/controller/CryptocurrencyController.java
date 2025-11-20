package com.cryptoportfolio.controller;

import com.cryptoportfolio.dto.CryptocurrencyDto;
import com.cryptoportfolio.service.CryptocurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cryptocurrencies")
@Tag(name = "Cryptocurrency", description = "Cryptocurrency management APIs")
public class CryptocurrencyController {

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @GetMapping
    @Operation(summary = "Get all cryptocurrencies", description = "Get all available cryptocurrencies")
    public ResponseEntity<List<CryptocurrencyDto>> getAllCryptocurrencies() {
        List<CryptocurrencyDto> cryptocurrencies = cryptocurrencyService.getAllCryptocurrencies();
        return ResponseEntity.ok(cryptocurrencies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cryptocurrency by ID", description = "Get a specific cryptocurrency by its ID")
    public ResponseEntity<CryptocurrencyDto> getCryptocurrencyById(@PathVariable Long id) {
        CryptocurrencyDto cryptocurrency = cryptocurrencyService.getCryptocurrencyById(id);
        return ResponseEntity.ok(cryptocurrency);
    }

    @PostMapping
    @Operation(summary = "Create cryptocurrency", description = "Create a new cryptocurrency")
    public ResponseEntity<CryptocurrencyDto> createCryptocurrency(@Valid @RequestBody CryptocurrencyDto cryptocurrencyDto) {
        CryptocurrencyDto createdCryptocurrency = cryptocurrencyService.createCryptocurrency(cryptocurrencyDto);
        return ResponseEntity.ok(createdCryptocurrency);
    }
}