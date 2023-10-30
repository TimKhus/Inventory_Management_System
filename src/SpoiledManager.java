import java.util.ArrayList;
import java.util.List;

public class SpoiledManager {

    private static List<AbstractItem> spoiledItems = new ArrayList<>();

    public static List<AbstractItem> getSpoiledItems() {
        return spoiledItems;
    }

    public static void addSpoiledItem(AbstractItem item) {
        spoiledItems.add(item);
    }

    public static boolean doesBrokenItemExist(String itemId) {
        for (AbstractItem item : spoiledItems) {
            if (item instanceof BrokenItem && ((BrokenItem) item).getItemId().equals(itemId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean doesExpiredItemExist(String itemId, String expiredDate) {
        for (AbstractItem item : spoiledItems) {
            if (item instanceof SpoiledItem && ((SpoiledItem) item).getItemId().equals(itemId) && ((SpoiledItem) item).getExpiredDate().equals(expiredDate)) {
                return true;
            }
        }
        return false;
    }

    public static double totalLosses() {
        double totalLosses = 0;

        for (AbstractItem item : spoiledItems) {
            if (item instanceof BrokenItem) {
                totalLosses += ((BrokenItem) item).calculateValue();
            } else if (item instanceof SpoiledItem) {
                totalLosses += ((SpoiledItem) item).calculateValue();
            }
        }
        return totalLosses;
    }

    public static void  showTotalLosses() {
        System.out.println("Broken Items:");
        for (AbstractItem item : spoiledItems) {
            if (item instanceof BrokenItem) {
                ((BrokenItem) item).getDetails();
            }
        }
        System.out.println();
        System.out.println("Expired Items:");
        for (AbstractItem item : spoiledItems) {
            if (item instanceof SpoiledItem) {
                ((SpoiledItem) item).getDetails();
            }
        }
        System.out.println();
    }
}
