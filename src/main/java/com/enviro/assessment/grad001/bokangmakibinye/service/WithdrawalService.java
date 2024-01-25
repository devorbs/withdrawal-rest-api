package com.enviro.assessment.grad001.bokangmakibinye.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.bokangmakibinye.exceptions.AgeLimitException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.DateCheckFailedException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.InvestorIdMismatchException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.NegativeRequestAmountException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bokangmakibinye.exceptions.RequestedAmountExceedsMaxAllowedException;
import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
import com.enviro.assessment.grad001.bokangmakibinye.model.Product;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNotice;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeCsvDTO;
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

    /**
     * Verifies the details of a withdrawal notice request.
     *
     * @param withdrawalNoticeRequest The withdrawal notice request to be verified.
     * @return True if the details are valid, false otherwise.
     * @throws NegativeRequestAmountException If the requested amount is negative.
     * @throws DateCheckFailedException        If the requested pay date is in the past.
     * @throws RequestedAmountExceedsMaxAllowedException If the requested amount exceeds 90% of the current balance.
     * @throws AgeLimitException               If the age is less than 65 for retirement products.
     */
    public boolean verifyWithdrawalNoticeDetails(WithdrawalNoticeRequest withdrawalNoticeRequest) {
        withdrawalNoticeRequest.setNoticeCreationDate(LocalDate.now());
        String accountHolderName = withdrawalNoticeRequest.getAccountHolderName();
        String accountNumber = withdrawalNoticeRequest.getAccountNumber();
        Long investorId = withdrawalNoticeRequest.getInvestorId();
        LocalDate noticeCreationDate = withdrawalNoticeRequest.getNoticeCreationDate();
        LocalDate requestedPayDate = withdrawalNoticeRequest.getRequestedPaydate();
        Long productId = withdrawalNoticeRequest.getProductId();
        Product product = getProductById(withdrawalNoticeRequest.getProductId());
        BigDecimal requestedAmount = withdrawalNoticeRequest.getRequestedAmount();
        BigDecimal productBalance = product.getBalance();
        BigDecimal maxAllowedAmount = productBalance.multiply(new BigDecimal("0.9"));
        Investor investor = getInvestorById(investorId);  

        
        if(accountHolderName != null && accountNumber != null && investorId != null && noticeCreationDate != null &&
            requestedPayDate != null && productId != null && requestedAmount != null) { 

            if(requestedAmount.compareTo(BigDecimal.ZERO) < 0) {
                String errorMessage = "Requested Amount cannot be a negative number.";
                logger.error(errorMessage);
                throw new NegativeRequestAmountException(errorMessage);
            }

            if(!requestedPayDate.isAfter(noticeCreationDate)) {
                String errorMessage = "Requested Paydate cannot be in the past.";
                logger.error(errorMessage);
                throw new DateCheckFailedException(errorMessage);
            } 
            
            if(!(requestedAmount.compareTo(maxAllowedAmount) <= 0)) {
                String errorMessage = "Requested amount cannot be more than 90% of the current balance.";
                logger.error(errorMessage);
                throw new RequestedAmountExceedsMaxAllowedException(errorMessage);
            }
            
            if(investor.getAge() < 65 && product.getType().equalsIgnoreCase("retirement")) {
                String errorMessage = "Age cannot be less than 65 for retirement products.";
                logger.error(errorMessage);
                throw new AgeLimitException(errorMessage);
            }
            return true;
        }
        return false;    
    }

    /**
     * Creates a withdrawal based on the provided withdrawal notice request.
     *
     * @param withdrawalNoticeRequest The withdrawal notice request.
     * @return The withdrawal notice response.
     * @throws InvestorIdMismatchException If the investor does not own the specified product.
     */
    @Transactional
    public WithdrawalNoticeResponse createWithdrawal(WithdrawalNoticeRequest withdrawalNoticeRequest) {
        
        Product product = getProductById(withdrawalNoticeRequest.getProductId());
        Investor investor = getInvestorById(withdrawalNoticeRequest.getInvestorId());
        WithdrawalNotice notice = null;

        if (investor.getId() == product.getInvestor().getId()){
            notice = generateWithdrawalNotice(withdrawalNoticeRequest, product, investor);
        } else {
            throw new InvestorIdMismatchException("Investor doesn't own product with id: " + product.getId());
        }

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

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product with the specified ID.
     * @throws NotFoundException If the product with the specified ID is not found.
     */
    private Product getProductById(Long id) {
        Product product;
        try {
            Objects.requireNonNull(id, "Product ID can't be null");
            product = productRepository.getReferenceById(id);
        } catch (EntityNotFoundException productNotFound) {
            logger.error("Product not found with id: "+ id);
            throw new NotFoundException("Product not found.", productNotFound);
        }
        return product;
    }

    /**
     * Retrieves an investor by their ID.
     *
     * @param id The ID of the investor.
     * @return The investor with the specified ID.
     * @throws NotFoundException If the investor with the specified ID is not found.
     */
    private Investor getInvestorById(Long id) {
        Investor investor;
        try {
            Objects.requireNonNull(id, "Investor ID can't be null");
            investor = investorRepository.getReferenceById(id);
        } catch (EntityNotFoundException investorNotFound) {
            logger.error("Investor not found with id "+ id);
            throw new NotFoundException("Investor not found.", investorNotFound);
        } 
        return investor;
    }

    /**
     * Generates a withdrawal notice based on the provided request, product, and investor.
     *
     * @param withdrawalNoticeRequest The withdrawal notice request.
     * @param product                 The associated product.
     * @param investor                The associated investor.
     * @return The generated withdrawal notice.
     */
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

    /**
     * Downloads withdrawal notices for a specific investor.
     *
     * @param investorId The ID of the investor.
     * @return A list of withdrawal notices in CSV DTO format.
     */
    public List<WithdrawalNoticeCsvDTO> downloadWithdrawalNotices(Long investorId) {
        
        List<WithdrawalNoticeCsvDTO> dtos = new ArrayList<>();
        Investor investor = getInvestorById(investorId);
        List<WithdrawalNotice> withdrawalNotices = withdrawalNoticeRepository.findByInvestor(investor);

        for (WithdrawalNotice withdrawalNotice : withdrawalNotices) {
            Investor nested_investor = withdrawalNotice.getInvestor();
            Product product = withdrawalNotice.getProduct();
    
            WithdrawalNoticeCsvDTO dto = new WithdrawalNoticeCsvDTO(
                    withdrawalNotice.getId(),
                    withdrawalNotice.getAccountNumber(),
                    withdrawalNotice.getAccountHolderName(),
                    withdrawalNotice.getRequestedPaydate(),
                    withdrawalNotice.getNoticeCreationDate(),
                    withdrawalNotice.getRequestedAmount(),
                    nested_investor.getId(),
                    nested_investor.getFirstName(),
                    nested_investor.getLastName(),
                    product.getId(),
                    product.getProductName(),
                    product.getBalance()
            );
    
            dtos.add(dto);
        }
        return dtos;
    }
}
