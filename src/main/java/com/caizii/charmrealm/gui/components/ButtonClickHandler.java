package com.caizii.charmrealm.gui.components;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ButtonClickHandler {
    void onClick(InventoryClickEvent event);
}
