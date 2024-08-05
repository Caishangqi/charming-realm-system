package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FarmProtectListener implements Listener {
  @EventHandler
  public void onBreak(BlockBreakEvent event) {
    if (event.getPlayer().getName().toUpperCase().contains("AS-FAKEPLAYER") || 
      event.getPlayer().getName().toUpperCase().contains("[MINECRAFT]") || 
      event.getPlayer().getName().toUpperCase().contains("[MEKANISM]") || 
      event.getPlayer().getName().toUpperCase().contains("[IF]"))
      return; 
    if (!Util.CheckIsHome(event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
      return; 
    if (!Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")).booleanValue()) {
      String temp = CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionBreakBlock");
      event.getPlayer().sendMessage(temp);
      event.setCancelled(true);
    } 
  }
}
