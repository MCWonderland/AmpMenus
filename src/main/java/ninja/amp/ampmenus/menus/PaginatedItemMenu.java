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
package ninja.amp.ampmenus.menus;

import ninja.amp.ampmenus.items.BackMenuItem;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.items.pagination.PaginationMenuItem;
import ninja.amp.ampmenus.items.pagination.PaginationPageItem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>This is an extension of {@link ItemMenu}. This menu will collect a list of {@link MenuItem}s to display to the
 * player and will automatically paginate those items across however many pages necessary.</p>
 *
 * <p>Some things to note about this menu:</p>
 * <ul>
 *     <li>This menu is always has a size of six lines.</li>
 *     <li>If a parent ItemMenu is set in the constructor, a {@link BackMenuItem} will be in the middle-bottom slot.</li>
 *     <li>Previous/next page items will be in the bottom left and right respectively, and will only be visible when necessary</li>
 * </ul>
 */
public abstract class PaginatedItemMenu extends ItemMenu {

    private final Map<UUID, AtomicInteger> pages = new HashMap<>();

    /**
     * Creates a {@link PaginatedItemMenu}.
     *
     * @param name   The name of the inventory.
     * @param plugin The {@link org.bukkit.plugin.java.JavaPlugin} instance.
     */
    public PaginatedItemMenu(String name, JavaPlugin plugin) {
        this(name, plugin, null);
    }

    /**
     * Creates a {@link PaginatedItemMenu}.
     *
     * @param name   The name of the inventory.
     * @param plugin The {@link org.bukkit.plugin.java.JavaPlugin} instance.
     * @param parent The ItemMenu's parent. If present, a {@link BackMenuItem} will be in the middle-bottom slot.
     */
    public PaginatedItemMenu(String name, JavaPlugin plugin, ItemMenu parent) {
        super(name, Size.SIX_LINE, plugin, parent);

        for (int i = 0; i < Size.FIVE_LINE.getSize(); i++) {
            setItem(i, new PaginationMenuItem(this, i));
        }

        setItem(6, 1, new PaginationPageItem(this, -1));
        if (parent != null) setItem(6, 5, new BackMenuItem());
        setItem(6, 9, new PaginationPageItem(this, 1));
        fillEmptySlots();
    }

    /**
     * This method should return items that should be visible in this menu. For instance,
     * @param player the player that items should be returned for
     * @return the complete list of {@link MenuItem}s to be shown in this menu, across all pages.
     */
    public abstract List<MenuItem> getItems(Player player);

    @Override
    public void open(Player player) {
        ensurePageDataAvailable(player);
        super.open(player);
    }

    /**
     * Gets the current page number that the player is on.
     * @param player the player who's page will be returned
     * @return the page the player is on, 0-indexed.
     */
    public int getPage(Player player) {
        ensurePageDataAvailable(player);
        return pages.get(player.getUniqueId()).get();
    }

    /**
     * Sets the page number that the player is on. This method will not update the GUI; you should probably call
     * {@link #update(Player)} after this method.
     * @param player the player who's page will be set
     * @param page the page the player should be set to, 0-indexed.
     */
    public void setPage(Player player, int page) {
        ensurePageDataAvailable(player);
        pages.get(player.getUniqueId()).set(page);
        update(player);
    }

    private void ensurePageDataAvailable(Player player) {
        if (!pages.containsKey(player.getUniqueId())) {
            pages.put(player.getUniqueId(), new AtomicInteger());
        }
    }

}
