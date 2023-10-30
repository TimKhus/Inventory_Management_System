public class Order {
    private static int orderCounter = 1; // unique order numbers counter
    private int orderNumber;
    private ShoppingCart cart;
    private double totalCost;
    private String userEmail;
    private Payment payment;

    public Order(ShoppingCart cart, double totalCost, String userEmail, Payment payment) {
        this.orderNumber = orderCounter++;
        this.cart = cart;
        this.totalCost = totalCost;
        this.userEmail = userEmail;
        this.payment = payment;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public ShoppingCart getShoppingCart() {
        return cart;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Payment getPayment() {
        return payment;
    }

    public String toString() {
        return String.format("Order number: %3d   Number of products: %2d   Value: %.2f%n", getOrderNumber(),
                getShoppingCart().getCart().size(), getShoppingCart().calculateCartValue());
    }

    public String getOrderDataInCSVFormat() {
        StringBuilder csvString = new StringBuilder();
        int cartSize = cart.getCart().size();
        int counter = 0;

        csvString.append(getClass().getSimpleName()).append(",");
        csvString.append(orderNumber).append(",");
        csvString.append(cart.getEmail()).append(",");
        csvString.append(totalCost).append(",");
        csvString.append(payment.isSuccessfulPayment()).append(",");
        csvString.append(cart.getCart().size()).append(",");
        csvString.append("\n");

        for (CartItem cartItem : cart.getCart()) {
            csvString.append(cartItem.getItemDataInCSVFormat());
            counter++;
            if (counter < cartSize) {
                csvString.append("\n");
            }
        }

        return csvString.toString();
    }

    public void display() {
        System.out.printf("Email: %-30s Order number: %-5d Number of products: %-3d Value: %-6.2f%n",
                getUserEmail(), getOrderNumber(), getShoppingCart().getCart().size(), getShoppingCart().calculateCartValue());
    }
}