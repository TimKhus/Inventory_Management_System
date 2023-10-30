import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private static Map<String, InventoryItem> inventoryData;

    public InventoryManager() {
        inventoryData = new HashMap<>();
    }

    public static Map<String, InventoryItem> getInventoryData() {
        return inventoryData;
    }

    public static void addInventoryItem(InventoryItem item) {
        String itemID = item.getItemId();
        if (!inventoryData.containsKey(itemID)) {
            inventoryData.put(itemID, item);
        } else {
            System.out.println("Item with this ID already exists");
        }
    }

    public static void deleteInventoryItem(String itemId) {
        inventoryData.remove(itemId);
    }

    public static InventoryItem getInventoryItem(String itemID){
        return inventoryData.get(itemID);
    }

    public static boolean doesItemExist(String name, String category, double price) {
        for (InventoryItem item : inventoryData.values()) {
            if (item.getName().equals(name) && item.getCategory().equals(category) && item.getPrice() == price) {
                return true;
            }
        }
        return false;
    }

}
