import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingCart {

    private List<CartItem> cart = new ArrayList<>();
    private String email;

    public ShoppingCart(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    double calculateCartValue() {
        double cartValue = 0;
        for (CartItem item : getCart()) {
            cartValue += item.calculateValue();
        }
        return cartValue;
    }

    public void showCart() {
        if (getCart().isEmpty()) {
            System.out.println("Your cart is empty, please add products");
            System.out.println();
        } else {
            System.out.printf("%-10s %-25s %-10s %-15s %-10s%n", "ID", "Name", "Price", "Quantity", "Value");
            for (CartItem item : getCart()) {
                System.out.printf("%-10s %-25s %-10.2f %-15d %-10.2f%n", item.getItemId(), item.getName(), item.getPrice(),
                        item.getQuantity(), item.calculateValue());
            }
            System.out.println("-".repeat(70));
            System.out.printf("Total value: %.2f%n", calculateCartValue());
            System.out.println();
        }

    }

    public void addToCart(String itemId) {
        Scanner sc = new Scanner(System.in);
        boolean checkQuantity = false;
        while (!checkQuantity) {
            try {
                InventoryItem invItem = InventoryManager.getInventoryItem(itemId);
                invItem.getDetails();
                System.out.println("Input quantity you want to order: ");
                int quantity = Integer.parseInt(sc.nextLine().trim());
                if (invItem.getQuantity() >= quantity && quantity > 0 && !isItemInCart(itemId)) {
                    CartItem cartItem = new CartItem(invItem.getName(), invItem.getCategory(), invItem.getPrice(), quantity,
                            invItem.getItemId());
                    invItem.setQuantity(invItem.getQuantity() - quantity);
                    getCart().add(cartItem);
                    checkQuantity = true;
                } else if (isItemInCart(itemId)) {
                    int quantityInCart = findCartItemById(itemId).getQuantity();
                    int newQuantity = quantityInCart + quantity;
                    removeFromCart(itemId);
                    CartItem cartItem = new CartItem(invItem.getName(), invItem.getCategory(), invItem.getPrice(), newQuantity,
                            invItem.getItemId());
                    getCart().add(cartItem);
                    checkQuantity = true;
                } else if (quantity <= 0) {
                    System.out.printf("You can order only positive number of %s. Please correct the order quantity:%n", invItem.getName());
                } else {
                    System.out.printf("%s - quantity in stock is: %d. Please correct the order quantity%n", invItem.getName(), invItem.getQuantity());
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong data. Please input the order quantity: ");
            }
        }
    }

    public boolean isItemInCart(String itemIdToRemove) {
        for (CartItem itemToRemove : cart) {
            if (itemToRemove.getItemId().equals(itemIdToRemove)) {
                return true;
            }
        }
        return false;
    }

    public void removeFromCart(String itemIdToRemove) {
        for (CartItem item : getCart()) {
            if (item.getItemId().equals(itemIdToRemove)) {
                int quantity = item.getQuantity();
                getCart().remove(item);
                int stockQuantity = InventoryManager.getInventoryItem(itemIdToRemove).getQuantity();
                InventoryManager.getInventoryItem(itemIdToRemove).setQuantity(stockQuantity + quantity);
            }
        }
    }

    public CartItem findCartItemById(String id) {
        for (CartItem item : getCart()) {
            if (item.getItemId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}