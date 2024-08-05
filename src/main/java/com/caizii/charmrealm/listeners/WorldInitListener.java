package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.CustomChunkGenerator;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.ChunkGenerator;

public class WorldInitListener implements Listener {
  @EventHandler
  public void onInit(WorldInitEvent event) {
    boolean check_false_core = false;
    if (Bukkit.getVersion().toString().contains("1.7.10") || Bukkit.getVersion().toString().contains("Paper") || Bukkit.getVersion().toString().contains("Purper"))
      check_false_core = true; 
    boolean check_is_home = false;
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (event.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "").equalsIgnoreCase(p.getName()))
        check_is_home = true; 
    } 
    if (CharmRealm.pluginVariable.create_list_home.contains(event.getWorld().getName())) {
      event.getWorld().setAutoSave(false);
      event.getWorld().setKeepSpawnInMemory(false);
    } 
    if (Util.CheckIsHome(event.getWorld().getName()) || check_is_home || CharmRealm.pluginVariable.create_list_home.contains(event.getWorld().getName())) {
      if (CharmRealm.pluginVariable.create_list_home.contains(event.getWorld().getName()))
        CharmRealm.pluginVariable.create_list_home.remove(event.getWorld().getName()); 
      event.getWorld().setKeepSpawnInMemory(CharmRealm.JavaPlugin.getConfig().getBoolean("KeepSpawnInMemory"));
      Location loc = event.getWorld().getHighestBlockAt(event.getWorld().getSpawnLocation()).getLocation();
      if (Bukkit.getVersion().contains("1.7.10") || Bukkit.getVersion().contains("1.7.2")) {
        event.getWorld().setSpawnLocation((int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
      } else {
        event.getWorld().setSpawnLocation(loc);
      } 
      if (!check_false_core) {
        WorldCreator worldCreator = new WorldCreator(event.getWorld().getName());
        worldCreator = worldCreator.generator((ChunkGenerator)new CustomChunkGenerator());
        Bukkit.getServer().createWorld(worldCreator);
      } 
    } 
  }
}
