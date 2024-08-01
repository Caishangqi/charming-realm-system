package com.GUI.handler;

import com.GUI.components.CustomGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager implements Listener {

    private final Map<UUID, CustomGUI> openGUIs = new HashMap<>();

    public void openGUI(Player player, CustomGUI gui) {
        openGUIs.put(player.getUniqueId(), gui);
        gui.open(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        CustomGUI gui = openGUIs.get(player.getUniqueId());
        if (gui != null) {
            gui.handleClick(event);
            event.setCancelled(true);
        }
    }
}
