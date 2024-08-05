package com.caizii.charmrealm.hookers;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.VisitGui;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class VisitGUIHook extends GUIHook {
    public static ItemStack HookNextButtonState(ItemStack Button, VisitGui gui) {

        if (gui.NowPage == gui.MaxPage) {
            ConfigurationSection nextSection = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("NextButton");
            NBTItem nbtNextItem = new NBTItem(Button);
            nbtNextItem.setInteger("CustomModelData", Integer.valueOf(nextSection.getInt("NoFetchCustomModelData")));
            Button = nbtNextItem.getItem();
        }
        return Button;
    }

    public static ItemStack HookPrevButtonState(ItemStack Button, VisitGui gui) {

        if (gui.NowPage == gui.MaxPage) {
            ConfigurationSection nextSection = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("PrevButton");
            NBTItem nbtNextItem = new NBTItem(Button);
            nbtNextItem.setInteger("CustomModelData", Integer.valueOf(nextSection.getInt("NoFetchCustomModelData")));
            Button = nbtNextItem.getItem();

        } else if (gui.NowPage == 0) {
            ConfigurationSection nextSection = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("PrevButton");
            NBTItem nbtNextItem = new NBTItem(Button);
            nbtNextItem.setInteger("CustomModelData", Integer.valueOf(nextSection.getInt("NoFetchCustomModelData")));
            Button = nbtNextItem.getItem();
        }
        return Button;
    }


}
