package com.phpdaddy.paymenttracker;

import com.phpdaddy.paymenttracker.model.Currency;
import com.phpdaddy.paymenttracker.model.Payment;
import com.phpdaddy.paymenttracker.service.PaymentService;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    private final static PaymentService paymentService = new PaymentService();
    private final static HashMap<Currency, Integer> balance = new HashMap<>();

    public static void main(String[] args) {
        if (args.length == 1) {
            File file = new File(args[0]);
            readPaymentsFromFile(file);
        }
        scheduleNetAmounts();
        while (true) {
            System.out.println("Enter payment in format 'USD 123' or 'quit' for exit or 'show' to show net amounts");
            processInput();
        }
    }

    private static void processInput() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        if (line.equals("quit")) {
            System.exit(0);
        } else if (line.equals("show")) {
            showNetAmounts();
            return;
        }
        readPayments(line);
    }

    private static void readPaymentsFromFile(File file) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                readPayments(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + file.getAbsolutePath() + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + file.getAbsolutePath() + "'");
        }
    }


    private static void readPayments(String line) {
        Payment payment;
        try {
            payment = paymentService.readPayment(line);
        } catch (RuntimeException ex) {
            System.out.println("!!! " + ex.getLocalizedMessage());
            return;
        }
        int currentBalance = 0;
        if (balance.get(payment.getCurrency()) != null) {
            currentBalance = balance.get(payment.getCurrency());
        }
        int newBalance = currentBalance + payment.getValue();
        if (newBalance <= 0) {
            balance.remove(payment.getCurrency());
            return;
        }
        balance.put(payment.getCurrency(), newBalance);
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
            System.out.println(currency + " " + value + " (USD " + (currency.getRate() * value) + ")");
        });
        System.out.println("**********************>");
    }
}
