package com.Listeners;

import com.SelfHome.Variable;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiftGuiClickItemListener implements Listener {
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onClick(InventoryClickEvent event) {
    if (!(event.getInventory().getHolder() instanceof com.GUI.GiftGui))
      return; 
    if (event.getClickedInventory() == null)
      return; 
    if (event.getClickedInventory().getType() == InventoryType.PLAYER && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)) {
      event.setCancelled(true);
      event.getWhoClicked().closeInventory();
      event.getWhoClicked().sendMessage(Variable.Lang_YML.getString("WantToPutItemIntoGiftChest"));
      return;
    } 
    if (event.getCurrentItem() == null)
      return; 
    if (event.getCurrentItem().getType() == Material.AIR)
      return; 
    ItemStack i = event.getCurrentItem();
    boolean has_lore = false;
    if (i.hasItemMeta()) {
      ItemMeta meta = i.getItemMeta();
      if (meta.hasLore()) {
        List<String> lores = meta.getLore();
        for (int c = 0; c < lores.size(); c++) {
          if (((String)lores.get(c)).contains(Variable.Lang_YML.getString("GiftLoreAddPrefix"))) {
            lores.remove(c);
            meta.setLore(lores);
            has_lore = true;
            break;
          } 
        } 
      } 
      if (has_lore)
        i.setItemMeta(meta); 
    } 
  }
}
