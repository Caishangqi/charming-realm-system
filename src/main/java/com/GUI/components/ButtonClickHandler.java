package com.GUI.components;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ButtonClickHandler {
    void onClick(InventoryClickEvent event);
}
