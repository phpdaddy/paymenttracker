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
            System.out.println("Enter payment in format 'USD 123'");
            processInput();
        }
    }

    private static void processInput() {
        Payment payment;
        try {
            payment = paymentService.readPayment();
            payments.add(payment);
            int currentBalance = 0;
            if (balance.get(payment.getCurrency()) != null) {
                currentBalance = balance.get(payment.getCurrency());
            }
            balance.put(payment.getCurrency(), currentBalance + payment.getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("!!! " + ex.getLocalizedMessage());
        }
    }

    private static void scheduleNetAmounts() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                showNetAmounts();
            }
        }, 60 * 1000, 60 * 1000);
    }

    private static void showNetAmounts() {
        System.out.println("<**********************");
        System.out.println("****** NET AMOUNTS ****");
        balance.forEach((currency, value) -> {
            System.out.println(currency + " " + value);
        });
        System.out.println("**********************>");
    }
}
