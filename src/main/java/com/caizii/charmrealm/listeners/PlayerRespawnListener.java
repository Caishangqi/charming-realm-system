package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class PlayerRespawnListener implements Listener {
  @EventHandler
  public void onSpawn(final PlayerRespawnEvent event) {
    if (!CharmRealm.JavaPlugin.getConfig().getBoolean("EnableAutoRespawnInHome"))
      return; 
    if (!CharmRealm.pluginVariable.wait_to_spawn_home.containsKey(event.getPlayer().getName()))
      return; 
    String worldname = ((String)CharmRealm.pluginVariable.wait_to_spawn_home.get(event.getPlayer().getName())).replace(CharmRealm.pluginVariable.world_prefix, "");
    final Location loc = Bukkit.getWorld(String.valueOf(CharmRealm.pluginVariable.world_prefix) + worldname).getSpawnLocation();
    if (CharmRealm.pluginVariable.bungee) {
      Double X = Double.valueOf(MySQL.getX(worldname));
      Double Y = Double.valueOf(MySQL.getY(worldname));
      Double Z = Double.valueOf(MySQL.getZ(worldname));
      loc.setX(X.doubleValue());
      loc.setY(Y.doubleValue());
      loc.setZ(Z.doubleValue());
      event.setRespawnLocation(loc);
      (new BukkitRunnable() {
          public void run() {
            event.getPlayer().teleport(loc);
          }
        }).runTaskLater((Plugin) CharmRealm.JavaPlugin, 5L);
    } else {
      File f2 = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(worldname)) + ".yml");
      YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f2);
      loc.setX(yamlConfiguration.getDouble("X"));
      loc.setY(yamlConfiguration.getDouble("Y"));
      loc.setZ(yamlConfiguration.getDouble("Z"));
      event.setRespawnLocation(loc);
      (new BukkitRunnable() {
          public void run() {
            event.getPlayer().teleport(loc);
          }
        }).runTaskLater((Plugin) CharmRealm.JavaPlugin, 5L);
    } 
    CharmRealm.pluginVariable.wait_to_spawn_home.remove(event.getPlayer().getName());
  }
}
