package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.gui.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryMoveItemListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
  public void onOpen(InventoryMoveItemEvent event) {
    if (event.getDestination().getHolder() == null)
      return; 
    boolean check_gui_in_plugins = false;
    if (event.getDestination().getHolder() instanceof CheckGui) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof CreateGui) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof DenyGui) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof InviteGui) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof MainGui) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof ManageGui) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof ManageGui2) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof TrustGui) {
      check_gui_in_plugins = true;
    } else if (event.getDestination().getHolder() instanceof VisitGui) {
      check_gui_in_plugins = true;
    } 
    if (!check_gui_in_plugins)
      return; 
    event.setCancelled(true);
  }
}
