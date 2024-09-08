package com.caizii.charmrealm.gui.button;

import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.gui.components.GUIButton;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MetaGUIButton extends GUIButton {


    protected Map<String, String> metaValues = new HashMap<>();

    public MetaGUIButton(CharmGUIBase parentGUI, ItemStack item, int slotIndex) {
        super(parentGUI, item, slotIndex);
    }

    public Map<String, String> getMetaValues() {
        return metaValues;
    }
}
