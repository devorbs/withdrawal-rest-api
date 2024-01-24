package com.enviro.assessment.grad001.bokangmakibinye.model;

import java.math.BigDecimal;

public class WithdrawalNoticeResponse {
    private BigDecimal previousBalance;
    private BigDecimal withdrawnBalance;
    private BigDecimal newBalance;

    
    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }
    public void setPreviousBalance(BigDecimal previousBalance) {
        this.previousBalance = previousBalance;
    }
    public BigDecimal getWithdrawnBalance() {
        return withdrawnBalance;
    }
    public void setWithdrawnBalance(BigDecimal withdrawnBalance) {
        this.withdrawnBalance = withdrawnBalance;
    }
    public BigDecimal getNewBalance() {
        return newBalance;
    }
    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    
}
