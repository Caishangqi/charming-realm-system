package com.GUI.factory;

import com.GUI.components.ButtonClickHandler;
import com.GUI.components.CharmGUIBase;
import com.GUI.components.GUIButton;
import de.tr7zw.nbtapi.NBT;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BaseButtonFactory implements ButtonFactory {
    private String displayName;
    private List<String> lore;
    @Getter
    public ButtonClickHandler handler = null;
    private Material material = Material.AIR;
    private int modelID = 0;
    private Sound clickSound = null;
    private double pitch = 0.0;
    private double volume = 0.0;
    private String slotRange = "";


    public BaseButtonFactory(Material material, String displayName, List<String> lore, String slotRange, ButtonClickHandler handler) {
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
        this.slotRange = slotRange;
    }

    public BaseButtonFactory(Material material, int modelID, String displayName, List<String> lore, String slotRange) {
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
        this.modelID = modelID;
        this.slotRange = slotRange;
    }

    public BaseButtonFactory(Material material, int modelID, String displayName, List<String> lore, String slotRange, Sound clickSound, double pitch, double volume) {
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
        this.modelID = modelID;
        this.clickSound = clickSound;
        this.pitch = pitch;
        this.volume = volume;
        this.slotRange = slotRange;
    }

    @Override
    public List<GUIButton> createButton(CharmGUIBase parentGUI) {

        List<GUIButton> buttons = new ArrayList<>();

        for (Integer i : convertRange(this.slotRange)) {
            ItemStack item = new ItemStack(this.material);
            // 注意先修改nbt在修改meta,不要修改一半meta在修改nbt
            NBT.modify(item, nbt -> {
                nbt.setInteger("CustomModelData", this.modelID);
                return item;
            });
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                if (displayName != null) {
                    meta.setDisplayName(displayName);
                }
                if (lore != null && !lore.isEmpty()) {
                    meta.setLore(lore);
                }
                item.setItemMeta(meta);
            }
            GUIButton button = new GUIButton(parentGUI, item, i) {

            };
            button.setMaterial(this.material);
            button.setModelID(this.modelID);
            button.setClickSound(clickSound);
            button.setPitch(pitch);
            button.setVolume(volume);
            buttons.add(button);
        }

        return buttons;


    }

    public List<Integer> convertRange(String range) {
        if (range.contains("-")) {
            String[] parts = range.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid range: " + range);
            }

            try {
                int start = Integer.parseInt(parts[0]);
                int end = Integer.parseInt(parts[1]);
                List<Integer> result = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    result.add(i);
                }
                return result;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number in range: " + range, e);
            }
        } else {
            try {
                int number = Integer.parseInt(range);
                List<Integer> result = new ArrayList<>();
                result.add(number);
                return result;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number: " + range, e);
            }
        }
    }

}
