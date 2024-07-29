package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.MySQL;
import com.Util.Util;
import java.io.File;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {
  @EventHandler
  public void onMove(final PlayerMoveEvent event) {
    (new BukkitRunnable() {
        public void run() {
          final Player p = event.getPlayer();
          if (p.isOp())
            return; 
          if (!Util.CheckIsHome(p.getWorld().getName().replace(Variable.world_prefix, "")))
            return; 
          double set_x = 0.0D;
          double min_x = 0.0D;
          double set_z = 0.0D;
          double min_z = 0.0D;
          if (Variable.bungee) {
            int level = 
              Integer.valueOf(MySQL.getLevel(p.getWorld().getName().replace(Variable.world_prefix, ""))).intValue();
            set_x = p.getWorld().getSpawnLocation().getX() + (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * level);
            min_x = p.getWorld().getSpawnLocation().getX() - (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * level);
            set_z = p.getWorld().getSpawnLocation().getZ() + (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * level);
            min_z = p.getWorld().getSpawnLocation().getZ() - (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * level);
          } else {
            File f2 = new File(Variable.Tempf, 
                String.valueOf(String.valueOf(p.getWorld().getName().replace(Variable.world_prefix, ""))) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f2);
            Location loc = p.getLocation();
            set_x = p.getWorld().getSpawnLocation().getX() + (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * yamlConfiguration.getInt("Level"));
            min_x = p.getWorld().getSpawnLocation().getX() - (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * yamlConfiguration.getInt("Level"));
            set_z = p.getWorld().getSpawnLocation().getZ() + (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * yamlConfiguration.getInt("Level"));
            min_z = p.getWorld().getSpawnLocation().getZ() - (
              Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 * yamlConfiguration.getInt("Level"));
          } 
          if (set_x < min_x) {
            double temp = set_x;
            set_x = min_x;
            min_x = temp;
          } 
          if (set_z < min_z) {
            double temp = set_z;
            set_z = min_z;
            min_z = temp;
          } 
          if (p.getLocation().getX() + 35.0D <= min_x || p.getLocation().getX() - 35.0D >= set_x || 
            p.getLocation().getZ() + 35.0D <= min_z || p.getLocation().getZ() - 35.0D >= set_z) {
            if (Main.JavaPlugin.getConfig().getBoolean("EnableAdventureMode") && 
              p.getGameMode() != GameMode.ADVENTURE) {
              p.setGameMode(GameMode.ADVENTURE);
              p.sendMessage(Variable.Lang_YML.getString("PlayerMoveOverBorderButAdventure"));
            } 
            if (!Main.JavaPlugin.getConfig().getString("BorderCommand").equalsIgnoreCase("") && 
              !Variable.DispathCommand.contains(p.getName()))
              Variable.DispathCommand.add(p.getName()); 
            cancel();
            return;
          } 
          if (p.getLocation().getX() + 25.0D <= min_x || p.getLocation().getX() - 25.0D >= set_x || 
            p.getLocation().getZ() + 25.0D <= min_z || p.getLocation().getZ() - 25.0D >= set_z) {
            if (Main.JavaPlugin.getConfig().getBoolean("PlayerMoveOverBorderBuff") && 
              !Variable.AddDebuff.contains(p.getName()))
              Variable.AddDebuff.add(p.getName()); 
            if (Main.JavaPlugin.getConfig().getBoolean("PlayerMoveOverBorderHit"))
              p.setVelocity(new Vector(0, 0, -3)); 
            cancel();
            return;
          } 
          if (p.getGameMode() == GameMode.ADVENTURE)
            (new BukkitRunnable() {
                public void run() {
                  p.setGameMode(GameMode.SURVIVAL);
                }
              }).runTask((Plugin)Main.JavaPlugin); 
        }
      }).runTaskAsynchronously((Plugin)Main.JavaPlugin);
  }
}
