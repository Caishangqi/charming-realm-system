package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    if (!CharmRealm.JavaPlugin.getConfig().getString("NormalJoinWorld").equalsIgnoreCase("")) {
      World world = Bukkit.getWorld(String.valueOf(String.valueOf(CharmRealm.pluginVariable.world_prefix)) + CharmRealm.JavaPlugin.getConfig().getString("NormalJoinWorld"));
      event.getPlayer().teleport(world.getSpawnLocation());
    } 
  }
}
