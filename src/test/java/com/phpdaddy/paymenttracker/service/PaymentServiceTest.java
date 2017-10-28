package com.phpdaddy.paymenttracker.service;


import com.phpdaddy.paymenttracker.model.Currency;
import com.phpdaddy.paymenttracker.model.Payment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class PaymentServiceTest {

    private final PaymentService paymentService = new PaymentService();

    @Test
    public void testReadPayment_Success() throws Exception {
        String input = "USD 123";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Payment payment = paymentService.readPayment();

        assertEquals(payment.getCurrency(), Currency.USD);
        assertEquals(payment.getValue().intValue(), 123);
    }

    @Test
    public void testReadPayment_IncorrectInputString() throws Exception {
        try {
            String input = "USDwad 123";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            paymentService.readPayment();

            fail("Should have thrown exception but did not!");
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Incorrect input string");
        }
    }

    @Test
    public void testReadPayment_IncorrectCurrency() throws Exception {

        try {
            String input = "AWD 123";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            paymentService.readPayment();

            fail("Should have thrown exception but did not!");
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Currency does not exist");
        }
    }
}
