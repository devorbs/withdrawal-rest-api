package com.enviro.assessment.grad001.bokangmakibinye.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
import com.enviro.assessment.grad001.bokangmakibinye.model.Product;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNotice;

public interface WithdrawalNoticeRepository extends JpaRepository<WithdrawalNotice, Long> {
    
    List<WithdrawalNotice> findByProduct(Product product);
    List<WithdrawalNotice> findByInvestor(Investor investor);
    
}
