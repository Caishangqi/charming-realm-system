package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    if (!Main.JavaPlugin.getConfig().getString("NormalJoinWorld").equalsIgnoreCase("")) {
      World world = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + Main.JavaPlugin.getConfig().getString("NormalJoinWorld"));
      event.getPlayer().teleport(world.getSpawnLocation());
    } 
  }
}
