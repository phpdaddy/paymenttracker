import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private final static String inputFormat = "([A-Z]{3}) (-?\\d+)";
    private final static ArrayList<Pair<Currency, Integer>> payments = new ArrayList<>();
    private final static HashMap<Currency, Integer> balance = new HashMap<Currency, Integer>();

    public static void main(String[] args) {
        scheduleNetAmounts();

        while (true) {
            processInput();
        }
    }

    private static void processInput() {
        Pair<Currency, Integer> pair;
        try {
            pair = readPayment();
            payments.add(pair);
            balance.put(pair.getKey(), balance.get(pair.getKey()) + pair.getValue());
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

    private static Pair<Currency, Integer> readPayment() {
        Scanner in = new Scanner(System.in);
        Pattern p = Pattern.compile(inputFormat);
        Matcher m = p.matcher(in.nextLine());
        if (!m.find()) {
            throw new RuntimeException("Incorrect input string");
        }
        return paymentFromStrings(m.group(1), m.group(2));
    }

    private static Pair<Currency, Integer> paymentFromStrings(String _currency, String _value) {
        Currency currency;
        try {
            currency = Currency.valueOf(_currency);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Currency does not exist");
        }
        Integer value = Integer.parseInt(_value);
        return new Pair<>(currency, value);
    }
}
