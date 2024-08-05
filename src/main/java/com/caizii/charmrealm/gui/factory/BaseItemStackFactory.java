package com.caizii.charmrealm.gui.factory;

import de.tr7zw.nbtapi.NBT;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Setter
@Getter
public class BaseItemStackFactory implements ItemStackFactory {

    protected ConfigurationSection itemStackConfig;

    /**
     *
     */
    @Override
    public ItemStack createItemStack() {
        Material material = null;
        try {
            material = Material.valueOf(itemStackConfig.getString("material"));
        } catch (NullPointerException exception) {
            throw new RuntimeException("Invalid material " + itemStackConfig.getString("material"));
        }

        ItemStack itemStack = new ItemStack(material);


        NBT.modify(itemStack, nbt -> {
            nbt.setInteger("CustomModelData", itemStackConfig.getInt("modelID"));
            return itemStack;
        });

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(itemStackConfig.getString("name"));
        itemMeta.setLore(itemStackConfig.getStringList("lore"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public BaseItemStackFactory(ConfigurationSection itemStackConfig) {
        this.itemStackConfig = itemStackConfig;
    }
}
