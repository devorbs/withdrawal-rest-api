package com.enviro.assessment.grad001.bokangmakibinye.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WithdrawalNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investor investor;

    private String accountNumber;
    private String accountHolderName;
    private LocalDate requestedPaydate;
    private LocalDate noticeCreationDate;
    private BigDecimal requestedAmount;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    public LocalDate getRequestedPaydate() {
        return requestedPaydate;
    }
    public void setRequestedPaydate(LocalDate requestedPaydate) {
        this.requestedPaydate = requestedPaydate;
    }
    public LocalDate getNoticeCreationDate() {
        return noticeCreationDate;
    }
    public void setNoticeCreationDate(LocalDate noticeCreationDate) {
        this.noticeCreationDate = noticeCreationDate;
    }
    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    public Investor getInvestor() {
        return investor;
    }
    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    
}
