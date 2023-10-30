import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public static void saveDataToFile(String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (InventoryItem item : InventoryManager.getInventoryData().values()) {
                writer.write(item.getItemDataInCSVFormat());
                writer.newLine();
            }
            if (!SpoiledManager.getSpoiledItems().isEmpty()){
                for (AbstractItem brokenItem : SpoiledManager.getSpoiledItems()) {
                    writer.write(brokenItem.getItemDataInCSVFormat());
                    writer.newLine();
                }
            }

            for (User user : UserManager.getUsers()) {
                writer.write(user.getUserDataInCSVFormat());
                writer.newLine();
            }

            for (Order order : OrderManager.getOrders()) {
                writer.write(order.getOrderDataInCSVFormat());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method for delete all data in data file
    public static void clearData(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
