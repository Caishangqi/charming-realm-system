package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class LivingEntityProtectInHomeListener implements Listener {
  @EventHandler
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
    if (!(event.getEntity() instanceof org.bukkit.entity.LivingEntity))
      return; 
    if (event.getEntity() instanceof Player)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
      return; 
    if (!Util.Check(p, p.getLocation().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")).booleanValue())
      event.setCancelled(true); 
  }
}
