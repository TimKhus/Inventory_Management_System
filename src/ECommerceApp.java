import java.util.Scanner;

public class ECommerceApp {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        Admin admin = new Admin("admin");
        OrderManager orderManager = new OrderManager();
        Reader.readDataFromFile("data.csv");


        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        boolean loggedIn = false; // so only registered user can use our system
        String loginEmail = null;

        while (!exit) {
            System.out.println("1 - Log in / register a new user");
            System.out.println("2 - View all products");
            System.out.println("3 - View all products by category");
            System.out.println("4 - Create order");
            System.out.println("5 - View all my orders");
            System.out.println("6 - Log out");
            System.out.println("7 - Exit");
            System.out.println("0 - Enter admin mode");
            System.out.println("Select an option: ");

            String choice = sc.nextLine().trim();

            if (Character.isDigit(choice.toCharArray()[0]) && choice.length() == 1) {
                switch (Integer.parseInt(choice)) {
                    // user registration or logging in
                    case 1 -> {
                        if (loggedIn) {
                            System.out.println("You already logged in");
                            System.out.println();
                            break;
                        }
                        System.out.println("1 - Register a new user");
                        System.out.println("2 - Log in with your email and password");
                        String regReply = sc.nextLine().trim();
                        if (Character.isDigit(regReply.toCharArray()[0]) && regReply.length() == 1) {
                            switch (Integer.parseInt(regReply)) {
                                // users registration
                                case 1 -> {
                                    System.out.println("Input your first name : ");
                                    String newFirstName = sc.nextLine().trim().toUpperCase();
                                    System.out.println("Input your last name: ");
                                    String newLastName = sc.nextLine().trim().toUpperCase();
                                    System.out.println("Input your email: ");
                                    String newEmail = sc.nextLine().trim().toLowerCase();
                                    // checking email is real
                                    if (!UserManager.isEmailValid(newEmail)) {
                                        System.out.println("Email does not exist. Please try again");
                                        System.out.println();
                                        break;
                                    }
                                    // if email already registered we ask user to log in with it
                                    if (UserManager.emailRegistered(newEmail)) {
                                        System.out.println("This email already registered. Please log in");
                                        System.out.println();
                                        break;
                                    }
                                    System.out.println("Input your password, without spaces: ");
                                    String newPassword = sc.nextLine().trim();
                                    // checking we have all required data
                                    if (UserManager.registerUser(newFirstName, newLastName, newEmail, newPassword)) {
                                        System.out.printf("New user %s registered%n", newEmail);
                                        System.out.printf("Hi %s! You are logged in using email %s%n", newFirstName, newEmail);
                                        System.out.println();
                                        loggedIn = true;
                                        loginEmail = newEmail;
                                    } else {
                                        System.out.println("Please fill in all required fields to register.");
                                        System.out.println();
                                    }
                                }
                                // log in if user already registered
                                case 2 -> {
                                    User user = null;
                                    System.out.println("Input your email: ");
                                    String email = sc.nextLine().trim().toLowerCase();
                                    // checking email is real
                                    if (!UserManager.isEmailValid(email)) {
                                        System.out.println("Email does not exist. Please try again");
                                        System.out.println();
                                        break;
                                    }
                                    //checking users email is in our base
                                    if (UserManager.emailRegistered(email)) {
                                        user = UserManager.findUser(email);
                                    } else {
                                        System.out.println("User with this email does not exist");
                                        System.out.println();
                                        break;
                                    }
                                    System.out.println("Input your password: ");
                                    String password = sc.nextLine().trim();
                                    assert user != null;
                                    if (user.getPassword().equals(password)) {
                                        System.out.printf("Hi %s! You are logged in using email %s%n", user.getFirstName(), email);
                                        System.out.println();
                                        loggedIn = true;
                                        loginEmail = email;
                                        break;
                                    } else {
                                        System.out.println("Wrong password! Please try again");
                                    }
                                    System.out.println();
                                }
                                default -> {
                                    System.out.println("Wrong command, please try again");
                                    System.out.println();
                                }
                            }
                        }
                    }
                    case 2 -> {
                        if (loggedIn) {
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
                        } else {
                            System.out.println("Please login or register to view room types and work with reservations");
                            System.out.println();
                        }
                    }
                    case 3 -> {
                        if (loggedIn) {
                            orderManager.showProductsByCategory();
                        }

                    }
                    case 4 -> {
                        if (loggedIn) {
                            ShoppingCart cart = new ShoppingCart(loginEmail);
                            boolean ordering = true;
                            while (ordering) {
                                System.out.println("1 - Add a product to your cart");
                                System.out.println("2 - Search for a product by name");
                                System.out.println("3 - View products by category");
                                System.out.println("4 - Display my cart");
                                System.out.println("5 - Remove a product from your cart");
                                System.out.println("Enter 'done' to finish your order.");
                                String input = sc.nextLine().trim();

                                if (input.equalsIgnoreCase("done")) {
                                    if (cart.getCart().size() > 0) {
                                        Payment payment = new Payment();
                                        if (PaymentProcessor.processPayment(cart.calculateCartValue(), payment)) {
                                            orderManager.createOrder(cart, cart.calculateCartValue(), loginEmail, payment);
                                        } else {
                                            System.out.println("Payment cancelled");
                                        }
                                    }
                                    ordering = false;
                                } else if (input.equals("1")) {
                                    // Adding product to cart by ID
                                    System.out.println("Select a product to add to your cart:");
                                    System.out.println("Enter the product ID to add it to your cart or 'done' to finish your order:");
                                    String itemIdInput = sc.nextLine().trim();

                                    if (InventoryManager.getInventoryItem(itemIdInput) != null) {
                                        cart.addToCart(itemIdInput);
                                        System.out.printf("%s added to your cart.%n", InventoryManager.getInventoryItem(itemIdInput).getName());
                                        System.out.println();
                                    } else {
                                        System.out.println("Product not found. Please enter a valid product ID.");
                                        System.out.println();
                                    }
                                } else if (input.equals("2")) {
                                    // Search by name
                                    System.out.print("Enter the name of the product to search: ");
                                    String searchName = sc.nextLine().trim();

                                    // Go through all products and find when name contains the specified value
                                    for (InventoryItem product : InventoryManager.getInventoryData().values()) {
                                        if (product.getName().toLowerCase().contains(searchName.toLowerCase())) {
                                            product.getDetails();
                                        }
                                    }
                                } else if (input.equals("3")) {
                                    // Show products by category
                                    orderManager.showProductsByCategory();
                                } else if (input.equals("4")) {
                                    cart.showCart();
                                } else if (input.equals("5")) {
                                    // Deleting product from cart
                                    System.out.println("Enter the product ID to remove it from your cart: ");
                                    String itemIdToRemove = sc.nextLine().trim();

                                    // Checking if product is in the cart
                                    if (cart.isItemInCart(itemIdToRemove)) {
                                        cart.removeFromCart(itemIdToRemove);
                                        System.out.printf("%s removed from your cart%n", InventoryManager.getInventoryItem(itemIdToRemove).getName());
                                    } else {
                                        System.out.println("Product not found in your cart. Please enter a valid product ID.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please choose a valid option.");
                                    System.out.println();
                                }
                            }
                        } else {
                            System.out.println("Please log in or register to create an order.");
                        }
                        System.out.println();
                    }
                    case 5 -> {
                        for (Order order : OrderManager.getOrders()) {
                            if (order.getUserEmail().equals(loginEmail)) {
                                System.out.println(order);
                            }
                        }
                    }
                    case 6 -> {
                        if (loggedIn) {
                            System.out.printf("Goodbye %s, we look forward to see you again%n", UserManager.findUser(loginEmail));
                            System.out.println();
                            loginEmail = null;
                            loggedIn = false;
                        } else {
                            System.out.println("Please login or register to view room types and work with reservations");
                            System.out.println();
                        }
                    }
                    case 7 -> {
                        exit = true;
                        Writer.clearData("data.csv");
                        Writer.saveDataToFile("data.csv");
                    }
                    case 0 -> {
                        // if we go to admin mode from user registered mode this helps to log out anyway
                        if (loggedIn) {
                            loginEmail = null;
                            loggedIn = false;
                            System.out.println("Goodbye, we look forward to see you again");
                        }
                        admin.startAdminMode();
                    }
                    default -> {
                        System.out.println("Wrong command, please try again");
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