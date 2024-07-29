package com.Util;

import com.SelfHome.Main;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class R1_12_2 extends Main {
  public static long getID(World world) {
    CraftWorld e = (CraftWorld)world;
    return (e.getHandle()).dimension;
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
