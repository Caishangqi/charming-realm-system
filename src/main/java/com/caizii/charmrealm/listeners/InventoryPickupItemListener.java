package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.gui.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class InventoryPickupItemListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
  public void onOpen(InventoryPickupItemEvent event) {
    if (event.getInventory().getHolder() == null)
      return; 
    boolean check_gui_in_plugins = false;
    if (event.getInventory().getHolder() instanceof CheckGui) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof CreateGui) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof DenyGui) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof InviteGui) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof MainGui) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof ManageGui) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof ManageGui2) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof TrustGui) {
      check_gui_in_plugins = true;
    } else if (event.getInventory().getHolder() instanceof VisitGui) {
      check_gui_in_plugins = true;
    } 
    if (!check_gui_in_plugins)
      return; 
    event.setCancelled(true);
  }
}
