package com.enviro.assessment.grad001.bokangmakibinye.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WithdrawalNoticeRequest {
    
    private Long investorId;
    private Long productId;
    private String accountNumber;
    private String accountHolderName;
    private LocalDate requestedPaydate;
    private LocalDate noticeCreationDate;
    private BigDecimal requestedAmount;


    @Override
    public String toString() {
        // Curren
        return "WithdrawalNoticeRequest [investorId=" + investorId + ", productId=" + productId + ", accountNumber="
                + accountNumber + ", accountHolderName=" + accountHolderName + ", requestedPaydate=" + requestedPaydate
                + ", noticeCreationDate=" + noticeCreationDate + ", requestedAmount=" + requestedAmount + "]";
    }

    public Long getInvestorId() {
        return investorId;
    }
    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
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
}
