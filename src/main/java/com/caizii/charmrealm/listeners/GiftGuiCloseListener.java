package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.GiftGui;
import com.caizii.charmrealm.utils.Home;
import com.caizii.charmrealm.utils.HomeAPI;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GiftGuiCloseListener implements Listener {
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onClose(final InventoryCloseEvent event) throws IOException {
    if (!(event.getInventory().getHolder() instanceof GiftGui))
      return; 
    (new BukkitRunnable() {
        public void run() {
          String home_name = "";
          for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)CharmRealm.pluginVariable.has_open_gifts_list.entrySet()) {
            if (((String)entry.getValue()).equalsIgnoreCase(event.getPlayer().getName())) {
              home_name = entry.getKey();
              CharmRealm.pluginVariable.has_open_gifts_list.remove(entry.getKey());
              break;
            } 
          } 
          StreamSerializer ss = new StreamSerializer();
          int amount = 0;
          Home home = HomeAPI.getHome(home_name);
          List<String> gifts = new ArrayList<>();
          ItemStack[] arrayOfItemStack;
          for (int j = (arrayOfItemStack = event.getInventory().getContents()).length, b = 0; b < j; ) {
            ItemStack i = arrayOfItemStack[b];
            if (i != null)
              if (i.getType() != Material.AIR) {
                amount++;
                if (amount > 45)
                  break; 
                String str = null;
                try {
                  str = ss.serializeItemStack(i);
                } catch (IOException e) {
                  e.printStackTrace();
                } 
                gifts.add(str);
              }  
            b++;
          } 
          try {
            home.setGifts(gifts);
          } catch (IOException e) {
            e.printStackTrace();
          } 
        }
      }).runTaskAsynchronously((Plugin) CharmRealm.JavaPlugin);
  }
}
