package com.caizii.charmrealm.gui.components;

import com.caizii.charmrealm.gui.types.EButtonType;
import com.caizii.charmrealm.utils.Color;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class GUIButton {

    @Getter
    protected final CharmGUIBase parentGUI;
    @Getter
    @Setter
    public ItemStack item;
    @Getter
    @Setter
    protected int slotIndex;
    @Getter
    @Setter
    protected ButtonClickHandler handler;
    @Setter
    public Material material = Material.AIR;
    @Setter
    public int modelID = 0;
    @Setter
    public Sound clickSound = null;
    @Setter
    public double pitch = 0.0;
    @Setter
    public double volume = 0.0;
    @Setter
    public String slotRange = "";
    @Getter
    @Setter
    public EButtonType buttonType = EButtonType.DEFAULT;
    @Getter
    @Setter
    public HashMap<String, String> internalData = new HashMap<>();
    @Getter
    @Setter
    protected ConfigurationSection buttonConfig;

    public GUIButton(CharmGUIBase parentGUI, ItemStack item, int slotIndex) {
        this.parentGUI = parentGUI;
        this.item = item;
        this.slotIndex = slotIndex;
    }


    public boolean onButtonDisplay(Player player) {
        return player.isValid();
    }

    /*
        Rend the button internal ItemStack with papi and color code
        @Note You need pass player to make some plugin's papi work

        this method will parsed papi and color code and put the new
        ItemStack into bukkit inventory

     */
    public void rendButton(Player player) {

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String colorParsedDisplayName = Color.parseColor(meta.getDisplayName());
            String papiParsedDisplayName = PlaceholderAPI.setPlaceholders(player, colorParsedDisplayName);
            meta.setDisplayName(papiParsedDisplayName);

            if (meta.getLore() != null) {
                List<String> colorParsedLore = Color.parseColor(meta.getLore());
                List<String> papiParsedLore = new ArrayList<>();
                for (String lore : colorParsedLore) {
                    papiParsedLore.add(PlaceholderAPI.setPlaceholders(player, lore));
                }
                meta.setLore(papiParsedLore);
            }

            item.setItemMeta(meta);
            parentGUI.getInventory().setItem(this.slotIndex, item);
        }
    }


    public void onClick(InventoryClickEvent event) {
        if (handler != null) {
            // 如果將聲音處理放在這裏則lambda表達式會覆蓋下面聲音
            // 代碼
            handler.onClick(event);
            // Handle basic button click logic, sounds etc
            Player player = Bukkit.getPlayer(event.getWhoClicked().getUniqueId());
            player.playSound(player.getLocation(), this.clickSound, (float) pitch, (float) volume);
            // Broadcast to the parent GUI
            this.getParentGUI().onButtonClicked(this);
        }
    }

    public String getButtonName() {
        if (item == null)
            throw new RuntimeException("can not set button lore because button.item is null");

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            return itemMeta.getDisplayName();
        } else {
            throw new RuntimeException("can not set button lore because no item or meta is set");
        }
    }

    public void setButtonName(String newButtonName) {
        if (item == null)
            throw new RuntimeException("can not set button lore because button.item is null");

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(newButtonName);
            item.setItemMeta(itemMeta);
        } else {
            throw new RuntimeException("can not set button lore because no item or meta is set");
        }

    }

    public List<String> getButtonLore() {
        if (item != null && item.getItemMeta() != null) {
            return item.getItemMeta().getLore();
        } else {
            throw new RuntimeException("can not get button lore because no item or meta is set");
        }
    }

    public String getButtonLore(int loreIndex) {
        try {
            return getButtonLore().get(loreIndex);
        } catch (IndexOutOfBoundsException exception) {
            throw new RuntimeException("can not get button lore because no lore index found");
        }

    }

    public void setButtonLore(List<String> newLore) {
        if (item == null)
            throw new RuntimeException("can not set button lore because button.item is null");

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(newLore);
            item.setItemMeta(itemMeta);
        } else {
            throw new RuntimeException("can not set button lore because no item or meta is set");
        }

    }

    public void setButtonLore(String newLore, int loreIndex) {
        if (item == null)
            throw new RuntimeException("can not set button lore because button.item is null");

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = item.getItemMeta().getLore();
            lore.set(loreIndex, newLore);
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        } else {
            throw new RuntimeException("can not set button lore because no item or meta is set");
        }
    }
}
