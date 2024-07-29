package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.MySQL;
import com.Util.Util;
import java.io.File;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onLoad(WorldLoadEvent event) {
    String worldname = event.getWorld().getName().replace(Variable.world_prefix, "");
    World world = event.getWorld();
    if (Variable.bungee) {
      if (MySQL.CheckIsAHome(worldname) && 
        Main.JavaPlugin.getConfig().getBoolean("HDSwitch"))
        Util.refreshBorder(world); 
    } else {
      File f2 = new File(Variable.Tempf, String.valueOf(String.valueOf(worldname)) + ".yml");
      if (f2.exists() && 
        Main.JavaPlugin.getConfig().getBoolean("HDSwitch"))
        Util.refreshBorder(world); 
    } 
  }
}
