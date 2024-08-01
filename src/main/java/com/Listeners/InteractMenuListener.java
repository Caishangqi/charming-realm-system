package com.Listeners;

import com.SelfHome.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractMenuListener implements Listener {
  @EventHandler
  public void onMenuOpen(PlayerInteractEvent event) {
    if (event.getPlayer().getItemInHand().getType().toString()
      .equalsIgnoreCase(Main.JavaPlugin.getConfig().getString("Material")))
      Bukkit.dispatchCommand((CommandSender)event.getPlayer(), "realm open"); 
  }
}
