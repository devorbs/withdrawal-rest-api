package com.enviro.assessment.grad001.bokangmakibinye.repository;

// import java.util.List;
// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.bokangmakibinye.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Optional<Product> findById(Long id);
    // List<Product> findAll();
    // List<Product> findByInvestorId(Long investorId);

}
