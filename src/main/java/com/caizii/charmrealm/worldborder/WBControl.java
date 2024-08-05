package com.caizii.charmrealm.worldborder;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Home;
import com.caizii.charmrealm.utils.HomeAPI;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class WBControl {
  public static void setEnable(Player p) {
    if (CharmRealm.pluginVariable.not_adopt_nms)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    String v = Bukkit.getServer().getClass().getPackage().getName();
    v = v.substring(v.lastIndexOf('.') + 1);
    try {
      if (v.contains("v1_12_R1")) {
        //R_12_1.hide(p, p.getWorld());
      } else if (v.contains("v1_16_R1")) {
        //R_16_1.hide(p, p.getWorld());
      } else if (v.contains("v1_16_R2")) {
        //R_16_2.hide(p, p.getWorld());
      } else if (v.contains("v1_16_R3")) {
        R_16_3.hide(p, p.getWorld());
      } else if (v.contains("arclight")) {
        hideArclightBorder(p, p.getWorld());
      } 
    } catch (NoSuchFieldError e) {
      CharmRealm.pluginVariable.not_adopt_nms = true;
    } 
  }
  
  public static void setDisable(Player p) {
    if (CharmRealm.pluginVariable.not_adopt_nms)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    String v = Bukkit.getServer().getClass().getPackage().getName();
    v = v.substring(v.lastIndexOf('.') + 1);
    Home home = HomeAPI.getHome(p.getWorld().getName());
    try {
      if (v.contains("v1_16_R3")) {
        R_16_3.show(p, p.getWorld(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getZ(), (CharmRealm.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * CharmRealm.JavaPlugin.getConfig().getInt("UpdateRadius")));
      } else if (v.contains("arclight")) {
        showArclightBorder(p, p.getWorld(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getZ(), (CharmRealm.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * CharmRealm.JavaPlugin.getConfig().getInt("UpdateRadius")));
      } 
    } catch (NoSuchFieldError e) {
      CharmRealm.pluginVariable.not_adopt_nms = true;
    } 
  }
  
  public static void togglecc(Player p) {
    if (CharmRealm.pluginVariable.not_adopt_nms)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    if (!CharmRealm.pluginVariable.has_already_hide_border.contains(p.getName())) {
      setEnable(p);
      p.sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("ToggleccWorldDisable"));
      CharmRealm.pluginVariable.has_already_hide_border.add(p.getName());
    } else {
      setDisable(p);
      p.sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("ToggleccWorldEnable"));
      CharmRealm.pluginVariable.has_already_hide_border.remove(p.getName());
    } 
  }
  
  private static void showArclightBorder(Player player, World world, double x, double z, double size) {
    WorldBorder border = world.getWorldBorder();
    border.setCenter(x, z);
    border.setSize(size);
    border.setDamageAmount(0.0D);
    border.setWarningDistance(0);
  }
  
  private static void hideArclightBorder(Player player, World world) {
    WorldBorder border = world.getWorldBorder();
    border.reset();
  }
}
