import java.util.Scanner;

public class FragileItem extends InventoryItem {
    private double weight;
    private String color;
    private boolean addedToInventory;
    private boolean extraPacked = false;

    public FragileItem(String name, String category, double price, int quantity, double weight, String color) {
        super(name, category, price, quantity);
        if (!doesItemExist(name, category, price, color)) {
            // object with these parameters does not exist, add
            this.weight = weight;
            this.color = color;
            InventoryManager.addInventoryItem(this);
        } else {
            // object with these parameters already exists, ask user to add the quantity
            newDelivery(name, category, price, quantity, color);
        }
    }

    private FragileItem(String name, String category, double price, int quantity, double weight, String color, String itemId) {
        super(name, category, price, quantity, itemId);
        this.weight = weight;
        this.color = color;
        if (!doesItemExist(name, category, price, color)) {
            InventoryManager.addInventoryItem(this);
        }
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public boolean isExtraPacked() {
        return extraPacked;
    }

    public void setExtraPacked(boolean extraPacked) {
        this.extraPacked = extraPacked;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public void getDetails() {
        super.getDetails();
        System.out.printf("Weight, kg: %-10.2f ", getWeight());
        System.out.printf("Color: %-10s%n", getColor());
    }

    @Override
    public void displayDescription() {
        super.displayDescription();
        System.out.printf("Weight, kg: %s%n", getWeight());
        System.out.printf("Color: %s%n", getColor());
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
        if (extraPacked) {
            return getPrice() + getWeight() * 0.2;
        }
        return getPrice();
    }

    @Override
    public String getItemDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%s,%.2f,%d,%.2f,%s", getClass().getSimpleName(), getItemId(), getName(),
                getCategory(), getPrice(), getQuantity(), getWeight(), getColor());
        return csvString;
    }

    public static FragileItem createForWriting(String name, String category, double price, int quantity, double weight, String color, String itemId) {
        FragileItem forWriting = new FragileItem(name, category, price, quantity, weight, color, itemId);
        return forWriting;
    }

    public static boolean doesItemExist(String name, String category, double price,String color) {
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item.getName().equals(name) && item.getCategory().equals(category) && item.getPrice() == price &&
                    ((FragileItem) item).getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }

    public static FragileItem getItemByNameCategoryPriceColor(String name, String category, double price, String color) {
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item.getName().equals(name) && item.getCategory().equals(category) && item.getPrice() == price &&
                    ((FragileItem) item).getColor().equals(color)) {
                return (FragileItem) item;
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
