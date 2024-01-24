package com.enviro.assessment.grad001.bokangmakibinye.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.bokangmakibinye.exceptions.DateCheckFailedException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.NegativeRequestAmountException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.RequestedAmountExceedsMaxAllowedException;
import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
import com.enviro.assessment.grad001.bokangmakibinye.model.Product;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNotice;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeRequest;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeResponse;
import com.enviro.assessment.grad001.bokangmakibinye.repository.InvestorRepository;
import com.enviro.assessment.grad001.bokangmakibinye.repository.ProductRepository;
import com.enviro.assessment.grad001.bokangmakibinye.repository.WithdrawalNoticeRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for handling withdrawal operations.
 */
@Service
public class WithdrawalService {

    private final Logger logger = LoggerFactory.getLogger(WithdrawalService.class);
    private final ProductRepository productRepository;
    private final InvestorRepository investorRepository;
    private final WithdrawalNoticeRepository withdrawalNoticeRepository;

    @Autowired
    public WithdrawalService(ProductRepository productRepository, InvestorRepository investorRepository, WithdrawalNoticeRepository withdrawalNoticeRepository) {
        this.productRepository = productRepository;
        this.investorRepository = investorRepository;
        this.withdrawalNoticeRepository = withdrawalNoticeRepository;
    }

    public boolean verifyWithdrawalNoticeDetails(WithdrawalNoticeRequest withdrawalNoticeRequest) {
        withdrawalNoticeRequest.setNoticeCreationDate(LocalDate.now());
        String accountHolderName = withdrawalNoticeRequest.getAccountHolderName();
        String accountNumber = withdrawalNoticeRequest.getAccountNumber();
        Long investorId = withdrawalNoticeRequest.getInvestorId();
        LocalDate noticeCreationDate = withdrawalNoticeRequest.getNoticeCreationDate();
        LocalDate requestedPayDate = withdrawalNoticeRequest.getRequestedPaydate();
        Long productId = withdrawalNoticeRequest.getProductId();
        BigDecimal requestedAmount = withdrawalNoticeRequest.getRequestedAmount();
        BigDecimal productBalance = getProductById(withdrawalNoticeRequest.getProductId()).getBalance();
        BigDecimal maxAllowedAmount = productBalance.multiply(new BigDecimal("0.9"));  

        
        if(accountHolderName != null && accountNumber != null && investorId != null && noticeCreationDate != null &&
            requestedPayDate != null && productId != null && requestedAmount != null) { 

            if(requestedAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new NegativeRequestAmountException("Requested Amount cannot be a negative number.");
            }

            if(requestedPayDate.isAfter(noticeCreationDate)) {
                throw new DateCheckFailedException("Requested Paydate cannot in the past.");
            } 
            
            if(requestedAmount.compareTo(maxAllowedAmount) <= 0) {
                throw new RequestedAmountExceedsMaxAllowedException("Requested amount cannot be more than 90% of the current balance.");
            } 
            return true;
        }
        return false;    
    }

    @Transactional
    public WithdrawalNoticeResponse createWithdrawal(WithdrawalNoticeRequest withdrawalNoticeRequest) {
        
        Product product = getProductById(withdrawalNoticeRequest.getProductId());
        Investor investor = getInvestorById(withdrawalNoticeRequest.getInvestorId());
        WithdrawalNotice notice = generateWithdrawalNotice(withdrawalNoticeRequest, product, investor);
        BigDecimal prevBal = product.getBalance();
        BigDecimal newBal = prevBal.subtract(withdrawalNoticeRequest.getRequestedAmount());


        product.setBalance(newBal);
        if (notice != null) withdrawalNoticeRepository.save(notice);
        
        WithdrawalNoticeResponse response = new WithdrawalNoticeResponse();
        response.setNewBalance(newBal);
        response.setPreviousBalance(prevBal);
        response.setWithdrawnBalance(withdrawalNoticeRequest.getRequestedAmount());

        return response;
    }

    private Product getProductById(Long id) {
        Product product;
        try {
            product = productRepository.getReferenceById(id);
        } catch (EntityNotFoundException productNotFound) {
            logger.error("Product not found.", productNotFound);
            throw new NotFoundException("Product not found.", productNotFound);
        }
        return product;
    }

    private Investor getInvestorById(Long id) {
        Investor investor;
        try {
            Objects.requireNonNull(id, "Investor ID can't be null");
            investor = investorRepository.getReferenceById(id);
        } catch (EntityNotFoundException investorNotFound) {
            logger.error("Investor not found.", investorNotFound);
            throw new NotFoundException("Investor not found.", investorNotFound);
        } 
        return investor;
    }

    private WithdrawalNotice generateWithdrawalNotice(WithdrawalNoticeRequest withdrawalNoticeRequest, Product product, Investor investor) {
        WithdrawalNotice notice = new WithdrawalNotice();

        notice.setAccountHolderName(withdrawalNoticeRequest.getAccountHolderName());
        notice.setAccountNumber(withdrawalNoticeRequest.getAccountNumber());
        notice.setNoticeCreationDate(withdrawalNoticeRequest.getNoticeCreationDate());
        notice.setProduct(product);
        notice.setInvestor(investor);
        notice.setRequestedAmount(withdrawalNoticeRequest.getRequestedAmount());
        notice.setRequestedPaydate(withdrawalNoticeRequest.getRequestedPaydate());

        return notice;
    }
}
