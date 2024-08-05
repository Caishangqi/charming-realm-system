package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class MaxHeightPlaceListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST)
  public void BlockCanBuildEvent(BlockPlaceEvent event) {
    if (event.isCancelled())
      return; 
    if (!Util.CheckIsHome(event.getBlock().getLocation().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
      return; 
    Block block = event.getBlock();
    if (block.getY() >= CharmRealm.JavaPlugin.getConfig().getInt("MaxHeight")) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("PlaceInMaxHeight"));
    } 
  }
}
