package com.phpdaddy.paymenttracker.service;

import com.phpdaddy.paymenttracker.Constants;
import com.phpdaddy.paymenttracker.model.Currency;
import com.phpdaddy.paymenttracker.model.Payment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentService {

    private final ArrayList<Payment> payments = new ArrayList<>();

    public Payment readPayment(String inputString) {
        Pattern p = Pattern.compile(Constants.PAYMENT_FORMAT);
        Matcher m = p.matcher(inputString);
        if (!m.find()) {
            throw new RuntimeException("Incorrect input string");
        }
        Payment payment = paymentFromStrings(m.group(1), m.group(2));
        payments.add(payment);
        return payment;
    }

    private static Payment paymentFromStrings(String _currency, String _value) {
        Currency currency;
        try {
            currency = Currency.valueOf(_currency);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Currency does not exist");
        }
        Integer value = Integer.parseInt(_value);
        return new Payment(currency, value);
    }
}
