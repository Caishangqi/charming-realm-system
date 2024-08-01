package com.GUI.factory;

import com.GUI.components.ButtonClickHandler;
import com.GUI.components.CustomGUI;
import com.GUI.components.GUIButton;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BaseButtonFactory implements ButtonFactory {
    private final String displayName;
    private final List<String> lore;
    private final ButtonClickHandler handler;
    private final Material material;

    public BaseButtonFactory(Material material, String displayName, List<String> lore, ButtonClickHandler handler) {
        this.displayName = displayName;
        this.lore = lore;
        this.handler = handler;
        this.material = material;
    }

    @Override
    public GUIButton createButton(CustomGUI parentGUI) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (displayName != null) {
                meta.setDisplayName(displayName);
            }
            if (lore != null) {
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
        }

        return new GUIButton(parentGUI, item) {
            @Override
            public void onClick(InventoryClickEvent event) {
                handler.onClick(event);
            }
        };
    }
}
