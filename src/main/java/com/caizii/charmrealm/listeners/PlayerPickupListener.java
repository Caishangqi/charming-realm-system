package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.MySQL;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.io.File;

public class PlayerPickupListener implements Listener {
  @EventHandler
  public void onPick(PlayerPickupItemEvent event) {
    if (CharmRealm.pluginVariable.bungee) {
      if (MySQL.getpickup(event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))
        .equalsIgnoreCase("false") && 
        
        !Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")).booleanValue()) {
        String temp = CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionPickupItem");
        event.getPlayer().sendMessage(temp);
        event.setCancelled(true);
      } 
    } else {
      File f2 = new File(CharmRealm.pluginVariable.Tempf, 
          String.valueOf(String.valueOf(event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))) + ".yml");
      if (f2.exists()) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f2);
        if (!yamlConfiguration.getBoolean("pickup") && 
          
          !Util.Check(event.getPlayer(), event.getPlayer().getLocation().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")).booleanValue()) {
          String temp = CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionPickupItem");
          event.getPlayer().sendMessage(temp);
          event.setCancelled(true);
        } 
      } 
    } 
  }
}
