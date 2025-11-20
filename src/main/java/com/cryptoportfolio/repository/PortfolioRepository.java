package com.cryptoportfolio.repository;

import com.cryptoportfolio.entity.Portfolio;
import com.cryptoportfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    List<Portfolio> findByUserId(Long userId);
    Optional<Portfolio> findByIdAndUser(Long id, User user);
}