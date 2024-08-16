package com.caizii.charmrealm.gui.handler;

import com.caizii.charmrealm.gui.canvas.Canvas;
import com.caizii.charmrealm.gui.components.CharmGUIBase;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * GUIHandler maintain a player uuid and canvas hashmap, and handle 2
 * event listener: InventoryClick and InventoryOpen Event.
 * <p>
 * The only 2 methods you need is openGUI and closeAllGUI, the GUI
 * update and internal logic is not handle by GUI handler
 */
public class CharmGUIHandler implements Listener {

    // For settings gui
    public static FileConfiguration GUI_SETTING_YML;

    private final Map<UUID, Canvas> canvas = new HashMap<>();

    /**
     * Open specific class of gui and display to player, the canvas will handle
     * and optimize whether or not create new gui
     * @param player The instigator player
     * @param guiClass The gui class you want to create or open for player
     * @param <T> The class
     */
    public <T extends CharmGUIBase> void openGUI(Player player, Class<T> guiClass) {
        if (canvas.containsKey(player.getUniqueId())) {
            canvas.get(player.getUniqueId()).displayGUIToViewport(guiClass);
        } else {
            Canvas newCanvas = new Canvas(player);
            canvas.put(player.getUniqueId(), newCanvas);
            newCanvas.displayGUIToViewport(guiClass);
        }
    }


    /**
     * The InventoryOpenEvent event lister, listen and initiate
     * guiOpenEvent() for guis
     * @param event Receive InventoryOpenEvent
     */
    @EventHandler
    public void onInventoryDisplay(InventoryOpenEvent event) {

        if (event.getInventory().getHolder() instanceof CharmGUIBase) {
            Player player = (Player) event.getPlayer();
            CharmGUIBase gui = (CharmGUIBase) event.getInventory().getHolder();
            Canvas playerCanvas = canvas.get(player.getUniqueId());

            if (playerCanvas != null)
                playerCanvas.guiOpenEvent(player, gui);
        }
    }

    /**
     * The InventoryClickEvent event lister, pass the event to all opened
     * and Subclass of CharmGUIBase.
     * @param event Receive InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Canvas playerCanvas = canvas.get(player.getUniqueId());

        if (event.getClickedInventory() == null)
            return;

        if (((event.getClickedInventory()).getHolder() instanceof CharmGUIBase)) {
            CharmGUIBase gui = (CharmGUIBase) event.getClickedInventory().getHolder();
            if (gui != null) {
                playerCanvas.guiClickEvent(event, gui);
            }
        } else {
            return;
        }
    }

    /**
     * Close all gui for all player in server, ensure data completely
     * and avoid item duplication
     */
    public void closeAllGUI() {

        canvas.forEach((uuid, canvas) -> {
            if (canvas != null) {
                canvas.closeDisplayedGUI(Bukkit.getPlayer(uuid));
            }
        });
    }


}
