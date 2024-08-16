package com.caizii.charmrealm.utils;

import com.caizii.charmrealm.CharmRealm;
import me.casperge.realisticseasons.api.SeasonsAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeAsync {
  private static BukkitRunnable task;

  public static void asyncTime() {
    if (!CharmRealm.JavaPlugin.getConfig().getBoolean("EnableAsnycTime"))
      return;

    task = new BukkitRunnable() {
      public void run() {
        if (CharmRealm.JavaPlugin.getConfig().getBoolean("EnableAsnycTime")) {
          String async_world_name = CharmRealm.JavaPlugin.getConfig().getString("AsyncTimeWorld");
          if (Bukkit.getWorld(async_world_name) != null) {
            World async_world = Bukkit.getWorld(async_world_name);
            for (World world : Bukkit.getWorlds()) {
              if (HomeAPI.getHome(world.getName()) != null) {
                Home home = HomeAPI.getHome(world.getName().replace(CharmRealm.pluginVariable.world_prefix, ""));
                if (home.isLocktime())
                  continue;

                if (CharmRealm.JavaPlugin.getConfig().getBoolean("RealisticSeasons")) {
                  SeasonsAPI seasonsapi = SeasonsAPI.getInstance();
                  seasonsapi.setSeason(world, seasonsapi.getSeason(async_world));
                  seasonsapi.setDate(world, seasonsapi.getDate(async_world));
                  world.setTime(async_world.getTime());
                  continue;
                }

                world.setTime(async_world.getTime());
              }
            }
          }
        } else {
          cancel();
        }
      }
    };

    task.runTaskTimer(CharmRealm.JavaPlugin, 0L, 20L);
  }

  public static void cancelAsyncTime() {
    if (task != null) {
      task.cancel();
      task = null;
    }
  }
}

