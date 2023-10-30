import java.util.Scanner;

public class PaymentProcessor {

    public static boolean processPayment(double cartValue, Payment payment) {
        Scanner sc = new Scanner(System.in);
        boolean paymentProcessed = false;

        while (!paymentProcessed) {
            System.out.printf("The amount to pay: %.2f%n", cartValue);
            System.out.println("Please input your card number (16 digits):");
            String cardNumberString = sc.nextLine().trim();

            // Checking the length and format of the card number
            if (cardNumberString.length() == 16 && cardNumberString.matches("\\d{16}")) {
                System.out.println("Input amount to pay:");

                try {
                    double amountToPay = Double.parseDouble(sc.nextLine().trim());

                    // Checking the payment amount
                    if (amountToPay == cartValue) {
                        System.out.println("Processing payment...");
                        payment.setSuccessfulPayment();
                        paymentProcessed = true;
                        System.out.println("Payment processed successfully");
                        System.out.println("Email with order details and delivery time sent to you");
                    } else {
                        System.out.println("Incorrect payment amount. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid payment amount.");
                }
            } else {
                System.out.println("Invalid card number. Please enter a valid 16-digit card number.");
            }
        }

        return paymentProcessed;
    }
}
