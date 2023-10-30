import java.util.Scanner;

public class ElectronicsItem extends InventoryItem {
    private String color;

    public ElectronicsItem(String name, String category, double price, int quantity, String color) {
        super(name, category, price, quantity);
        if (!doesItemExist(name, category, price, color)) {
            // object with these parameters does not exist, add
            this.color = color;
                InventoryManager.addInventoryItem(this);
        } else {
            // object with these parameters already exists, ask user to add the quantity
            newDelivery(name, category, price, quantity, color);
        }
    }

    private ElectronicsItem(String name, String category, double price, int quantity, String color, String itemId) {
        super(name, category, price, quantity, itemId);
        this.color = color;
        if (!doesItemExist(name, category, price, color)) {
            InventoryManager.addInventoryItem(this);
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void getDetails() {
        super.getDetails();
        System.out.printf("Color: %-10s%n", color);
    }

    @Override
    public void displayDescription() {
        super.displayDescription();
        System.out.printf("Color: %s%n", color);
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public void handleBreak() {
        if (getQuantity() > 0) {
            System.out.println("Broken items found. Procedure for correcting stock");
            Scanner sc = new Scanner(System.in);
            int brokenQuantity = -1;
            while (brokenQuantity < 0) {
                try {
                    System.out.printf("Enter the quantity of broken %s:%n", getName());
                    brokenQuantity = Integer.parseInt(sc.nextLine().trim());
                    if (brokenQuantity <= getQuantity()) {
                        System.out.printf("Broken items, quantity: %d%n", brokenQuantity);
                        setQuantity(getQuantity() - brokenQuantity);
                        System.out.println("New quantity is: " + getQuantity());
                        BrokenItem brokenItem = new BrokenItem(getItemId(), getName(), getCategory(), getPrice(), brokenQuantity);
                        SpoiledManager.addSpoiledItem(brokenItem);
                    } else {
                        System.out.printf("We have %d items on stock. Please try again%n", getQuantity());
                        brokenQuantity = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong input");
                }
            }
        } else {
            System.out.println("Remaining stock of this item: 0");
        }
    }

    @Override
    public Double calculateValue() {
        // if there are less than 5 items in stock - 5% discount
        if (getQuantity() <= 4) {
            return super.getPrice() * 0.95;
        }
        return super.getPrice();
    }

    @Override
    public String getItemDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%s,%.2f,%d,%s", getClass().getSimpleName(), getItemId(), getName(),
                getCategory(), getPrice(), getQuantity(), getColor());
        return csvString;
    }

    public static ElectronicsItem createForWriting(String name, String category, double price, int quantity, String color, String itemId) {
        ElectronicsItem forWriting= new ElectronicsItem(name, category, price,quantity, color, itemId);
        return forWriting;
    }

    public static boolean doesItemExist(String name, String category, double price,String color) {
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item.getName().equals(name) && item.getCategory().equals(category) && item.getPrice() == price &&
                    ((ElectronicsItem) item).getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }

    public static ElectronicsItem getItemByNameCategoryPriceColor(String name, String category, double price, String color) {
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item.getName().equals(name) && item.getCategory().equals(category) && item.getPrice() == price &&
                    ((ElectronicsItem) item).getColor().equals(color)) {
                return (ElectronicsItem) item;
            }
        }
        return null; // If the object is not found, null is returned
    }

    private void newDelivery(String name, String category, double price, int quantity, String color) {
        Scanner sc = new Scanner(System.in);
        getItemByNameCategoryPriceColor(name, category, price, color).setColor(color);
        boolean checkQuantity = false;
        int existingQuantity = InventoryItem.getItemByNameCategoryPrice(name, category, price).getQuantity();
        System.out.printf("Item %s, category %s, price %.2f already exists%n", name, category, price);
        System.out.println("Current quantity: " + existingQuantity);

        while (!checkQuantity) {
            try {
                System.out.println("Enter quantity to add:");
                int newQuantity = Integer.parseInt(sc.nextLine().trim());
                if (newQuantity >= 0) {
                    InventoryItem.getItemByNameCategoryPrice(name, category, price).setQuantity(newQuantity + existingQuantity);
                    checkQuantity = true;
                } else {
                    System.out.println("Quantity must be a non-negative number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong data. Please input the quantity to add: ");
            }
        }
    }
}
