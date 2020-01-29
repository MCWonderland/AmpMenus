package ninja.amp.ampmenus.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link MenuItem} that will dynamically change it's {@link #getFinalIcon(Player)} output depending on
 * {@link #getValue(Player)}.
 */
public abstract class BooleanItem extends MenuItem {

    private final ItemStack trueIcon;
    private final ItemStack falseIcon;

    public BooleanItem(String displayName, String... lore) {
        this(
                displayName,
                new ItemStack(Material.GREEN_WOOL),
                new ItemStack(Material.RED_WOOL),
                lore
        );
    }

    public BooleanItem(String displayName, ItemStack trueIcon, ItemStack falseIcon, String... lore) {
        super(displayName, new ItemStack(Material.BARRIER), lore);
        this.trueIcon = trueIcon;
        this.falseIcon = falseIcon;
    }

    public abstract boolean getValue(Player player);

    @Override
    public ItemStack getFinalIcon(Player player) {
        return getValue(player) ? trueIcon : falseIcon;
    }

    public ItemStack getTrueIcon() {
        return trueIcon;
    }

    public ItemStack getFalseIcon() {
        return falseIcon;
    }

}
