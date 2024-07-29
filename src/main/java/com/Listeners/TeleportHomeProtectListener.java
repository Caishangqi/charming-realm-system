package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportHomeProtectListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
  public void onTeleport(final PlayerTeleportEvent event) {
    World world = event.getTo().getWorld();
    if (!Util.CheckIsHome(world.getName().replace(Variable.world_prefix, "")))
      return; 
    if (!Main.JavaPlugin.getConfig().getBoolean("EnableSpawnProtection"))
      return; 
    (new BukkitRunnable() {
        public void run() {
          Location loc = event.getTo();
          boolean check = false;
          double firstY = loc.getY();
          for (double i = firstY; i > 0.0D; i--) {
            loc.setY(i);
            if (loc.getWorld().getBlockAt(loc).getType() != Material.AIR) {
              check = true;
              break;
            } 
          } 
          if (!check && 
            event.getTo().getWorld().getBlockAt(event.getTo()).getType() == Material.AIR) {
            event.getPlayer().sendMessage(Variable.Lang_YML.getString("SpawnProtection"));
            event.getTo().getWorld().getBlockAt(event.getTo()).setType(Material.GLASS);
          } 
        }
      }).runTask((Plugin)Main.JavaPlugin);
  }
}
