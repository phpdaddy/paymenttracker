package com.phpdaddy.paymenttracker.model;

public enum Currency {
    USD(1), HKD(0.13f), RMB(0.15f), NZD(0.69f), GBP(1.31f);

    private float rate;

    Currency(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
