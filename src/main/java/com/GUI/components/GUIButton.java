package com.GUI.components;

import com.GUI.types.EButtonType;
import com.Util.Color;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIButton {

    @Getter
    protected final CharmGUIBase parentGUI;
    @Getter
    protected ItemStack item;
    @Getter
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
    protected void rendButton(Player player) {

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
        }
    }
}
