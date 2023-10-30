import ItemsInterfaces.*;

public abstract class AbstractItem implements Item, Categorizable, Breakable, Perishable, Sellable {
    private final String name;
    private String category;
    private double price;

    public AbstractItem(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public void getDetails() {
        // method for getting item details
        System.out.printf("Item: %-15s Category: %-15s Price: %-10.2f", name, category, price);
    }

    @Override
    public Double calculateValue() {
        // method for calculating items price
        return price;
    }

    @Override
    public void displayDescription() {
        // method for getting item description
        System.out.printf("Item: %s%nCategory: %s%n", name, category);
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean isBreakable() {
        // could the item be broken
        return false;
    }

    @Override
    public void handleBreak() {
        // handling the breakage
        System.out.println("The item cannot not  be broken");
    }

    @Override
    public boolean isPerishable() {
        // could the item expire
        return false;
    }

    @Override
    public void handleExpiration() {
        // expiration handling
        System.out.println("The item cannot be expired");
    }

    @Override
    public boolean isSellable() {
        return true;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%.2f", getClass().getSimpleName(), getName(),
                getCategory(), getPrice());
        return csvString;
    }
}
