package com.caizii.charmrealm.gui;

import com.caizii.charmrealm.CharmRealm;
import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ManageGui4 implements InventoryHolder {
  public Inventory MainGui = Bukkit.createInventory(this, 45, CharmRealm.pluginVariable.GUI_YML.getString("Manage4Title"));
  
  public ManageGui4(final Player p) {
    (new BukkitRunnable() {
        public void run() {
          ManageGui4.this.MainGui.clear();
          if (CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableManageGui4NormalPane")) {
            try {
              ItemStack itemStack = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString("PaneMaterial")));
            } catch (Exception e) {
              String temp5 = CharmRealm.pluginVariable.Lang_YML.getString("GlassPaneNotFound");
              if (temp5.contains("<Material>"))
                temp5 = temp5.replace("<Material>", CharmRealm.pluginVariable.GUI_YML.getString("PaneMaterial")); 
              Bukkit.getConsoleSender().sendMessage(temp5);
              return;
            } 
            ItemStack blb1 = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString("PaneMaterial")));
            blb1.setDurability((short)15);
            ItemMeta i1 = blb1.getItemMeta();
            i1.setDisplayName("");
            blb1.setItemMeta(i1);
            int i;
            for (i = 0; i < 9; i++)
              ManageGui4.this.MainGui.setItem(i, blb1); 
            ManageGui4.this.MainGui.setItem(9, blb1);
            ManageGui4.this.MainGui.setItem(18, blb1);
            ManageGui4.this.MainGui.setItem(27, blb1);
            ManageGui4.this.MainGui.setItem(17, blb1);
            ManageGui4.this.MainGui.setItem(26, blb1);
            ManageGui4.this.MainGui.setItem(35, blb1);
            for (i = 36; i < 45; i++) {
              if (i != 40)
                ManageGui4.this.MainGui.setItem(i, blb1); 
            } 
          } 
          ConfigurationSection cs = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("");
          for (String temp : cs.getKeys(false)) {
            if (CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".InMenu") == null)
              continue; 
            if (!CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".InMenu").equalsIgnoreCase("Manage4"))
              continue; 
            try {
              ItemStack itemStack = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            } catch (Exception e) {
              String temp5 = CharmRealm.pluginVariable.Lang_YML.getString("MaterialNotFound");
              if (temp5.contains("<Material>"))
                temp5 = temp5.replace("<Material>", CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".Material")); 
              if (temp5.contains("<ID>"))
                temp5 = temp5.replace("<ID>", temp); 
              Bukkit.getConsoleSender().sendMessage(temp5);
              return;
            } 
            ItemStack item = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            if (CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".SubID") != 0)
              item.setDurability((short)CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".SubID")); 
            ItemMeta meta = item.getItemMeta();
            List<String> lores = new ArrayList<>();
            meta.setDisplayName(CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".CustomName"));
            for (int i = 0; i < CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").size(); i++) {
              String tempstr = CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").get(i);
              try {
                tempstr = PlaceholderAPI.setPlaceholders(p, tempstr);
              } catch (Exception exception) {}
              lores.add(tempstr);
            } 
            for (int c = 0; c < CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").size(); c++) {
              String[] tempenc = ((String)CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").get(c)).split(",");
              meta.addEnchant(Enchantment.getByName(tempenc[0]), Integer.valueOf(tempenc[1]).intValue(), true);
            } 
            meta.setLore(lores);
            item.setItemMeta(meta);
            if (CharmRealm.pluginVariable.GUI_YML.contains(String.valueOf(temp) + ".CustomModelData")) {
              NBTItem nbtItem = new NBTItem(item);
              nbtItem.setInteger("CustomModelData", Integer.valueOf(CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".CustomModelData")));
              item = nbtItem.getItem();
            } 
            ManageGui4.this.MainGui.setItem(CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".Index") - 1, item);
          } 
        }
      }).runTaskAsynchronously((Plugin) CharmRealm.JavaPlugin);
  }
  
  public Inventory getInventory() {
    return this.MainGui;
  }
}
