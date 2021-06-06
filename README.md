# AmpMenus

An object-oriented approach to handling Inventory Menus/GUIs in Bukkit.

# Features

* Create object-oriented inventory GUI menus
* Implement complex, context-based functionality of items within menus
  * Differing actions depending on different click types, including shift
    * ex: left click to teleport to a warp, right click to delete it
  * Dynamically hide "unavailable" items
    * ex: staff-only item that appears when player has a permission & hides otherwise
  * Show enchantment effect on a "selected" item
  * Easily create items to "toggle" something (ToggleableMenuItem, TristateMenuItem)
* Create submenus of a parent item menu
* Built-in support for pagination when there can be many menu items
  * Pages of friends, worlds, punishments, etc.

# Use cases

AmpMenus is intended for advanced use cases such as the following examples:

![Example1](https://github.com/ampayne2/AmpMenus/blob/master/Example1.png)

Attribute Allocation Menu
* Displays the player's current attribute amounts on the attribute items
* Increase and decrease the points to add by clicking the wool above/below the attribute items
* Display the increase points item as green wool if the player has enough attribute points to increase the attribute
* Display the decrease points item as red wool if the player has actually increased the attribute
* Confirm or cancel point selection

![Example2](https://github.com/ampayne2/AmpMenus/blob/master/Example2.png)

Capture The Flag Shop
* Display the next available tier of different classes and perks

Because the items in these menus behave differently depending on the player and certain data or conditions, they would not be possible with a traditional plugin such as http://dev.bukkit.org/bukkit-plugins/chest-commands/ and would be impractical to create with a simpler utility such as https://forums.bukkit.org/threads/icon-menu.108342/

# Artifact

### Maven:
```xml
<repository>
    <id>scarsz</id>
    <url>https://nexus.scarsz.me/content/groups/public/</url>
</repository>

<dependency>
    <groupId>ninja.amp</groupId>
    <artifactId>ampmenus</artifactId>
    <version>1.10.0</version>
</dependency>
```

### Gradle:
```groovy
repositories {
    maven {
        url "https://nexus.scarsz.me/content/groups/public/"
    }
}

dependencies {
    compile 'ninja.amp:ampmenus:1.10.0'
}
```

## Basic Usage

```java
// Create an ItemMenu instance - you only need one of these. Don't create one every time you need it...
ItemMenu menu = new ItemMenu("Shop", Size.TWO_LINE, plugin);

// Adding items to your ItemMenu
menu.setItem(17, new CloseItem());

// Opening the menu for a Player
menu.open(player);
```

## Advanced Usage
```java
public class ShopMenu extends ItemMenu {
    public ShopMenu(JavaPlugin plugin) {
        super("Shop", Size.TWO_LINE, plugin);

        // Adding items to your ItemMenu
        setItem(17, new CloseItem());
    }

    // Useful in case you only want the back item to appear if the menu has a parent
    @Override
    public void setParent(ItemMenu parent) {
        super.setParent(parent);
        if (parent != null) {
            setItem(16, new BackItem());
        }
    }
}
```
```java
// When creating a new ItemMenu, a parent ItemMenu can be set
ItemMenu subMenu = new ItemMenu("Vote Perks", Size.FIVE_LINE, plugin, mainMenu);
```

Creating Menu Items:

```java
/**
* A MenuItem that makes the player perform the "kill" command
*/
public class SuicideItem extends MenuItem {
    private static final String DISPLAY_NAME = ChatColor.BLUE + "Click for OP!";
    private static final ItemStack ICON = new ItemStack(Material.DIAMOND);

    public SuicideItem() {
        super(DISPLAY_NAME, ICON);
    }

    // This method controls what happens when the MenuItem is clicked
    @Override
    public void onItemClick(ItemClickEvent event) {
        event.getPlayer().performCommand("kill");
    }

    // This method lets you modify the ItemStack before it is displayed, based on the player opening the menu
    @Override
    public ItemStack getFinalIcon(Player player) {
        ItemStack finalIcon = super.getFinalIcon(player);
        if (player.hasPermission("you.cant.fool.me")) {
            finalIcon.setType(Material.LEASH);
            ItemMeta meta = finalIcon.getItemMeta();
            meta.setDisplayName(ChatColor.DARK_RED + "Suicide");
            finalIcon.setItemMeta(meta);
        }
        return finalIcon;
    }
}
```

## Contributing

* All new files must include the license header. This can be done automatically with Maven by running mvn clean install.
* Generally follow the Oracle code conventions and the current style.
* Use four spaces for indentation, not tabs.
* 200 column limit for readability.
