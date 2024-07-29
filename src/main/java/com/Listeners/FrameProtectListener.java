package com.Listeners;

import com.SelfHome.Variable;
import com.Util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class FrameProtectListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInteract(PlayerInteractEntityEvent event) {
    if (event.isCancelled())
      return; 
    if (!Util.CheckIsHome(event.getPlayer().getWorld().getName()))
      return; 
    if (!Util.Check(event.getPlayer(), event.getPlayer().getWorld().getName()).booleanValue()) {
      Entity entity = event.getRightClicked();
      if (entity.getName().toUpperCase().contains("FRAME")) {
        String temp = Variable.Lang_YML.getString("NoPermissionInteract");
        event.getPlayer().sendMessage(temp);
        event.setCancelled(true);
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
  public void onDamage(EntityDamageByEntityEvent event) {
    Player p = null;
    Entity entity = event.getDamager();
    if (entity instanceof Player) {
      p = (Player)entity;
    } else if (entity instanceof Projectile) {
      ProjectileSource ps = ((Projectile)entity).getShooter();
      if (ps instanceof Player)
        p = (Player)ps; 
    } 
    if (p == null)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    if (!Util.Check(p, p.getWorld().getName()).booleanValue()) {
      Entity entity2 = event.getEntity();
      if (entity2.getType() == EntityType.ITEM_FRAME) {
        String temp = Variable.Lang_YML.getString("NoPermissionInteract");
        p.sendMessage(temp);
        event.setCancelled(true);
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onHangingBreak(HangingBreakByEntityEvent event) {
    if (event.isCancelled())
      return; 
    if (!(event.getRemover() instanceof Player))
      return; 
    Player p = (Player)event.getRemover();
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    if (!Util.Check(p, p.getWorld().getName()).booleanValue()) {
      String temp = Variable.Lang_YML.getString("NoPermissionInteract");
      p.sendMessage(temp);
      event.setCancelled(true);
    } 
  }
}
