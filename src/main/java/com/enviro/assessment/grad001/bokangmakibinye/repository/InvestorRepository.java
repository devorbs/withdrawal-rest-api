package com.enviro.assessment.grad001.bokangmakibinye.repository;

import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Optional<Investor> findById(Long id);
}
