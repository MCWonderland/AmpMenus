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
package ninja.amp.ampmenus.menus;

import ninja.amp.ampmenus.Materials;
import ninja.amp.ampmenus.MenuListener;
import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.items.StaticMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A Menu controlled by ItemStacks in an Inventory.
 */
public class ItemMenu implements Listener {

    private JavaPlugin plugin;
    private String name;
    private Size size;
    private MenuItem[] items;
    private ItemMenu parent;

    /**
     * The {@link ninja.amp.ampmenus.items.StaticMenuItem} that appears in empty slots if {@link ninja.amp.ampmenus.menus.ItemMenu#fillEmptySlots()} is called.
     */
    public static final MenuItem EMPTY_SLOT_ITEM = new StaticMenuItem(" ", Materials.EMPTY_ITEM);

    /**
     * Creates an {@link ninja.amp.ampmenus.menus.ItemMenu} with no parent.
     *
     * @param name   The name of the inventory.
     * @param size   The {@link ninja.amp.ampmenus.menus.ItemMenu.Size} of the inventory.
     * @param plugin The Plugin instance.
     */
    public ItemMenu(String name, Size size, JavaPlugin plugin) {
        this(name, size, plugin, null);
    }

    /**
     * Creates an {@link ninja.amp.ampmenus.menus.ItemMenu}.
     *
     * @param name   The name of the inventory.
     * @param size   The {@link ninja.amp.ampmenus.menus.ItemMenu.Size} of the inventory.
     * @param plugin The {@link org.bukkit.plugin.java.JavaPlugin} instance.
     * @param parent The ItemMenu's parent.
     */
    public ItemMenu(String name, Size size, JavaPlugin plugin, ItemMenu parent) {
        this.plugin = plugin;
        this.name = name;
        this.size = size;
        this.items = new MenuItem[size.getSize()];
        this.parent = parent;
    }

    /**
     * Gets the name of the {@link ninja.amp.ampmenus.menus.ItemMenu}.
     *
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}'s name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of the {@link ninja.amp.ampmenus.menus.ItemMenu} for the given {@link Player}. Override to change
     * names of inventories for different players.
     *
     * @param player the player who's contextually opening this inventory
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}'s name.
     */
    public String getName(Player player) {
        return getName();
    }

    /**
     * Gets the {@link ninja.amp.ampmenus.menus.ItemMenu.Size} of the {@link ninja.amp.ampmenus.menus.ItemMenu}.
     *
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}'s {@link ninja.amp.ampmenus.menus.ItemMenu.Size}.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Checks if the {@link ninja.amp.ampmenus.menus.ItemMenu} has a parent.
     *
     * @return True if the {@link ninja.amp.ampmenus.menus.ItemMenu} has a parent, else false.
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Gets the parent of the {@link ninja.amp.ampmenus.menus.ItemMenu}.
     *
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}'s parent.
     */
    public ItemMenu getParent() {
        return parent;
    }

    /**
     * Sets the parent of the {@link ninja.amp.ampmenus.menus.ItemMenu}.
     *
     * @param parent The {@link ninja.amp.ampmenus.menus.ItemMenu}'s parent.
     */
    public void setParent(ItemMenu parent) {
        this.parent = parent;
    }

    /**
     * Sets the {@link ninja.amp.ampmenus.items.MenuItem} of a slot.
     *
     * @param position The slot position.
     * @param menuItem The {@link ninja.amp.ampmenus.items.MenuItem}.
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu setItem(int position, MenuItem menuItem) {
        items[position] = menuItem;
        return this;
    }

    /**
     * Sets the {@link ninja.amp.ampmenus.items.MenuItem} of a slot.
     *
     * @param row The slot row, 1-6, inclusive, value depending on item menu size.
     * @param column The slot column, 1-9, inclusive.
     * @param menuItem The {@link ninja.amp.ampmenus.items.MenuItem}.
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu setItem(int row, int column, MenuItem menuItem) {
        items[(row - 1) * 9 + (column - 1)] = menuItem;
        return this;
    }

    /**
     * Fills all empty slots in the {@link ninja.amp.ampmenus.menus.ItemMenu} with a certain {@link ninja.amp.ampmenus.items.MenuItem}.
     *
     * @param menuItem The {@link ninja.amp.ampmenus.items.MenuItem}.
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu fillEmptySlots(MenuItem menuItem) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = menuItem;
            }
        }
        return this;
    }

    /**
     * Fills all empty slots in the {@link ninja.amp.ampmenus.menus.ItemMenu} with the default empty slot item.
     *
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu fillEmptySlots() {
        return fillEmptySlots(EMPTY_SLOT_ITEM);
    }

    /**
     * Opens the {@link ninja.amp.ampmenus.menus.ItemMenu} for a player.
     *
     * @param player The player.
     */
    public void open(Player player) {
        if (!MenuListener.getInstance().isRegistered(plugin)) {
            MenuListener.getInstance().register(plugin);
        }
        Inventory inventory = Bukkit.createInventory(new MenuHolder(this, Bukkit.createInventory(player, size.getSize())), size.getSize(), getName(player));
        apply(inventory, player);
        player.openInventory(inventory);
    }

