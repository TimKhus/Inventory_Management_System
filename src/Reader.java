import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public static void readDataFromFile(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                counter++;
                String typeOfClass = data[0];
                switch (typeOfClass) {
                    case "GroceryItem":
                        // GroceryItem data
                        String itemId = data[1];
                        String name = data[2];
                        String category = data[3];
                        double price = Double.parseDouble(data[4]);
                        int quantity = Integer.parseInt(data[5]);
                        String expireDateString = data[6];
                        GroceryItem itemG = GroceryItem.createForWriting(name, category, price,quantity, expireDateString, itemId);
                        break;
                    case "ElectronicsItem":
                        // ElectronicsItem data
                        itemId = data[1];
                        name = data[2];
                        category = data[3];
                        price = Double.parseDouble(data[4]);
                        quantity = Integer.parseInt(data[5]);
                        String color = data[6];
                        ElectronicsItem itemE = ElectronicsItem.createForWriting(name, category, price, quantity, color, itemId);
                        break;
                    case "FragileItem":
                        // FragileItem data
                        itemId = data[1];
                        name = data[2];
                        category = data[3];
                        price = Double.parseDouble(data[4]);
                        quantity = Integer.parseInt(data[5]);
                        double weight = Double.parseDouble(data[6]);
                        color = data[7];
                        FragileItem itemF = FragileItem.createForWriting(name, category, price, quantity, weight, color, itemId);
                        break;
                    case "BrokenItem" :
                        // BrokenItem data
                        itemId = data[1];
                        name = data[2];
                        category = data[3];
                        price = Double.parseDouble(data[4]);
                        quantity = Integer.parseInt(data[5]);
                        BrokenItem itemB = new BrokenItem(itemId, name, category, price, quantity);
                        SpoiledManager.addSpoiledItem(itemB);
                        break;
                    case "SpoiledItem":
                        // SpoiledItem data
                        itemId = data[1];
                        name = data[2];
                        category = data[3];
                        price = Double.parseDouble(data[4]);
                        quantity = Integer.parseInt(data[5]);
                        expireDateString = data[6];
                        SpoiledItem itemS = new SpoiledItem(itemId, name, category, price, quantity, expireDateString);
                        SpoiledManager.addSpoiledItem(itemS);
                        break;
                    case "User":
                        //Users data
                        String firstName = data[1];
                        String lastName = data[2];
                        String email = data[3];
                        String password = data[4];
                        User user = new User(firstName, lastName, email, password);
                        UserManager.getUsers().add(user);
                        break;
                    case "Order":
                        //Order data
                        int orderNumber = Integer.parseInt(data[1]);
                        String userEmail = data[2];
                        double totalCost = Double.parseDouble(data[3]);
                        boolean paymentIsSuccessful = Boolean.parseBoolean(data[4]);
                        int cartLength = Integer.parseInt(data[5]);

                        ShoppingCart cart = new ShoppingCart(userEmail);
                        Payment payment = new Payment();
                        if (paymentIsSuccessful == true) {
                            payment.setSuccessfulPayment();
                        }

                        for (int i = 0; i < cartLength; i++) {
                            String[] cartItemDetails = reader.readLine().split(",");
                            itemId = cartItemDetails[1];
                            name = cartItemDetails[2];
                            category = cartItemDetails[3];
                            price = Double.parseDouble(cartItemDetails[4]);
                            quantity = Integer.parseInt(cartItemDetails[5]);
                            CartItem cartItem = new CartItem(name, category, price, quantity, itemId);
                            cart.getCart().add(cartItem);
                        }
                        Order order = new Order(cart, cart.calculateCartValue(), userEmail, payment);
                        OrderManager.getOrders().add(order);
                        break;
                    default:
                        System.out.printf("The %d line from file was not processed. Please check the database file%n", counter);
                        break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
