package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.Util;
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
    if (!Util.CheckIsHome(event.getBlock().getLocation().getWorld().getName().replace(Variable.world_prefix, "")))
      return; 
    Block block = event.getBlock();
    if (block.getY() >= Main.JavaPlugin.getConfig().getInt("MaxHeight")) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(Variable.Lang_YML.getString("PlaceInMaxHeight"));
    } 
  }
}
