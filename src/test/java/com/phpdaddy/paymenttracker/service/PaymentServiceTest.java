package com.phpdaddy.paymenttracker.service;


import com.phpdaddy.paymenttracker.model.Currency;
import com.phpdaddy.paymenttracker.model.Payment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class PaymentServiceTest {

    private final PaymentService paymentService = new PaymentService();

    @Test
    public void testReadPayment_Success() throws Exception {
        Payment payment = paymentService.readPayment("USD 123");

        assertEquals(payment.getCurrency(), Currency.USD);
        assertEquals(payment.getValue().intValue(), 123);
    }

    @Test
    public void testReadPayment_IncorrectInputString() throws Exception {
        try {
            paymentService.readPayment("USDwad 123");

            fail("Should have thrown exception but did not!");
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Incorrect input string");
        }
    }

    @Test
    public void testReadPayment_IncorrectCurrency() throws Exception {

        try {
            paymentService.readPayment("AWD 123");

            fail("Should have thrown exception but did not!");
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Currency does not exist");
        }
    }
}
