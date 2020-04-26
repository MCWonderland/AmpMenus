/*
 * This file is part of AmpMenus.
 *
 * Copyright (c) 2014-2020 <https://github.com/Scarsz/AmpMenus/>
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
package ninja.amp.ampmenus.items.pagination;

import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.menus.ItemMenu;
import ninja.amp.ampmenus.menus.PaginatedItemMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * This class is used internally in {@link PaginatedItemMenu}. This is the {@link MenuItem} that acts as a proxy for the
 * items supplied in {@link PaginatedItemMenu#getItems(Player)}. The main purpose of this class is to transparently act
 * as the correct MenuItem when one is present, otherwise behave as a {@link ItemMenu#EMPTY_SLOT_ITEM}.
 */
public class PaginationMenuItem extends MenuItem {

    private final PaginatedItemMenu menu;
    private final int index;

    public PaginationMenuItem(PaginatedItemMenu menu, int index) {
        super("Pagination item", new ItemStack(Material.AIR));
        this.menu = menu;
        this.index = index;
    }

    public MenuItem getEffectiveItem(Player player) {
        List<MenuItem> items = menu.getItems(player);
        int target = menu.getPage(player) * (9 * 5) + index;
        return items.size() - 1 >= target ? items.get(target) : ItemMenu.EMPTY_SLOT_ITEM;
    }

    @Override
    public ItemStack getFinalIcon(Player player) {
        return getEffectiveItem(player).getFinalIcon(player);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        getEffectiveItem(event.getPlayer()).onItemClick(event);
    }

    @Override
    public void onItemLeftClick(ItemClickEvent event) {
        getEffectiveItem(event.getPlayer()).onItemLeftClick(event);
    }

    @Override
    public void onItemRightClick(ItemClickEvent event) {
        getEffectiveItem(event.getPlayer()).onItemRightClick(event);
    }

    @Override
    public void onItemMiddleClick(ItemClickEvent event) {
        getEffectiveItem(event.getPlayer()).onItemMiddleClick(event);
    }

}
