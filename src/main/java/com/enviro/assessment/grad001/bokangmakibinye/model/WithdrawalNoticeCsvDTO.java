package com.enviro.assessment.grad001.bokangmakibinye.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WithdrawalNoticeCsvDTO {
    private Long withdrawalNoticeId;
    private String accountNumber;
    private String accountHolderName;
    private LocalDate requestedPaydate;
    private LocalDate noticeCreationDate;
    private BigDecimal requestedAmount;
    private Long investorId;
    private String investorFirstName;
    private String investorLastName;
    private Long productId;
    private String productName;
    private BigDecimal productBalance;


    public WithdrawalNoticeCsvDTO(Long withdrawalNoticeId, String accountNumber, String accountHolderName,
            LocalDate requestedPaydate, LocalDate noticeCreationDate, BigDecimal requestedAmount, Long investorId,
            String investorFirstName, String investorLastName, Long productId, String productName,
            BigDecimal productBalance) {
        this.withdrawalNoticeId = withdrawalNoticeId;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.requestedPaydate = requestedPaydate;
        this.noticeCreationDate = noticeCreationDate;
        this.requestedAmount = requestedAmount;
        this.investorId = investorId;
        this.investorFirstName = investorFirstName;
        this.investorLastName = investorLastName;
        this.productId = productId;
        this.productName = productName;
        this.productBalance = productBalance;
    }

    public Long getWithdrawalNoticeId() {
        return withdrawalNoticeId;
    }

    public void setWithdrawalNoticeId(Long withdrawalNoticeId) {
        this.withdrawalNoticeId = withdrawalNoticeId;
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

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public String getInvestorFirstName() {
        return investorFirstName;
    }

    public void setInvestorFirstName(String investorFirstName) {
        this.investorFirstName = investorFirstName;
    }

    public String getInvestorLastName() {
        return investorLastName;
    }

    public void setInvestorLastName(String investorLastName) {
        this.investorLastName = investorLastName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductBalance() {
        return productBalance;
    }

    public void setProductBalance(BigDecimal productBalance) {
        this.productBalance = productBalance;
    }
}
