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

import ninja.amp.ampmenus.events.ItemClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link MenuItem} that will dynamically change it's {@link #getFinalIcon(Player)} output depending on
 * {@link #getValue(Player)}.
 *
 * Clicking this MenuItem will result in {@link #toggleValue(Player)} being called. This is useful for true/false
 * settings per-player, such as seeing other players in a lobby plugin.
 */
public abstract class ToggleableMenuItem extends BooleanMenuItem {

    public ToggleableMenuItem(String displayName, String... lore) {
        super(displayName, lore);
    }
    public ToggleableMenuItem(String displayName, ItemStack trueIcon, ItemStack falseIcon, String... lore) {
        super(displayName, trueIcon, falseIcon, lore);
    }

    public abstract void toggleValue(Player player);

    @Override
    public void onItemClick(ItemClickEvent event) {
        toggleValue(event.getPlayer());
        event.setWillUpdate(true);
    }

}
