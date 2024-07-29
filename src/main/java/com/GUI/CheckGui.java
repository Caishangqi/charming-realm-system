package com.GUI;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.MySQL;
import de.tr7zw.nbtapi.NBTItem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckGui implements InventoryHolder {
  public Inventory MainGui = Bukkit.createInventory(this, 45, Variable.GUI_YML.getString("CheckTitle"));
  
  public CheckGui(final Player p) {
    (new BukkitRunnable() {
        public void run() {
          CheckGui.this.MainGui.clear();
          if (Variable.GUI_YML.getBoolean("EnableCheckGuiNormalPane")) {
            try {
              ItemStack itemStack = new ItemStack(Material.valueOf(Variable.GUI_YML.getString("PaneMaterial")));
            } catch (Exception e) {
              String temp5 = Variable.Lang_YML.getString("GlassPaneNotFound");
              if (temp5.contains("<Material>"))
                temp5 = temp5.replace("<Material>", Variable.GUI_YML.getString("PaneMaterial")); 
              Bukkit.getConsoleSender().sendMessage(temp5);
              return;
            } 
            ItemStack blb1 = new ItemStack(Material.valueOf(Variable.GUI_YML.getString("PaneMaterial")));
            blb1.setDurability((short)15);
            ItemMeta i1 = blb1.getItemMeta();
            i1.setDisplayName("");
            blb1.setItemMeta(i1);
            int i;
            for (i = 0; i < 9; i++)
              CheckGui.this.MainGui.setItem(i, blb1); 
            CheckGui.this.MainGui.setItem(9, blb1);
            CheckGui.this.MainGui.setItem(18, blb1);
            CheckGui.this.MainGui.setItem(27, blb1);
            CheckGui.this.MainGui.setItem(17, blb1);
            CheckGui.this.MainGui.setItem(26, blb1);
            CheckGui.this.MainGui.setItem(35, blb1);
            for (i = 36; i < 45; i++) {
              if (i != 40)
                CheckGui.this.MainGui.setItem(i, blb1); 
            } 
          } 
          ConfigurationSection cs = Variable.GUI_YML.getConfigurationSection("");
          for (String temp : cs.getKeys(false)) {
            if (Variable.GUI_YML.getString(String.valueOf(temp) + ".InMenu") == null)
              continue; 
            if (!Variable.GUI_YML.getString(String.valueOf(temp) + ".InMenu").equalsIgnoreCase("Check"))
              continue; 
            try {
              ItemStack itemStack = new ItemStack(Material.valueOf(Variable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            } catch (Exception e) {
              String temp5 = Variable.Lang_YML.getString("MaterialNotFound");
              if (temp5.contains("<Material>"))
                temp5 = temp5.replace("<Material>", Variable.GUI_YML.getString(String.valueOf(temp) + ".Material")); 
              if (temp5.contains("<ID>"))
                temp5 = temp5.replace("<ID>", temp); 
              Bukkit.getConsoleSender().sendMessage(temp5);
              return;
            } 
            ItemStack item = new ItemStack(Material.valueOf(Variable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            if (Variable.GUI_YML.getInt(String.valueOf(temp) + ".SubID") != 0)
              item.setDurability((short)Variable.GUI_YML.getInt(String.valueOf(temp) + ".SubID")); 
            ItemMeta meta = item.getItemMeta();
            List<String> lores = new ArrayList<>();
            meta.setDisplayName(Variable.GUI_YML.getString(String.valueOf(temp) + ".CustomName"));
            for (int i = 0; i < Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").size(); i++) {
              String tempstr = Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").get(i);
              try {
                tempstr = PlaceholderAPI.setPlaceholders(p, tempstr);
              } catch (Exception exception) {}
              lores.add(tempstr);
            } 
            for (int c = 0; c < Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").size(); c++) {
              String[] tempenc = ((String)Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").get(c)).split(",");
              meta.addEnchant(Enchantment.getByName(tempenc[0]), Integer.valueOf(tempenc[1]).intValue(), true);
            } 
            meta.setLore(lores);
            item.setItemMeta(meta);
            if (Variable.GUI_YML.contains(String.valueOf(temp) + ".CustomModelData")) {
              NBTItem nbtItem = new NBTItem(item);
              nbtItem.setInteger("CustomModelData", Integer.valueOf(Variable.GUI_YML.getInt(String.valueOf(temp) + ".CustomModelData")));
              item = nbtItem.getItem();
            } 
            CheckGui.this.MainGui.setItem(Variable.GUI_YML.getInt(String.valueOf(temp) + ".Index") - 1, item);
          } 
          if (Variable.bungee) {
            for (String worldname : MySQL.getAllWorlds()) {
              int amount = 0;
              boolean check_has = false;
              String want_to = worldname;
              if (want_to.equalsIgnoreCase(p.getName()))
                check_has = true; 
              if (!check_has)
                for (int i = 0; i < MySQL.getMembers(worldname).size(); i++) {
                  String temp_str = MySQL.getMembers(worldname).get(i);
                  if (temp_str.equalsIgnoreCase(p.getName()) || temp_str.equals("*")) {
                    check_has = true;
                    break;
                  } 
                }  
              if (!check_has)
                for (int i = 0; i < MySQL.getOP(worldname).size(); i++) {
                  String temp_str = MySQL.getOP(worldname).get(i);
                  if (temp_str.equalsIgnoreCase(p.getName()) || temp_str.equals("*")) {
                    check_has = true;
                    break;
                  } 
                }  
              if (!check_has)
                continue; 
              if (Variable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("HEAD") || 
                Variable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("SKULL")) {
                try {
                  ItemStack itemStack = new ItemStack(
                      Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")), 1, 
                      (short)SkullType.PLAYER.ordinal());
                } catch (Exception e) {
                  String temp5 = Variable.Lang_YML.getString("PlayerHeadMaterialNotFound");
                  if (temp5.contains("<Material>"))
                    temp5 = temp5.replace("<Material>", Variable.GUI_YML.getString("HeadMaterial")); 
                  Bukkit.getConsoleSender().sendMessage(temp5);
                  return;
                } 
                ItemStack skull = new ItemStack(
                    Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")), 1, 
                    (short)SkullType.PLAYER.ordinal());
                SkullMeta player_SKULL = (SkullMeta)skull.getItemMeta();
                if (Variable.GUI_YML.getBoolean("EnableSkullSkin")) {
                  Player temp_p = Bukkit.getPlayer(want_to);
                  if (temp_p != null)
                    try {
                      player_SKULL.setOwningPlayer((OfflinePlayer)temp_p);
                    } catch (Exception exception) {} 
                } 
                player_SKULL.setDisplayName(String.valueOf(String.valueOf(Variable.Lang_YML.getString("CheckGuiHomePrefix"))) + want_to + 
                    Variable.Lang_YML.getString("CheckGuiHomeSuffix"));
                skull.setItemMeta((ItemMeta)player_SKULL);
                CheckGui.this.MainGui.addItem(new ItemStack[] { skull });
              } else {
                ItemStack item = new ItemStack(
                    Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")));
                ItemMeta i_meta = item.getItemMeta();
                i_meta.setDisplayName(String.valueOf(String.valueOf(Variable.Lang_YML.getString("CheckGuiHomePrefix"))) + want_to + 
                    Variable.Lang_YML.getString("CheckGuiHomeSuffix"));
                item.setItemMeta(i_meta);
                CheckGui.this.MainGui.addItem(new ItemStack[] { item });
              } 
              if (amount <= 21) {
                amount++;
                continue;
              } 
              break;
            } 
          } else {
            int amount = 0;
            File folder = new File(Variable.Tempf);
            File[] arrayOfFile;
            for (int j = (arrayOfFile = folder.listFiles()).length, b = 0; b < j; b++) {
              File temp = arrayOfFile[b];
              boolean check_has = false;
              String want_to = temp.getPath().replace(Variable.Tempf, "").replace(".yml", "")
                .replace(Variable.file_loc_prefix, "");
              YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(temp);
              int i;
              for (i = 0; i < yamlConfiguration.getStringList("Members").size(); i++) {
                String temp_str = yamlConfiguration.getStringList("Members").get(i);
                if (temp_str.equalsIgnoreCase(p.getName()) || temp_str.equals("*")) {
                  check_has = true;
                  break;
                } 
              } 
              if (!check_has)
                for (i = 0; i < yamlConfiguration.getStringList("OP").size(); i++) {
                  String temp_str = yamlConfiguration.getStringList("OP").get(i);
                  if (temp_str.equalsIgnoreCase(p.getName()) || temp_str.equals("*")) {
                    check_has = true;
                    break;
                  } 
                }  
              if (check_has) {
                if (Variable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("HEAD") || 
                  Variable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("SKULL")) {
                  try {
                    ItemStack itemStack = new ItemStack(
                        Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")), 1, 
                        (short)SkullType.PLAYER.ordinal());
                  } catch (Exception e) {
                    String temp5 = Variable.Lang_YML.getString("PlayerHeadMaterialNotFound");
                    if (temp5.contains("<Material>"))
                      temp5 = temp5.replace("<Material>", Variable.GUI_YML.getString("HeadMaterial")); 
                    Bukkit.getConsoleSender().sendMessage(temp5);
                    return;
                  } 
                  ItemStack skull = new ItemStack(
                      Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")), 1, 
                      (short)SkullType.PLAYER.ordinal());
                  SkullMeta player_SKULL = (SkullMeta)skull.getItemMeta();
                  if (Variable.GUI_YML.getBoolean("EnableSkullSkin")) {
                    Player temp_p = Bukkit.getPlayer(want_to);
                    if (temp_p != null)
                      try {
                        player_SKULL.setOwningPlayer((OfflinePlayer)temp_p);
                      } catch (Exception exception) {} 
                  } 
                  player_SKULL.setDisplayName(String.valueOf(String.valueOf(Variable.Lang_YML.getString("CheckGuiHomePrefix"))) + want_to + 
                      Variable.Lang_YML.getString("CheckGuiHomeSuffix"));
                  skull.setItemMeta((ItemMeta)player_SKULL);
                  CheckGui.this.MainGui.addItem(new ItemStack[] { skull });
                } else {
                  ItemStack item = new ItemStack(
                      Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")));
                  ItemMeta i_meta = item.getItemMeta();
                  i_meta.setDisplayName(String.valueOf(String.valueOf(Variable.Lang_YML.getString("CheckGuiHomePrefix"))) + want_to + 
                      Variable.Lang_YML.getString("CheckGuiHomeSuffix"));
                  item.setItemMeta(i_meta);
                  CheckGui.this.MainGui.addItem(new ItemStack[] { item });
                } 
                if (amount <= 21) {
                  amount++;
                } else {
                  break;
                } 
              } 
            } 
          } 
        }
      }).runTaskAsynchronously((Plugin)Main.JavaPlugin);
  }
  
  public Inventory getInventory() {
    return this.MainGui;
  }
}
