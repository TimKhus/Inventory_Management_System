public class SpoiledItem extends AbstractItem {
    private int quantity;
    private String itemId;
    private String expiredDate;

    public SpoiledItem(String itemId, String name, String category, double price, int quantity, String expiredDate) {
        super(name, category, price);
        this.itemId = itemId;
        this.quantity = quantity;
        this.expiredDate = expiredDate;
    }

    public static SpoiledItem getSpoiledItem(String itemId) {
        for (AbstractItem item : SpoiledManager.getSpoiledItems()) {
            if (item instanceof SpoiledItem && ((SpoiledItem) item).getItemId().equals(itemId)) {
                return (SpoiledItem) item;
            }
        }
        return null;
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

    public String getExpiredDate() {
        return expiredDate;
    }

    @Override
    public String getItemDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%s,%.2f,%d,%s", getClass().getSimpleName(), getItemId(), getName(),
                getCategory(), getPrice(), getQuantity(), getExpiredDate());
        return csvString;
    }

    @Override
    public Double calculateValue() {
        return getPrice() * getQuantity();
    }

    @Override
    public void getDetails() {
        super.getDetails();
        System.out.printf("Quantity: %-10d", quantity);
        System.out.printf("Expire date: %-10s%n", getExpiredDate());
    }

}
