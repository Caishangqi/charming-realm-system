package com.GUI;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.Home;
import com.Util.HomeAPI;
import com.comphenix.protocol.utility.StreamSerializer;
import de.tr7zw.nbtapi.NBTItem;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GiftGui implements InventoryHolder {
  public Inventory MainGui = Bukkit.createInventory(this, 45, Variable.Lang_YML.getString("GiftGuiTitle"));
  
  public GiftGui(Player p, final String Name) {
    (new BukkitRunnable() {
        public void run() {
          GiftGui.this.MainGui.clear();
          Home home = HomeAPI.getHome(Name);
          List<String> list = home.getGifts();
          if (list == null)
            return; 
          StreamSerializer ss = new StreamSerializer();
          int amount = 0;
          for (String str : list) {
            amount++;
            ItemStack i = null;
            if (str == null)
              continue; 
            if (str.equalsIgnoreCase(""))
              continue; 
            try {
              i = ss.deserializeItemStack(str);
            } catch (IllegalArgumentException e) {
              continue;
            } 
            if (Variable.GUI_YML.contains("CustomModelData")) {
              NBTItem nbtItem = new NBTItem(i);
              nbtItem.setInteger("CustomModelData", Integer.valueOf(Variable.GUI_YML.getInt("CustomModelData")));
              i = nbtItem.getItem();
            } 
            GiftGui.this.MainGui.addItem(new ItemStack[] { i });
            if (amount >= 45)
              break; 
          } 
        }
      }).runTaskAsynchronously((Plugin)Main.JavaPlugin);
  }
  
  public Inventory getInventory() {
    return this.MainGui;
  }
}
