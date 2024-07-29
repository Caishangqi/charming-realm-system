package com.Util;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FirstBorderShaped {
  public static void AddShapeBorder(final World world) {
    (new BukkitRunnable() {
        public void run() {
          String serverType = Bukkit.getServer().getName();
          if ("arclight".equalsIgnoreCase(serverType)) {
            FirstBorderShaped.clearAndSetBorder(world);
          } else if (Variable.hook_FastAsyncWorldEdit && Main.JavaPlugin.getConfig().getBoolean("FaweSwitch") && Main.JavaPlugin.getConfig().getBoolean("UpdateClearOld")) {
            FaweAPI.getTaskManager().async(new Runnable() {
                  public void run() {
                    Home home = HomeAPI.getHome(String.valueOf(Variable.world_prefix) + world.getName());
                    int radius = (home.getLevel() - 1) * Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 + 4;
                    AsyncWorld aw = AsyncWorld.wrap(world);
                    List<Location> list = new ArrayList<>();
                    if (Main.JavaPlugin.getConfig().getString("BorderShape").equalsIgnoreCase("Circle")) {
                      list = FirstBorderShaped.traverseSphere(world, (int)world.getSpawnLocation().getX(), (int)world.getSpawnLocation().getY(), (int)world.getSpawnLocation().getZ(), radius);
                    } else if (Main.JavaPlugin.getConfig().getString("BorderShape").equalsIgnoreCase("Square")) {
                      list = FirstBorderShaped.traverseCube(world, (int)world.getSpawnLocation().getX(), (int)world.getSpawnLocation().getY(), (int)world.getSpawnLocation().getZ(), radius);
                    } 
                    for (Location loc : list) {
                      Block block = world.getBlockAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
                      if (block.getType() == Material.valueOf(Main.JavaPlugin.getConfig().getString("BorderMaterial")))
                        block.setType(Material.AIR); 
                    } 
                    FirstBorderShaped.ShapeBorder(world);
                  }
                });
          } 
        }
      }).runTaskLater((Plugin)Main.JavaPlugin, 100L);
  }
  
  public static void ShapeBorder(final World world) {
    (new BukkitRunnable() {
        public void run() {
          String serverType = Bukkit.getServer().getName();
          if ("arclight".equalsIgnoreCase(serverType)) {
            FirstBorderShaped.setBorder(world);
          } else if (Variable.hook_FastAsyncWorldEdit && Main.JavaPlugin.getConfig().getBoolean("FaweSwitch")) {
            FaweAPI.getTaskManager().async(new Runnable() {
                  public void run() {
                    Home home = HomeAPI.getHome(String.valueOf(Variable.world_prefix) + world.getName());
                    int radius = home.getLevel() * Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 + 4;
                    AsyncWorld aw = AsyncWorld.wrap(world);
                    List<Location> list = new ArrayList<>();
                    if (Main.JavaPlugin.getConfig().getString("BorderShape").equalsIgnoreCase("Circle")) {
                      list = FirstBorderShaped.traverseSphere(world, (int)world.getSpawnLocation().getX(), (int)world.getSpawnLocation().getY(), (int)world.getSpawnLocation().getZ(), radius);
                    } else if (Main.JavaPlugin.getConfig().getString("BorderShape").equalsIgnoreCase("Square")) {
                      list = FirstBorderShaped.traverseCube(world, (int)world.getSpawnLocation().getX(), (int)world.getSpawnLocation().getY(), (int)world.getSpawnLocation().getZ(), radius);
                    } 
                    for (Location loc : list) {
                      Block block = world.getBlockAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
                      block.setType(Material.valueOf(Main.JavaPlugin.getConfig().getString("BorderMaterial")));
                    } 
                  }
                });
          } 
        }
      }).runTaskLater((Plugin)Main.JavaPlugin, 100L);
  }
  
  private static void clearAndSetBorder(World world) {
    resetBorder(world);
    setBorder(world);
  }
  
  private static void resetBorder(World world) {
    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "worldborder center " + world.getSpawnLocation().getX() + " " + world.getSpawnLocation().getZ());
    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "worldborder set 60000000");
  }
  
  private static void setBorder(World world) {
    Home home = HomeAPI.getHome(String.valueOf(Variable.world_prefix) + world.getName());
    int radius = home.getLevel() * Main.JavaPlugin.getConfig().getInt("UpdateRadius") / 2 + 4;
    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "worldborder center " + world.getSpawnLocation().getX() + " " + world.getSpawnLocation().getZ());
    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "worldborder set " + (radius * 2));
  }
  
  public static List<Location> traverseSphere(World world, int centerX, int centerY, int centerZ, int radius) {
    List<Location> coordinates = new ArrayList<>();
    for (int x = centerX - radius; x <= centerX + radius; x++) {
      for (int z = centerZ - radius; z <= centerZ + radius; z++) {
        for (int y = centerY - radius; y <= centerY + radius; y++) {
          if (isInsideSphere(x, y, z, centerX, centerY, centerZ, radius) && !isInsideSphere(x, y, z, centerX, centerY, centerZ, radius - 5))
            coordinates.add(new Location(world, x, y, z)); 
        } 
      } 
    } 
    return coordinates;
  }
  
  private static boolean isInsideSphere(int x, int y, int z, int centerX, int centerY, int centerZ, int radius) {
    int dx = x - centerX;
    int dy = y - centerY;
    int dz = z - centerZ;
    return (dx * dx + dy * dy + dz * dz <= radius * radius);
  }
  
  public static List<Location> traverseCube(World world, int centerX, int centerY, int centerZ, int radius) {
    List<Location> coordinates = new ArrayList<>();
    for (int x = centerX - radius; x <= centerX + radius; x++) {
      for (int z = centerZ - radius; z <= centerZ + radius; z++) {
        coordinates.add(new Location(world, x, (centerY - radius), z));
        coordinates.add(new Location(world, x, (centerY + radius), z));
      } 
    } 
    int y;
    for (y = centerY - radius; y <= centerY + radius; y++) {
      for (int z = centerZ - radius; z <= centerZ + radius; z++) {
        coordinates.add(new Location(world, (centerX - radius), y, z));
        coordinates.add(new Location(world, (centerX + radius), y, z));
      } 
    } 
    for (y = centerY - radius; y <= centerY + radius; y++) {
      for (int i = centerX - radius; i <= centerX + radius; i++) {
        coordinates.add(new Location(world, i, y, (centerZ - radius)));
        coordinates.add(new Location(world, i, y, (centerZ + radius)));
      } 
    } 
    return coordinates;
  }
}
