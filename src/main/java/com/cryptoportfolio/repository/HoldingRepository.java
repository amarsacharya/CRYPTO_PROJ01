package com.cryptoportfolio.repository;

import com.cryptoportfolio.entity.Holding;
import com.cryptoportfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByPortfolio(Portfolio portfolio);
    List<Holding> findByPortfolioId(Long portfolioId);
    Optional<Holding> findByIdAndPortfolio(Long id, Portfolio portfolio);
}