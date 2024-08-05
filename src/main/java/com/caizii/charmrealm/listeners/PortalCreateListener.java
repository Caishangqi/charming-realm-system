package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreateListener implements Listener {
  @EventHandler
  public void onCreate(PortalCreateEvent event) {
    if (Util.CheckIsHome(event.getWorld().getName()) && 
      CharmRealm.JavaPlugin.getConfig().getBoolean("DisablePortalCreate")) {
      for (Player p : event.getWorld().getPlayers())
        p.sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("DisablePortalCreate")); 
      event.setCancelled(true);
    } 
  }
}
