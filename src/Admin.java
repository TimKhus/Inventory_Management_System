import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Admin {
    private String password;
    private Scanner sc;

    public Admin(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    private static Admin admin = new Admin("admin");

    public void startAdminMode() {
        Scanner sc = new Scanner(System.in);
        boolean adminMode = false;
        System.out.println("Enter 'admin' to access admin mode: ");
        String input = sc.nextLine().trim();

        if (input.equals("admin")) {
            System.out.println("Enter the admin password:");
            String adminPassword = sc.nextLine();
            if (adminPassword.equals(admin.getPassword())) {
                adminMode = true;
                System.out.println("Admin mode activated");
            } else {
                System.out.println("Invalid admin password. Continue as a regular user");
                System.out.println();
            }
        } else {
            System.out.println("Wrong admin name. Please try again");
            System.out.println();
        }

        if (adminMode) {
            System.out.println("Welcome to admin mode");

            boolean adminModeActive = true;

            while (adminModeActive) {
                System.out.println("1 - Add item");
                System.out.println("2 - Remove item");
                System.out.println("3 - Display all products");
                System.out.println("4 - Display all products by category");
                System.out.println("5 - Search item by ID");
                System.out.println("6 - Search item by name");
                System.out.println("7 - Work with broken and expired items");
                System.out.println("8 - Display orders");
                System.out.println("9 - Display users");
                System.out.println("0 - Exit");
                System.out.println("Select an option: ");

                String commandString = sc.nextLine().trim();

                if (Character.isDigit(commandString.toCharArray()[0]) && commandString.length() == 1) {
                    switch (Integer.parseInt(commandString)) {
                        case 1 -> {
                            // Add item
                            addItemToInventory();
                        }
                        case 2 -> {
                            // Remove inventory item
                            removeInventoryItem();
                        }
                        case 3 -> {
                            // Show all products in database
                            showAllProducts();
                        }
                        case 4 -> {
                            // show all products by class
                            showAllProductsByClass();    // print all classes in beginning
                        }
                        case 5 -> {
                            // search items by ID
                            searchItemById();
                        }
                        case 6 -> {
                            // search items by name
                            searchItemByName(); //return all items with details in name
                        }
                        case 7 -> {
                            // work with expired and broken items
                            handleExpiredAndBrokenItems();
                        }
                        case 8 -> {
                            // display all orders
                            displayOrders();
                        }
                        case 9 -> {
                            // display all users
                            displayUsers();
                        }
                        case 0 -> {
                            // Exit
                            adminModeActive = false;
                            System.out.println("You exit admin mode");
                            System.out.println();
                        }
                        default -> {
                            System.out.println("Wrong command");
                            System.out.println();
                        }
                    }
                } else {
                    System.out.println("Wrong command, please try again");
                    System.out.println();
                }
            }
        }
    }

    private void displayUsers() {
        for (User user : UserManager.getUsers()) {
            System.out.println(user);
        }
        System.out.println();
    }

    private void displayOrders() {
        for (Order order : OrderManager.getOrders()) {
            order.display();
        }
        System.out.println();
    }

    private void handleExpiredAndBrokenItems() {
        // Work with expired and broken items
        Scanner sc = new Scanner(System.in);
        boolean expiredAndBrokenMenuActive = true;

        while (expiredAndBrokenMenuActive) {
            System.out.println("1 - Handle broken items");
            System.out.println("2 - Handle expired items");
            System.out.println("3 - Show total losses");
            System.out.println("0 - Back to the main admin menu");
            System.out.println("Select an option: ");

            String subCommandString = sc.nextLine().trim();

            if (Character.isDigit(subCommandString.toCharArray()[0]) && subCommandString.length() == 1) {
                switch (Integer.parseInt(subCommandString)) {
                    case 1 -> {
                        // Handle broken items
                        handleBrokenItems();
                    }
                    case 2 -> {
                        // Handle expired items
                        handleExpiredItems();
                    }
                    case 3 -> {
                        // Show total losses
                        SpoiledManager.showTotalLosses();
                        System.out.print("Total amount: ");
                        System.out.println(SpoiledManager.totalLosses());
                        System.out.println();
                    }
                    case 0 -> {
                        // Exit the sub-menu
                        expiredAndBrokenMenuActive = false;
                    }
                    default -> {
                        System.out.println("Wrong command");
                    }
                }
            } else {
                System.out.println("Wrong command, please try again");
            }
        }
    }

    private void handleExpiredItems() {
        Scanner sc = new Scanner(System.in);
        boolean spoiledMenuActive = true;

        while (spoiledMenuActive) {
            System.out.println("1 - Handle expired item by its ID");
            System.out.println("2 - Search item by name");
            System.out.println("0 - Back to the main admin menu");
            System.out.println("Select an option: ");

            String commandString = sc.nextLine().trim();

            if (Character.isDigit(commandString.toCharArray()[0]) && commandString.length() == 1) {
                switch (Integer.parseInt(commandString)) {
                    case 1 -> {
                        System.out.println("Input item ID to find it in database:");
                        while (true) {
                            String itemId = sc.nextLine().trim().toLowerCase();
                            if (itemId.equals("exit")) {
                                break;
                            }
                            if (InventoryManager.getInventoryData().containsKey(itemId)) {
                                InventoryManager.getInventoryItem(itemId).getDetails();
                                InventoryManager.getInventoryItem(itemId).handleExpiration();
                                break;
                            } else {
                                System.out.println("No item in database with this ID");
                                System.out.println("Please item ID to find it in database or input 'exit' to exit:");
                            }
                        }
                    }
                    case 2 -> {
                        searchItemByName();
                    }
                    case 0 -> {
                        // Exit the sub-menu
                        spoiledMenuActive = false;
                    }
                    default -> {
                        System.out.println("Wrong command");
                    }
                }
            } else {
                System.out.println("Wrong command, please try again");
            }
        }

    }

    private void handleBrokenItems() {
        Scanner sc = new Scanner(System.in);
        boolean BrokenMenuActive = true;

        while (BrokenMenuActive) {
            System.out.println("1 - Handle broken item by its ID");
            System.out.println("2 - Search item by name");
            System.out.println("0 - Back to the main admin menu");
            System.out.println("Select an option: ");

            String commandString = sc.nextLine().trim();

            if (Character.isDigit(commandString.toCharArray()[0]) && commandString.length() == 1) {
                switch (Integer.parseInt(commandString)) {
                    case 1 -> {
                        System.out.println("Input item ID to find it in database:");
                        while (true) {
                            String itemId = sc.nextLine().trim().toLowerCase();
                            if (itemId.equals("exit")) {
                                break;
                            }
                            if (InventoryManager.getInventoryData().containsKey(itemId)) {
                                InventoryManager.getInventoryItem(itemId).getDetails();
                                InventoryManager.getInventoryItem(itemId).handleBreak();
                                break;
                            } else {
                                System.out.println("No item in database with this ID");
                                System.out.println("Please item ID to find it in database or input 'exit' to exit:");
                            }
                        }
                    }
                    case 2 -> {
                        searchItemByName();
                    }
                    case 0 -> {
                        // Exit the sub-menu
                        BrokenMenuActive = false;
                    }
                    default -> {
                        System.out.println("Wrong command");
                    }
                }
            } else {
                System.out.println("Wrong command, please try again");
            }
        }
    }

    private void searchItemByName() {
        Scanner sc = new Scanner(System.in);
        int counter = 0;
        System.out.println("Input item name to find it in database:");
        String itemName = sc.nextLine().trim().toLowerCase();
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item.getName().toLowerCase().contains(itemName)) {
                counter++;
                item.getDetails();
            }
        }
        if (counter == 0) {
            System.out.println("No such item in the database");
        }

    }

    private void searchItemById() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input item ID to find it in database:");
        while (true) {
            String itemId = sc.nextLine().trim().toLowerCase();
            if (itemId.equals("exit")) {
                break;
            }
            if (InventoryManager.getInventoryData().containsKey(itemId)) {
                InventoryManager.getInventoryItem(itemId).getDetails();
                System.out.println();
                break;
            } else {
                System.out.println("No item in database with this ID");
                System.out.println("Please item ID to find it in database or input 'exit' to exit:");
            }
        }
    }

    private void showAllProductsByClass() {
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

    private void showAllProducts() {
        System.out.println("Electronic Items:");
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item instanceof ElectronicsItem) {
                item.getDetails();
            }
        }
        System.out.println();
        System.out.println("Fragile Items:");
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item instanceof FragileItem) {
                item.getDetails();
            }
        }
        System.out.println();
        System.out.println("Grocery Items:");
        for (InventoryItem item : InventoryManager.getInventoryData().values()) {
            if (item instanceof GroceryItem) {
                item.getDetails();
            }
        }
        System.out.println();
    }

    public void addItemToInventory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the item type:");
        System.out.println("1 - Grocery Item");
        System.out.println("2 - Fragile Item");
        System.out.println("3 - Electronics Item");
        String input = sc.nextLine().trim();

        if (Character.isDigit(input.toCharArray()[0]) && input.length() == 1) {
            int itemType = Integer.parseInt(input);

            switch (itemType) {
                case 1 -> addGroceryItemToInventory();
                case 2 -> addFragileItemToInventory();
                case 3 -> addElectronicsItemToInventory();
                default -> System.out.println("Wrong command, please try again");
            }
        } else {
            System.out.println("Wrong data type");
            System.out.println();
        }
    }

    private void addGroceryItemToInventory() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Input name for item:");
            String gName = sc.nextLine().trim();
            System.out.println("Input category for item:");
            String gCategory = sc.nextLine().trim();
            System.out.println("Input price for item, digits only:");
            String gPriceString = sc.nextLine().trim();
            System.out.println("Input quantity of items, digits only:");
            String gQuantityString = sc.nextLine().trim();
            if (gName.matches(".*[a-zA-Z].*") && gCategory.matches(".*[a-zA-Z].*")) {
                try {
                    double gPrice = Double.parseDouble(gPriceString);
                    int gQuantity = Integer.parseInt(gQuantityString);
                    GroceryItem groceryItem = new GroceryItem(gName, gCategory, gPrice, gQuantity);
                    System.out.printf("%s was successfully added to database%n", gName);
                    groceryItem.getDetails();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong data type for Price or Quantity. Please try again.");
                }
            } else {
                System.out.println("Wrong name or Category. Please try again.");
            }
        }
    }

    private void addFragileItemToInventory() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Input name for item:");
            String fName = sc.nextLine().trim();
            System.out.println("Input category for item:");
            String fCategory = sc.nextLine().trim();
            System.out.println("Input price for item, digits only:");
            String fPriceString = sc.nextLine().trim();
            System.out.println("Input quantity of items, digits only:");
            String fQuantityString = sc.nextLine().trim();
            System.out.println("Input weight of item, digits only:");
            String fWeightString = sc.nextLine().trim();
            System.out.println("Input color of item:");
            String fColor = sc.nextLine().trim();
            if (fName.matches(".*[a-zA-Z].*") && fCategory.matches(".*[a-zA-Z].*") && fColor.matches(".*[a-zA-Z].*")) {
                try {
                    double fPrice = Double.parseDouble(fPriceString);
                    int fQuantity = Integer.parseInt(fQuantityString);
                    double fWeight = Double.parseDouble(fWeightString);
                    FragileItem fragileItem = new FragileItem(fName, fCategory, fPrice, fQuantity, fWeight, fColor);
                    System.out.printf("%s was successfully added to database%n", fName);
                    fragileItem.getDetails();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong data type for Price, Quantity or Weight. Please try again.");
                }
            } else {
                System.out.println("Wrong Name, Category or Color. Please try again.");
            }
        }
    }

    private void addElectronicsItemToInventory() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Input name for item:");
            String eName = sc.nextLine().trim();
            System.out.println("Input category for item:");
            String eCategory = sc.nextLine().trim();
            System.out.println("Input price for item, digits only:");
            String ePriceString = sc.nextLine().trim();
            System.out.println("Input quantity of items, digits only:");
            String eQuantityString = sc.nextLine().trim();
            System.out.println("Input color of item:");
            String eColor = sc.nextLine().trim();
            if (eName.matches(".*[a-zA-Z].*") && eCategory.matches(".*[a-zA-Z].*") && eColor.matches(".*[a-zA-Z].*")) {
                try {
                    double gPrice = Double.parseDouble(ePriceString);
                    int gQuantity = Integer.parseInt(eQuantityString);
                    ElectronicsItem electronicsItem = new ElectronicsItem(eName, eCategory, gPrice, gQuantity, eColor);
                    System.out.printf("%s was successfully added to database%n", eName);
                    electronicsItem.getDetails();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong data type for Price, Quantity or Weight. Please try again.");
                }
            } else {
                System.out.println("Wrong Name, Category or Color. Please try again.");
            }
        }
    }

    public void removeInventoryItem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input item ID to delete it from database:");
        while (true) {
            String itemId = sc.nextLine().trim().toLowerCase();
            if (itemId.equals("exit")) {
                break;
            }
            if (InventoryManager.getInventoryData().containsKey(itemId)) {
                String itemName = InventoryManager.getInventoryItem(itemId).getName();
                InventoryManager.deleteInventoryItem(itemId);
                System.out.printf("%s ID %s was successfully deleted from the database%n", itemName, itemId);
                break;
            } else {
                System.out.println("No item in database with this ID");
                System.out.println("Please input ID to delete item or input 'exit' to exit:");
            }
        }
    }
}
