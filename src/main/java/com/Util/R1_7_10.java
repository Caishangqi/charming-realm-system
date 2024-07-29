package com.Util;

import com.SelfHome.Main;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class R1_7_10 extends Main {
  public static long getID(World world) {
    CraftWorld e = (CraftWorld)world;
    return (e.getHandle()).dimension;
  }
  
  public static double getTps() {
    double[] list = (MinecraftServer.getServer()).recentTps;
    return list[0];
  }
  
  public static String getName(Entity entity) {
    String CustomName = ((CraftEntity)entity).getHandle().getName();
    if (CustomName.toUpperCase().contains("HYDRA"))
      CustomName = "Hydra"; 
    if (CustomName.contains(" "))
      CustomName = CustomName.replace(" ", "_"); 
    if (CustomName.contains("'"))
      CustomName = CustomName.replace("'", "_"); 
    return CustomName.toUpperCase();
  }
}