    /**
     * Updates the {@link ninja.amp.ampmenus.menus.ItemMenu} for a player.
     *
     * @param player The player to update the {@link ninja.amp.ampmenus.menus.ItemMenu} for.
     */
    @SuppressWarnings("deprecation")
    public void update(Player player) {
        if (player.getOpenInventory() != null) {
            Inventory inventory = player.getOpenInventory().getTopInventory();
            if (inventory.getHolder() instanceof MenuHolder && ((MenuHolder) inventory.getHolder()).getMenu().equals(this)) {
                apply(inventory, player);
                player.updateInventory();
            }
        }
    }

    /**
     * Applies the {@link ninja.amp.ampmenus.menus.ItemMenu} for a player to an Inventory.
     *
     * @param inventory The Inventory.
     * @param player    The Player.
     */
    private void apply(Inventory inventory, Player player) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                inventory.setItem(i, items[i].getFinalIcon(player));
            } else {
                inventory.setItem(i, null);
            }
        }
    }

    /**
     * Handles InventoryClickEvents for the {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public void onInventoryClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        if (slot >= 0 && slot < size.getSize() && items[slot] != null) {
            Player player = (Player) event.getWhoClicked();
            ItemClickEvent itemClickEvent = new ItemClickEvent(player, event.getClick());
            items[slot].onItemClick(itemClickEvent);
            if (event.isLeftClick()) items[slot].onItemLeftClick(itemClickEvent);
            if (event.isRightClick()) items[slot].onItemRightClick(itemClickEvent);
            if (event.getClick() == ClickType.MIDDLE) items[slot].onItemMiddleClick(itemClickEvent);

            if (itemClickEvent.willUpdate()) {
                update(player);
            } else {
                //noinspection deprecation
                player.updateInventory();

                if (itemClickEvent.willClose() || itemClickEvent.willGoBack()) {
                    final String playerName = player.getName();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        Player p = Bukkit.getPlayerExact(playerName);
                        if (p != null) {
                            p.closeInventory();
                        }
                    }, 1);
                }

                if (itemClickEvent.willGoBack() && hasParent()) {
                    final String playerName = player.getName();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        Player p = Bukkit.getPlayerExact(playerName);
                        if (p != null) {
                            parent.open(p);
                        }
                    }, 3);
                }
            }
        }
    }

    /**
     * Handles InventoryCloseEvent for the {@link ninja.amp.ampmenus.menus.ItemMenu}. Does nothing by default.
     */
    public void onInventoryClose(InventoryCloseEvent event) {
        // Do nothing by default
    }

    /**
     * Checks whether the passed player is "allowed" to close the menu. Override to create "captive" style menus.
     * @param player the player attempting to close the menu
     */
    public boolean isAllowedToClose(Player player) {
        // all menus can be closed by default
        return true;
    }

    /**
     * Destroys the {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public void destroy() {
        plugin = null;
        name = null;
        size = null;
        items = null;
        parent = null;
    }

    /**
     * Possible sizes of an {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public enum Size {

        ONE_LINE(9),
        TWO_LINE(18),
        THREE_LINE(27),
        FOUR_LINE(36),
        FIVE_LINE(45),
        SIX_LINE(54);

        private final int size;

        Size(int size) {
            this.size = size;
        }

        /**
         * Gets the {@link ninja.amp.ampmenus.menus.ItemMenu.Size}'s amount of slots.
         *
         * @return The amount of slots.
         */
        public int getSize() {
            return size;
        }

        /**
         * Gets the required {@link ninja.amp.ampmenus.menus.ItemMenu.Size} for an amount of slots.
         *
         * @param slots The amount of slots.
         * @return The required {@link ninja.amp.ampmenus.menus.ItemMenu.Size}.
         */
        public static Size fit(int slots) {
            if (slots <= 9) {
                return ONE_LINE;
            } else if (slots <= 18) {
                return TWO_LINE;
            } else if (slots <= 27) {
                return THREE_LINE;
            } else if (slots <= 36) {
                return FOUR_LINE;
            } else if (slots <= 45) {
                return FIVE_LINE;
            } else if (slots <= 54) {
                return SIX_LINE;
            } else {
                throw new IllegalArgumentException("Unable to fit " + slots + " slots into an inventory");
            }
        }

    }

}
