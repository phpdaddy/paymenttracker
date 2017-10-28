package com.phpdaddy.paymenttracker.service;

import com.phpdaddy.paymenttracker.Constants;
import com.phpdaddy.paymenttracker.model.Currency;
import com.phpdaddy.paymenttracker.model.Payment;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentService {

    public Payment readPayment() {
        Scanner in = new Scanner(System.in);
        Pattern p = Pattern.compile(Constants.PAYMENT_FORMAT);
        Matcher m = p.matcher(in.nextLine());
        if (!m.find()) {
            throw new RuntimeException("Incorrect input string");
        }
        return paymentFromStrings(m.group(1), m.group(2));
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
