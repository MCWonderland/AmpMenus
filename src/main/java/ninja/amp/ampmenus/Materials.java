package ninja.amp.ampmenus;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class Materials {

    public static final ItemStack BACK_ITEM;
    public static final ItemStack EMPTY_ITEM;
    public static final ItemStack UNKNOWN_ITEM;
    public static final ItemStack RED_WOOL;
    public static final ItemStack YELLOW_WOOL;
    public static final ItemStack GREEN_WOOL;
    public static final ItemStack CLOSE_ITEM;

    static {
        BACK_ITEM = (ItemStack) firstNonNull(
                tryGetItem("OAK_FENCE_GATE"),
                tryGetItem("FENCE_GATE")
        );
        EMPTY_ITEM = (ItemStack) firstNonNull(
                tryGetItem("BLACK_STAINED_GLASS_PANE"),
                tryGetItem("STAINED_GLASS_PANE", DyeColor.BLACK.getWoolData())
        );
        UNKNOWN_ITEM = (ItemStack) firstNonNull(
                tryGetItem("BARRIER"),
                tryGetItem("TNT")
        );
        RED_WOOL = (ItemStack) firstNonNull(
                tryGetItem("RED_WOOL"),
                tryGetItem("WOOL", DyeColor.RED.getWoolData())
        );
        YELLOW_WOOL = (ItemStack) firstNonNull(
                tryGetItem("YELLOW_WOOL"),
                tryGetItem("WOOL", DyeColor.YELLOW.getWoolData())
        );
        GREEN_WOOL = (ItemStack) firstNonNull(
                tryGetItem("GREEN_WOOL"),
                tryGetItem("WOOL", DyeColor.GREEN.getWoolData())
        );
        CLOSE_ITEM = (ItemStack) firstNonNull(
                tryGetItem("MUSIC_DISC_CAT"),
                tryGetItem("GOLD_RECORD")
        );
    }

    private static Object firstNonNull(Object... possible) {
        for (Object o : possible) {
            if (o != null) {
                return o;
            }
        }
        return null;
    }

    private static ItemStack tryGetItem(String material) {
        try {
            return new ItemStack(Material.valueOf(material));
        } catch (Exception e) {
            return null;
        }
    }

    private static Object tryGetItem(String material, short data) {
        try {
            return new ItemStack(Material.valueOf(material), 1, data);
        } catch (Exception e) {
            return null;
        }
    }

}
