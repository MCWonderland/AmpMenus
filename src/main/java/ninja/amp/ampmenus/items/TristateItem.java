package ninja.amp.ampmenus.items;

import ninja.amp.ampmenus.Materials;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link MenuItem} that will dynamically change it's {@link #getFinalIcon(Player)} output depending on
 * {@link #getValue(Player)}.
 */
public abstract class TristateItem extends MenuItem {

    private final ItemStack stateOneIcon;
    private final ItemStack stateTwoIcon;
    private final ItemStack stateThreeIcon;

    public TristateItem(String displayName, String... lore) {
        this(
                displayName,
                new ItemStack(Materials.RED_WOOL),
                new ItemStack(Materials.YELLOW_WOOL),
                new ItemStack(Materials.GREEN_WOOL),
                lore
        );
    }

    public TristateItem(String displayName, ItemStack stateOneIcon, ItemStack stateTwoIcon, ItemStack stateThreeIcon, String... lore) {
        super(displayName, Materials.UNKNOWN_ITEM, lore);
        this.stateOneIcon = stateOneIcon;
        this.stateTwoIcon = stateTwoIcon;
        this.stateThreeIcon = stateThreeIcon;
    }

    public abstract Tristate getValue(Player player);

    @Override
    public ItemStack getFinalIcon(Player player) {
        Tristate value = getValue(player);
        return value.value == 1
                ? stateOneIcon
                : value.value == 2
                        ? stateTwoIcon
                        : stateThreeIcon;
    }

    public ItemStack getStateOneIcon() {
        return stateOneIcon;
    }
    public ItemStack getStateTwoIcon() {
        return stateTwoIcon;
    }
    public ItemStack getStateThreeIcon() {
        return stateThreeIcon;
    }

    public enum Tristate {

        ONE(1),
        TWO(2),
        THREE(3),

        LOW(1),
        MEDIUM(2),
        HIGH(3),

        RED(1),
        YELLOW(2),
        GREEN(3),

        OFF(1),
        SLOW(2),
        FAST(3),

        NO(1),
        MAYBE(2),
        YES(3);

        private final int value;

        Tristate(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

}
