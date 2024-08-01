package com.GUI.components;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class CustomGUI {
    private final Inventory inventory;
    private final Map<Integer, GUIButton> buttons = new HashMap<>();

    public CustomGUI(String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, title);
        onCustomGUIInitialize();
    }

    // Set the layouts and buttons
    public void onCustomGUIInitialize() {

    }

    // Start rend the buttons to the native bukkit inventory
    public boolean onCustomGUIDisplay(Player player) {
        return false;
    }

    public boolean onCustomGUIClose(Player player) {
        return false;
    }

    public void setButton(int slot, GUIButton button) {
        buttons.put(slot, button);
        inventory.setItem(slot, button.getItem());
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void handleClick(InventoryClickEvent event) {
        GUIButton button = buttons.get(event.getSlot());
        if (button != null) {
            button.onClick(event);
        }
    }
}
