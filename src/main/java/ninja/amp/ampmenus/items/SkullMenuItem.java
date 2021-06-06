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

import github.scarsz.mojang.Head;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * A {@link MenuItem} that displays a player skull.
 */
public class SkullMenuItem extends MenuItem {

    private SkullTarget target;

    public SkullMenuItem(String displayName, String... lore) {
        super(displayName, Head.getPlayerSkullItem(), lore);
        this.target = null;
    }
    public SkullMenuItem(String displayName, String textureId, String... lore) {
        super(displayName, Head.getPlayerSkullItem(), lore);
        this.target = new SkullTarget.SkullTexture(textureId);
    }
    public SkullMenuItem(String displayName, UUID uuid, String... lore) {
        super(displayName, Head.getPlayerSkullItem(), lore);
        this.target = new SkullTarget.UUIDTexture(uuid);
    }
    public SkullMenuItem(String displayName, OfflinePlayer player, String... lore) {
        this(displayName, player.getUniqueId(), lore);
    }

    @Override
    public ItemStack getFinalIcon(Player player) {
        ItemStack item;
        if (target != null) {
            item = target.getItem();
        } else {
            item = Head.create(player);
        }

        setNameAndLore(item, getDisplayName(), getLore());
        return item;
    }

    public SkullTarget getTarget() {
        return target;
    }

    public void setTarget(SkullTarget target) {
        this.target = target;
    }

    private interface SkullTarget {

        ItemStack getItem();

        class SkullTexture implements SkullTarget {

            private final String textureId;

            public SkullTexture(String textureId) {
                this.textureId = textureId;
            }

            @Override
            public ItemStack getItem() {
                return Head.createFromTexture(textureId);
            }

        }

        class UUIDTexture implements SkullTarget {

            private final UUID uuid;

            public UUIDTexture(UUID uuid) {
                this.uuid = uuid;
            }

            @Override
            public ItemStack getItem() {
                return Head.create(uuid);
            }

        }

    }

}
