package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.MySQL;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import java.io.File;

public class WorldLoadListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onLoad(WorldLoadEvent event) {
    String worldname = event.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "");
    World world = event.getWorld();
    if (CharmRealm.pluginVariable.bungee) {
      if (MySQL.CheckIsAHome(worldname) && 
        CharmRealm.JavaPlugin.getConfig().getBoolean("HDSwitch"))
        Util.refreshBorder(world); 
    } else {
      File f2 = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(worldname)) + ".yml");
      if (f2.exists() && 
        CharmRealm.JavaPlugin.getConfig().getBoolean("HDSwitch"))
        Util.refreshBorder(world); 
    } 
  }
}
