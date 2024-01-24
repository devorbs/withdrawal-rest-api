package com.enviro.assessment.grad001.bokangmakibinye.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
import com.enviro.assessment.grad001.bokangmakibinye.repository.InvestorRepository;

/**
 * Service class for handling operations related to investors.
 */
@Service
public class InvestorService {
    
    private final InvestorRepository investorRepository;

    /**
     * Constructs a new InvestorService with the specified investor repository.
     *
     * @param investorRepository The repository for investor-related operations.
     */
    @Autowired
    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    /**
     * Retrieves an investor by the specified ID.
     *
     * @param id The ID of the investor to retrieve.
     * @return An Optional containing the investor if found, otherwise an empty Optional.
     */
    public Optional<Investor> getInvestorById(Long id) {
        return investorRepository.findById(id);
    }
}
