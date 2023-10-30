import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderManager {
    private static List<Order> orders;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public void createOrder(ShoppingCart cart, double totalCost, String userEmail, Payment payment) {
        Order order = new Order(cart, totalCost, userEmail, payment);
        orders.add(order);
    }

    public Order findOrder(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }

    public void showProductsByCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the main category:");
        System.out.println("1 - Grocery Item");
        System.out.println("2 - Fragile Item");
        System.out.println("3 - Electronics Item");
        String input = sc.nextLine().trim();

        if (Character.isDigit(input.toCharArray()[0]) && input.length() == 1) {
            int itemType = Integer.parseInt(input);
            switch (itemType) {
                case 1 -> {
                    System.out.println("Grocery Items:");
                    for (InventoryItem item : InventoryManager.getInventoryData().values()) {
                        if (item instanceof GroceryItem) {
                            item.getDetails();
                        }
                    }
                    System.out.println();
                }
                case 2 -> {
                    System.out.println("Fragile Items:");
                    for (InventoryItem item : InventoryManager.getInventoryData().values()) {
                        if (item instanceof FragileItem) {
                            item.getDetails();
                        }
                    }
                    System.out.println();
                }
                case 3 -> {
                    System.out.println("Electronic Items:");
                    for (InventoryItem item : InventoryManager.getInventoryData().values()) {
                        if (item instanceof ElectronicsItem) {
                            item.getDetails();
                        }
                    }
                    System.out.println();
                }
                default -> System.out.println("Wrong command, please try again");
            }
        } else {
            System.out.println("Wrong data type");
            System.out.println();
        }

    }
}