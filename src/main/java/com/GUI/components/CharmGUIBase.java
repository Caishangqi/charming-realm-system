package com.GUI.components;

import com.GUI.factory.BaseButtonFactory;
import com.Util.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharmGUIBase implements InventoryHolder {
    private final Inventory inventory;
    private final Map<Integer, GUIButton> buttons = new HashMap<>();
    @Getter
    private final boolean bIsAllowEmptyClick = false;
    @Getter
    protected Player owner;
    @Getter
    protected FileConfiguration GUIConfigYML;
    @Getter
    protected String title;
    @Getter
    protected int size;

    public CharmGUIBase(Player owner) {
        this.GUIConfigYML = getGUIConfig();
        this.owner = owner;
        String GUITitle = GUIConfigYML.getString("name");
        size = GUIConfigYML.getInt("size");
        String parsedTitle = Color.parseColorAndPlaceholder(owner, GUITitle);
        assert GUITitle != null;
        this.inventory = Bukkit.createInventory(this, size, parsedTitle);
        onCustomGUIInitialize();
    }

    // Set the layouts and buttons but not render papi and color
    public void onCustomGUIInitialize() {

    }

    // Start rend the buttons and papi, color to the native bukkit inventory
    public boolean onCustomGUIDisplay(Player player) {
        for (GUIButton button : buttons.values()) {
            button.rendButton(player);
        }
        return player.isValid();
    }

    public boolean onCustomGUIClose(Player player) {
        player.closeInventory();
        return player.isValid();
    }

    /*
    Only put the Button instance on List (Layout) later onDisplay will rend those
    buttons into the bukkit inventory
     */
    public void setButton(GUIButton button, ButtonClickHandler handler) {
        buttons.put(button.slotIndex, button);
        button.setHandler(handler);
    }

    /*
    Only put the Button instance on List (Layout) later onDisplay will rend those
    buttons into the bukkit inventory
     */
    public void setButton(List<GUIButton> buttons, ButtonClickHandler handler) {
        if (buttons.size() == 1) {
            setButton(buttons.get(0), handler);
        } else {
            for (GUIButton button : buttons) {
                setButton(button, handler);
            }
        }

    }

    public void open(Player player) {
        this.owner = player;
        onCustomGUIDisplay(player);
        player.openInventory(inventory);
    }

    public void handleClick(InventoryClickEvent event) {

        GUIButton button = buttons.get(event.getSlot());
        if (button != null) {
            button.onClick(event);
        }

        event.setCancelled(!bIsAllowEmptyClick);
    }

    public ConfigurationSection readButtonConfiguration(String ButtonKey) {
        return this.GUIConfigYML.getConfigurationSection("items." + ButtonKey);
    }


    public List<GUIButton> readButtons(String ButtonKey) {
        ConfigurationSection buttonConfig = readButtonConfiguration(ButtonKey);
        ConfigurationSection buttonItemConfig = buttonConfig.getConfigurationSection("item");

        return new BaseButtonFactory(Material.valueOf(buttonItemConfig.getString("material")), buttonItemConfig.getInt("modelID"), buttonItemConfig.getString("name"), buttonItemConfig.getStringList("lore"), buttonConfig.getString("slot"), Sound.valueOf(buttonConfig.getString("sound")), buttonConfig.getDouble("pitch"), buttonConfig.getDouble("volume")).createButton(this);
    }

    public FileConfiguration getGUIConfig() {
        return this.GUIConfigYML;
    }

    ;

    /**
     * @return
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
