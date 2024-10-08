package org.finterest.invest.conversion.dto;

import java.math.BigDecimal;

public class ConversionRequestDTO {
    private int pointAmount;
    private BigDecimal moneyAmount;

    public int getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}