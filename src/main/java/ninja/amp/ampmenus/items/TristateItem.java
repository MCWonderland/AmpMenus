/*
 * This file is part of AmpMenus.
 *
 * Copyright (c) 2014-2021 <https://github.com/Scarsz/AmpMenus/>
 *
 * AmpMenus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpMenus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpMenus.  If not, see <http://www.gnu.org/licenses/>.
 */
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
        return stateOneIcon.clone();
    }
    public ItemStack getStateTwoIcon() {
        return stateTwoIcon.clone();
    }
    public ItemStack getStateThreeIcon() {
        return stateThreeIcon.clone();
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

        public Tristate fromValue(int value) {
            if (value < 1 || value > 3) throw new IllegalArgumentException("Value must be between 1-3");
            return value == 1 ? Tristate.ONE : value == 2 ? Tristate.TWO : Tristate.THREE;
        }

        public int getValue() {
            return value;
        }

    }

}
