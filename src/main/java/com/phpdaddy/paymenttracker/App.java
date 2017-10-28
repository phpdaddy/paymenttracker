package com.phpdaddy.paymenttracker;

import com.phpdaddy.paymenttracker.model.Currency;
import com.phpdaddy.paymenttracker.model.Payment;
import com.phpdaddy.paymenttracker.service.PaymentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    private final static PaymentService paymentService = new PaymentService();
    private final static ArrayList<Payment> payments = new ArrayList<>();
    private final static HashMap<Currency, Integer> balance = new HashMap<>();

    public static void main(String[] args) {
        scheduleNetAmounts();

        while (true) {
            processInput();
        }
    }

    private static void processInput() {
        Payment payment;
        try {
            payment = paymentService.readPayment();
            payments.add(payment);
            balance.put(payment.getCurrency(), balance.get(payment.getCurrency()) + payment.getValue());
        } catch (Exception ex) {
            System.out.println("!!! " + ex.getLocalizedMessage());
        }
    }

    private static void scheduleNetAmounts() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("***********************");
                System.out.println("****** NET AMOUNTS ****");
                balance.forEach((currency, value) -> {
                    System.out.println(currency + " " + value);
                });
                System.out.println("***********************");
            }
        }, 0, 60 * 100);
    }

}
