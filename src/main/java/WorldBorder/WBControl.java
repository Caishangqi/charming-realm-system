package WorldBorder;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.Home;
import com.Util.HomeAPI;
import com.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class WBControl {
  public static void setEnable(Player p) {
    if (Variable.not_adopt_nms)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    String v = Bukkit.getServer().getClass().getPackage().getName();
    v = v.substring(v.lastIndexOf('.') + 1);
    try {
      if (v.contains("v1_12_R1")) {
        R_12_1.hide(p, p.getWorld());
      } else if (v.contains("v1_16_R1")) {
        R_16_1.hide(p, p.getWorld());
      } else if (v.contains("v1_16_R2")) {
        R_16_2.hide(p, p.getWorld());
      } else if (v.contains("v1_16_R3")) {
        R_16_3.hide(p, p.getWorld());
      } else if (v.contains("arclight")) {
        hideArclightBorder(p, p.getWorld());
      } 
    } catch (NoSuchFieldError e) {
      Variable.not_adopt_nms = true;
    } 
  }
  
  public static void setDisable(Player p) {
    if (Variable.not_adopt_nms)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    String v = Bukkit.getServer().getClass().getPackage().getName();
    v = v.substring(v.lastIndexOf('.') + 1);
    Home home = HomeAPI.getHome(p.getWorld().getName());
    try {
      if (v.contains("v1_12_R1")) {
        R_12_1.show(p, p.getWorld(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getZ(), (Main.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * Main.JavaPlugin.getConfig().getInt("UpdateRadius")));
      } else if (v.contains("v1_16_R1")) {
        R_16_1.show(p, p.getWorld(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getZ(), (Main.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * Main.JavaPlugin.getConfig().getInt("UpdateRadius")));
      } else if (v.contains("v1_16_R2")) {
        R_16_2.show(p, p.getWorld(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getZ(), (Main.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * Main.JavaPlugin.getConfig().getInt("UpdateRadius")));
      } else if (v.contains("v1_16_R3")) {
        R_16_3.show(p, p.getWorld(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getZ(), (Main.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * Main.JavaPlugin.getConfig().getInt("UpdateRadius")));
      } else if (v.contains("arclight")) {
        showArclightBorder(p, p.getWorld(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getZ(), (Main.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * Main.JavaPlugin.getConfig().getInt("UpdateRadius")));
      } 
    } catch (NoSuchFieldError e) {
      Variable.not_adopt_nms = true;
    } 
  }
  
  public static void togglecc(Player p) {
    if (Variable.not_adopt_nms)
      return; 
    if (!Util.CheckIsHome(p.getWorld().getName()))
      return; 
    if (!Variable.has_already_hide_border.contains(p.getName())) {
      setEnable(p);
      p.sendMessage(Variable.Lang_YML.getString("ToggleccWorldDisable"));
      Variable.has_already_hide_border.add(p.getName());
    } else {
      setDisable(p);
      p.sendMessage(Variable.Lang_YML.getString("ToggleccWorldEnable"));
      Variable.has_already_hide_border.remove(p.getName());
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
