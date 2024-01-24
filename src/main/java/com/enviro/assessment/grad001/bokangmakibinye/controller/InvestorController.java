package com.enviro.assessment.grad001.bokangmakibinye.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
import com.enviro.assessment.grad001.bokangmakibinye.service.InvestorService;

/**
 * Controller class for handling investor-related HTTP requests.
 */
@RestController
@RequestMapping("/investor")
public class InvestorController {
    
    private final InvestorService investorService;

    /**
     * Constructs a new InvestorController with the specified investor service.
     *
     * @param investorService The service responsible for handling investor-related operations.
     */
    @Autowired
    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    /**
     * Retrieves an investor by the specified ID.
     *
     * @param id The ID of the investor to retrieve.
     * @return ResponseEntity containing the retrieved investor or a not found response if the investor is not present.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Investor> getInvestorById(@PathVariable Long id) {
        Optional<Investor> investor = investorService.getInvestorById(id);

        if (investor.isPresent()) {
            return ResponseEntity.ok(investor.get());
        }
        return ResponseEntity.notFound().build();
    }
}
