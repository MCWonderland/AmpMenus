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
package ninja.amp.ampmenus.items.pagination;

import ninja.amp.ampmenus.Materials;
import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.menus.PaginatedItemMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This class is used internally in {@link PaginatedItemMenu}. This is the {@link MenuItem} that increments/decrements
 * the current page the player is on.
 */
public class PaginationPageItem extends MenuItem {

    private final PaginatedItemMenu menu;
    private final int modifier;

    public PaginationPageItem(PaginatedItemMenu menu, int modifier) {
        super(" ", new ItemStack(Material.PAPER));
        this.menu = menu;
        this.modifier = modifier;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        int clamped = clampPageNumber(event.getPlayer(), this.menu.getPage(event.getPlayer()) + modifier);
        this.menu.setPage(event.getPlayer(), clamped);
        event.setWillUpdate(true);
    }

    @Override
    public ItemStack getFinalIcon(Player player) {
        int target = this.menu.getPage(player) + modifier;
        int clamped = clampPageNumber(player, this.menu.getPage(player) + modifier);
        boolean validPage = target == clamped;

        if (target != 0 && validPage) {
            ItemStack icon = this.getIcon().clone();
            icon.setAmount(target);
            return icon;
        } else {
            return Materials.EMPTY_ITEM;
        }
    }

    private int getPageCount(Player player) {
        return (this.menu.getItems(player).size() / (9 * 5)) + 1;
    }

    private int clampPageNumber(Player player, int page) {
        return Math.max(0, Math.min(page, getPageCount(player) - 1));
    }

}
