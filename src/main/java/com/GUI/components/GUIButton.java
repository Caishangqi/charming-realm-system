package com.GUI.components;

import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class GUIButton {

    @Getter
    private final CustomGUI parentGUI;
    @Getter
    private final ItemStack item;

    public GUIButton(CustomGUI parentGUI, ItemStack item) {
        this.parentGUI = parentGUI;
        this.item = item;
    }

    public abstract void onClick(InventoryClickEvent event);
}
