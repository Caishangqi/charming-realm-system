package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EntityInteractByEntityListener implements Listener {
  List<String> list = new ArrayList<>();
  
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onInteract(PlayerInteractAtEntityEvent event) {
    if (event.isCancelled())
      return; 
    if (event.getRightClicked().getType() == EntityType.PLAYER)
      return; 
    if (!Util.CheckIsHome(event.getPlayer().getWorld().getName()))
      return; 
    if (!Util.Check(event.getPlayer(), event.getPlayer().getWorld().getName()).booleanValue() && 
      CharmRealm.JavaPlugin.getConfig().getBoolean("EnableEntityInteract")) {
      event.getPlayer().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("ProtectEntity"));
      event.setCancelled(true);
      if (event.getRightClicked().getType().toString().toUpperCase().contains("BLADESTAND") && event.getPlayer().getItemInHand().getType().toString().equalsIgnoreCase("AIR")) {
        this.list.add(event.getPlayer().getName());
        return;
      } 
      if (this.list.contains(event.getPlayer().getName())) {
        ItemStack item = event.getPlayer().getItemInHand();
        if (!item.getType().toString().toUpperCase().contains("BLADE")) {
          this.list.remove(event.getPlayer().getName());
          return;
        } 
        Bukkit.dispatchCommand((CommandSender)event.getPlayer(), "realm gift send " + event.getPlayer().getWorld().getName());
        this.list.remove(event.getPlayer().getName());
      } 
    } 
  }
}
