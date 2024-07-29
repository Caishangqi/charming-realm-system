package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreateListener implements Listener {
  @EventHandler
  public void onCreate(PortalCreateEvent event) {
    if (Util.CheckIsHome(event.getWorld().getName()) && 
      Main.JavaPlugin.getConfig().getBoolean("DisablePortalCreate")) {
      for (Player p : event.getWorld().getPlayers())
        p.sendMessage(Variable.Lang_YML.getString("DisablePortalCreate")); 
      event.setCancelled(true);
    } 
  }
}
