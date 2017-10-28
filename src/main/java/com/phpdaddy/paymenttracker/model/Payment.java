package com.phpdaddy.paymenttracker.model;

public class Payment {
    private Currency currency;
    private Integer value;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
