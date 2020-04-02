package ninja.amp.ampmenus.items;

import ninja.amp.ampmenus.Materials;
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
                new ItemStack(Materials.GREEN_WOOL),
                new ItemStack(Materials.RED_WOOL),
                lore
        );
    }

    public BooleanItem(String displayName, ItemStack trueIcon, ItemStack falseIcon, String... lore) {
        super(displayName, Materials.UNKNOWN_ITEM, lore);
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
