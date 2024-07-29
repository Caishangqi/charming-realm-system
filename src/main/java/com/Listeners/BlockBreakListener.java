package com.Listeners;

import com.SelfHome.Variable;
import com.Util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
  @EventHandler
  public void onBreak(BlockBreakEvent event) {
    if (event.getPlayer().getName().toUpperCase().contains("AS-FAKEPLAYER") || 
      event.getPlayer().getName().toUpperCase().contains("[MINECRAFT]") || 
      event.getPlayer().getName().toUpperCase().contains("[MEKANISM]") || 
      event.getPlayer().getName().toUpperCase().contains("[IF]"))
      return; 
    if (!Util.CheckIsHome(event.getPlayer().getWorld().getName().replace(Variable.world_prefix, "")))
      return; 
    if (!Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replace(Variable.world_prefix, "")).booleanValue()) {
      String temp = Variable.Lang_YML.getString("NoPermissionBreakBlock");
      event.getPlayer().sendMessage(temp);
      event.setCancelled(true);
    } 
  }
}
