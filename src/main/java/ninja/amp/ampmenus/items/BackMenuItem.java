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
import ninja.amp.ampmenus.events.ItemClickEvent;
import org.bukkit.ChatColor;

/**
 * A {@link ninja.amp.ampmenus.items.StaticMenuItem} that opens the {@link ninja.amp.ampmenus.menus.ItemMenu}'s parent menu if it exists.
 */
public class BackMenuItem extends StaticMenuItem {

    public BackMenuItem() {
        super(ChatColor.RED + "Back", Materials.BACK_ITEM);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillGoBack(true);
    }

}
