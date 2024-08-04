package com.GUI.factory;

import com.GUI.components.ButtonClickHandler;
import com.GUI.components.CharmGUIBase;
import com.GUI.components.GUIButton;
import com.GUI.types.EButtonType;
import de.tr7zw.nbtapi.NBT;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BaseButtonFactory implements ButtonFactory {

    protected String displayName;
    protected List<String> lore;
    protected EButtonType buttonType = EButtonType.DEFAULT;
    @Getter
    public ButtonClickHandler handler = null;
    protected Material material = Material.AIR;
    protected int modelID = 0;
    protected Sound clickSound = null;
    protected double pitch = 0.0;
    protected double volume = 0.0;
    protected String slotRange = "";
    protected ConfigurationSection buttonConfig;

    public BaseButtonFactory(ConfigurationSection buttonConfig) {
        this.buttonConfig = buttonConfig;
        ConfigurationSection buttonItemStackConfig = buttonConfig.getConfigurationSection("item");

        this.material = Material.valueOf(buttonItemStackConfig.getString("material"));
        this.modelID = buttonItemStackConfig.getInt("modelID");
        this.displayName = buttonItemStackConfig.getString("name");
        this.lore = buttonItemStackConfig.getStringList("lore");

        this.slotRange = buttonConfig.getString("slot");
        this.buttonType = EButtonType.valueOf(buttonConfig.getString("type"));
        this.clickSound = Sound.valueOf(buttonConfig.getString("sound"));
        this.pitch = buttonConfig.getDouble("pitch");
        this.volume = buttonConfig.getDouble("volume");
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
            button.setButtonType(this.buttonType);
            button.setMaterial(this.material);
            button.setModelID(this.modelID);
            button.setClickSound(clickSound);
            button.setPitch(pitch);
            button.setVolume(volume);
            // put buttonConfig into button for validation (can optimize)
            button.setButtonConfig(this.buttonConfig);
            // inject the click event to button
            injectEvent(button);

            buttons.add(button);
        }

        return buttons;


    }

    // base button factory will not contain any event
    @Override
    public void injectEvent(GUIButton button) {
        button.setHandler(null);
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
