package com.caizii.charmrealm.gui.handler;

import com.caizii.charmrealm.gui.components.CharmGUIBase;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CharmGUIHandler implements Listener {

    // For settings gui
    public static FileConfiguration GUI_SETTING_YML;

    private final Map<UUID, CharmGUIBase> openGUIs = new HashMap<>();

    public void openGUI(CharmGUIBase gui) {
        openGUIs.put(gui.getOwner().getUniqueId(), gui);
        gui.open(gui.getOwner());
    }

    public void openGUI(Player player, CharmGUIBase gui) {
        openGUIs.put(player.getUniqueId(), gui);
        gui.open(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        CharmGUIBase gui = openGUIs.get(player.getUniqueId());

        if (event.getClickedInventory() == null)
            return;

        if (((event.getClickedInventory()).getHolder() instanceof CharmGUIBase)) {
            if (gui != null) {
                gui.handleClick(event);
            }
        } else {
            return;
        }
    }

    public boolean closeAllGUI() {
        openGUIs.forEach((uuid, gui) -> {
            if (gui != null) {
                gui.onCustomGUIClose(Bukkit.getPlayer(uuid));
            }
        });
        return true;
    }


}
