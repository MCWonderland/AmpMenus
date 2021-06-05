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

import java.util.List;

/**
 * A {@link MenuItem} that will dynamically change it's {@link #getFinalIcon(Player)} output depending on
 * {@link #getValue(Player)}.
 */
public abstract class BooleanItem extends MenuItem {

    private final ItemStack trueIcon;
    private final ItemStack falseIcon;

    public BooleanItem(String displayName, String... lore) {
        this(displayName, new ItemStack(Materials.GREEN_WOOL), new ItemStack(Materials.RED_WOOL), lore);
    }

    public BooleanItem(String displayName, ItemStack trueIcon, ItemStack falseIcon, String... lore) {
        super(displayName, Materials.UNKNOWN_ITEM, lore);
        this.trueIcon = trueIcon;
        this.falseIcon = falseIcon;
    }

    public abstract boolean getValue(Player player);

    @Override
    public ItemStack getFinalIcon(Player player) {
        boolean value = getValue(player);
        return setNameAndLore(
                value ? getTrueIcon() : getFalseIcon(),
                getDisplayName(value),
                getLore(value)
        );
    }

    /**
     * Functionally identical to {@link MenuItem#getDisplayName()} if not overridden. The value passed should be used to
     * determine the display name for this MenuItem.
     * @param value the value to get the display name for
     * @return the display name of the given value
     */
    public String getDisplayName(boolean value) {
        return getDisplayName();
    }

    /**
     * Functionally identical to {@link MenuItem#getLore()} if not overridden. The value passed should be used to
     * determine the lore for this MenuItem.
     * @param value the value to get the lore for
     * @return the lore of the given value
     */
    public List<String> getLore(boolean value) {
        return getLore();
    }

    public ItemStack getTrueIcon() {
        return trueIcon;
    }
    public ItemStack getFalseIcon() {
        return falseIcon;
    }

}
