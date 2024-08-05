package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HomeProtectPlaceListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlace3(BlockPlaceEvent event) {
    if (!Util.CheckIsHome(event.getPlayer().getWorld().getName().replaceAll(CharmRealm.pluginVariable.world_prefix, "")))
      return; 
    if (!Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replaceAll(CharmRealm.pluginVariable.world_prefix, "")).booleanValue()) {
      String temp = CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionInteract");
      event.getPlayer().sendMessage(temp);
      event.setCancelled(true);
    } 
  }
}
