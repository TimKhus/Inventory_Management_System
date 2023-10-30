public class BrokenItem extends AbstractItem {
    private int quantity;
    private String itemId;

    public BrokenItem(String itemId, String name, String category, double price, int quantity) {
        super(name, category, price);
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public static BrokenItem getBrokenItem(String itemId) {
        for (AbstractItem item : SpoiledManager.getSpoiledItems()) {
            if (item instanceof BrokenItem && ((BrokenItem) item).getItemId().equals(itemId)) {
                return (BrokenItem) item;
            }
        }
        return null;
    }

    @Override
    public Double calculateValue() {
        return getPrice() * getQuantity();
    }

    @Override
    public String getItemDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%s,%.2f,%d,%s", getClass().getSimpleName(), getItemId(), getName(),
                getCategory(), getPrice(), getQuantity(), "Broken");
        return csvString;
    }

    @Override
    public void getDetails() {
        super.getDetails();
        System.out.printf("Quantity: %-10d%n", quantity);
    }
}
