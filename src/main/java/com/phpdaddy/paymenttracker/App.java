package com.phpdaddy.paymenttracker;

import com.phpdaddy.paymenttracker.model.Currency;
import com.phpdaddy.paymenttracker.model.Payment;
import com.phpdaddy.paymenttracker.service.PaymentService;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    private final static PaymentService paymentService = new PaymentService();
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
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        try {
            if (line.equals("quit")) {
                System.exit(0);
            }
            payment = paymentService.readPayment(line);
        } catch (RuntimeException ex) {
            System.out.println("!!! " + ex.getLocalizedMessage());
            return;
        }
        int currentBalance = 0;
        if (balance.get(payment.getCurrency()) != null) {
            currentBalance = balance.get(payment.getCurrency());
        }
        balance.put(payment.getCurrency(), currentBalance + payment.getValue());
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
