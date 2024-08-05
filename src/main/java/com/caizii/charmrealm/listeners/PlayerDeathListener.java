package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
  @EventHandler
  public void onDeath(PlayerDeathEvent event) {
    if (!Util.CheckIsHome(event.getEntity().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
      return; 
    if (!CharmRealm.pluginVariable.wait_to_spawn_home.containsKey(event.getEntity().getName()))
      CharmRealm.pluginVariable.wait_to_spawn_home.put(event.getEntity().getName(), event.getEntity().getLocation().getWorld().getName()); 
  }
}
