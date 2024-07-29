package com.Listeners;

import com.SelfHome.Variable;
import com.Util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HomeProtectPlaceListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlace3(BlockPlaceEvent event) {
    if (!Util.CheckIsHome(event.getPlayer().getWorld().getName().replaceAll(Variable.world_prefix, "")))
      return; 
    if (!Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replaceAll(Variable.world_prefix, "")).booleanValue()) {
      String temp = Variable.Lang_YML.getString("NoPermissionInteract");
      event.getPlayer().sendMessage(temp);
      event.setCancelled(true);
    } 
  }
}
