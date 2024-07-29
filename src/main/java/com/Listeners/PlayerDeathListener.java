package com.Listeners;

import com.SelfHome.Variable;
import com.Util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
  @EventHandler
  public void onDeath(PlayerDeathEvent event) {
    if (!Util.CheckIsHome(event.getEntity().getWorld().getName().replace(Variable.world_prefix, "")))
      return; 
    if (!Variable.wait_to_spawn_home.containsKey(event.getEntity().getName()))
      Variable.wait_to_spawn_home.put(event.getEntity().getName(), event.getEntity().getLocation().getWorld().getName()); 
  }
}
