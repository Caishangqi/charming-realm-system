package com.Listeners;

import com.SelfHome.Variable;
import com.Util.MySQL;
import com.Util.Util;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {
  @EventHandler
  public void onDrop(PlayerDropItemEvent event) {
    if (Variable.bungee) {
      if (MySQL.getdropitem(event.getPlayer().getWorld().getName().replace(Variable.world_prefix, ""))
        .equalsIgnoreCase("false") && 
        
        !Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replace(Variable.world_prefix, "")).booleanValue()) {
        String temp = Variable.Lang_YML.getString("NoPermissionDropItem");
        event.getPlayer().sendMessage(temp);
        event.setCancelled(true);
      } 
    } else {
      File f2 = new File(Variable.Tempf, 
          String.valueOf(String.valueOf(event.getPlayer().getWorld().getName().replace(Variable.world_prefix, ""))) + ".yml");
      if (f2.exists()) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f2);
        if (!yamlConfiguration.getBoolean("drop") && 
          
          !Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replace(Variable.world_prefix, "")).booleanValue()) {
          String temp = Variable.Lang_YML.getString("NoPermissionDropItem");
          event.getPlayer().sendMessage(temp);
          event.setCancelled(true);
        } 
      } 
    } 
  }
}
