public class InventoryItem extends AbstractItem {
    private int quantity;
    private String itemId;
    private static int counter = 0;

    public InventoryItem(String name, String category, double price, int quantity) {
        super(name, category, price);
        this.quantity = quantity;
        this.itemId = generateID();
    }

    protected InventoryItem(String name, String category, double price, int quantity, String itemId) {
        super(name, category, price);
        this.quantity = quantity;
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        if (!InventoryManager.getInventoryData().containsKey(itemId)) {
            this.itemId = itemId;
        } else {
            System.out.println("There is another item with this ID in database");
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void getDetails() {
        super.getDetails();
        System.out.printf("Quantity: %-10d ", quantity);
        System.out.printf("Item ID: %-10s ", itemId);
    }

    @Override
    public boolean isSellable() {
        if (getQuantity() <= 0) {
            return false;
        }
        return true;
    }

    public String generateID() {
        String itemId = String.valueOf(10000 + counter);
        while (InventoryManager.getInventoryData().containsKey(itemId)) {
            counter++;
            itemId = String.valueOf(10000 + counter);
        }
        return itemId;
    }

    public String getItemDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%s,%.2f,%d", getClass().getSimpleName(), getItemId(), getName(),
                getCategory(), getPrice(), getQuantity());
        return csvString;
    }

    public static InventoryItem getItemByNameCategoryPrice(String name, String category, double price) {
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item.getName().equals(name) && item.getCategory().equals(category) && item.getPrice() == price) {
                return item;
            }
        }
        return null; // If the object is not found, null is returned
    }
}
