package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.MySQL;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.io.File;

public class PlayerDamageInHomeListener implements Listener {
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
    if (!Util.CheckIsHome(p.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
      return; 
    if (Util.Check(p, p.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")).booleanValue())
      return; 
    if (CharmRealm.pluginVariable.bungee) {
      if (MySQL.getPVP(p.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))
        .equalsIgnoreCase("false")) {
        String temp = CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionPVP");
        p.sendMessage(temp);
        event.setCancelled(true);
      } 
    } else {
      File f2 = new File(CharmRealm.pluginVariable.Tempf, 
          String.valueOf(String.valueOf(event.getDamager().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))) + ".yml");
      YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f2);
      if (!yamlConfiguration.getBoolean("pvp")) {
        String temp = CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionPVP");
        p.sendMessage(temp);
        event.setCancelled(true);
      } 
    } 
  }
}
