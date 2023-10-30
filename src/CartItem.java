public class CartItem extends InventoryItem {

    public CartItem(String name, String category, double price, int quantity, String itemId) {
        super(name, category, price, quantity, itemId);
    }

    @Override
    public Double calculateValue() {
        return getPrice() * getQuantity();
    }

}
