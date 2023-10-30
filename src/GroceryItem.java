import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GroceryItem extends InventoryItem {
    private LocalDate expireDate;

    public GroceryItem(String name, String category, double price, int quantity) {
        super(name, category, price, quantity);
        if (!InventoryManager.doesItemExist(name, category, price)) {
            // object with these parameters does not exist, add
            this.expireDate = expireDateInput();
            InventoryManager.addInventoryItem(this);
        } else {
            // object with these parameters already exists, ask user to add the quantity
            newDelivery(name, category, price, quantity);
        }
    }

    private GroceryItem(String name, String category, double price, int quantity, String expireDateString, String itemId) {
        super(name, category, price, quantity, itemId);
        setExpireDate(expireDateString);
        if (!InventoryManager.doesItemExist(name, category, price)) {
            InventoryManager.addInventoryItem(this);
        }
    }

    public String getExpireDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String expireDateString = expireDate.format(formatter);
        return expireDateString;
    }

    public void setExpireDate(String expireDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            expireDate = LocalDate.parse(expireDateString, formatter);
        } catch (Exception e) {
            System.out.println("Wrong date format.");
        }
        this.expireDate = expireDate;
    }

    @Override
    public void getDetails() {
        super.getDetails();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.printf("Expiry date: %-10s%n", getExpireDate());
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
                        if (!SpoiledManager.doesBrokenItemExist(getItemId())) {
                            SpoiledManager.addSpoiledItem(brokenItem);
                        } else {
                            int currentBrokenQuantity = BrokenItem.getBrokenItem(getItemId()).getQuantity();
                            BrokenItem.getBrokenItem(getItemId()).setQuantity(currentBrokenQuantity + brokenQuantity);
                        }
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
    public boolean isPerishable() {
        return true;
    }

    @Override
    public void handleExpiration() {
        if (getQuantity() > 0) {
            Scanner sc = new Scanner(System.in);
            int expiredQuantity = -1;
            while (expiredQuantity < 0) {
                try {
                    System.out.printf("Enter the quantity of expired %s:%n", getName());
                    expiredQuantity = Integer.parseInt(sc.nextLine().trim());
                    if (expiredQuantity <= getQuantity()) {
                        System.out.printf("Expired items, quantity: %d%n", expiredQuantity);
                        setQuantity(getQuantity() - expiredQuantity);
                        System.out.println("New quantity is: " + getQuantity());
                        String expireDateSpoiled = null;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                        while (expireDateSpoiled == null) {
                            System.out.print("Input expiry date for spoiled item (dd.MM.yyyy): ");
                            String input = sc.nextLine().trim();

                            try {
                                expireDateSpoiled = LocalDate.parse(input, formatter).toString();
                            } catch (java.time.format.DateTimeParseException e) {
                                System.out.println("Wrong input format. Please try again.");
                            }
                        }
                        SpoiledItem spoiledItem = new SpoiledItem(getItemId(), getName(), getCategory(), getPrice(),
                                expiredQuantity, expireDateSpoiled);
                        if (!SpoiledManager.doesExpiredItemExist(getItemId(), expireDateSpoiled)) {
                            SpoiledManager.addSpoiledItem(spoiledItem);
                        } else {
                            int currentExpiredQuantity = SpoiledItem.getSpoiledItem(getItemId()).getQuantity();
                            SpoiledItem.getSpoiledItem(getItemId()).setQuantity(currentExpiredQuantity + expiredQuantity);
                        }
                    } else {
                        System.out.printf("We have %d items on stock. Please try again%n", getQuantity());
                        expiredQuantity = -1;
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
        if (isExpired()) {
            return null;
        } else if ((expireDate.compareTo(LocalDate.now())) < 3) {
            return getPrice() * 0.5;
        } else if ((expireDate.compareTo(LocalDate.now())) < 6) {
            return getPrice() * 0.7;
        }
        return getPrice();
    }

    @Override
    public boolean isSellable() {
        if (getQuantity() <= 0 || isExpired()) {
            return false;
        }
        return true;
    }

    public boolean isExpired() {
        if ((expireDate.compareTo(LocalDate.now())) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getItemDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%s,%.2f,%d,%s", getClass().getSimpleName(), getItemId(), getName(),
                getCategory(), getPrice(), getQuantity(), getExpireDate());
        return csvString;
    }


    public LocalDate expireDateInput() {
        // asking for required expiry date
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        while (expireDate == null) {
            System.out.printf("Input expiry date for %s (dd.MM.yyyy): %n", getName());
            String expiryDateString = sc.nextLine().trim();
            try {
                expireDate = LocalDate.parse(expiryDateString, formatter);
            } catch (Exception e) {
                System.out.println("Wrong date format.");
            }
        }
        return expireDate;
    }

    public static GroceryItem getItemByNameCategoryPrice(String name, String category, double price) {
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item.getName().equals(name) && item.getCategory().equals(category) && item.getPrice() == price) {
                return (GroceryItem) item;
            }
        }
        return null; // If the object is not found, null is returned
    }

    private void newDelivery(String name, String category, double price, int quantity) {
        Scanner sc = new Scanner(System.in);
        setExpireDate(GroceryItem.getItemByNameCategoryPrice(name, category, price).getExpireDate());
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

    public static GroceryItem createForWriting(String name, String category, double price, int quantity, String expireDateString, String itemId) {
        GroceryItem forWriting= new GroceryItem(name, category, price,quantity, expireDateString, itemId);
        return forWriting;
    }
}
