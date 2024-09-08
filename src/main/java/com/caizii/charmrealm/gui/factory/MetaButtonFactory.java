package com.caizii.charmrealm.gui.factory;

import com.caizii.charmrealm.gui.button.MetaGUIButton;
import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.gui.components.GUIButton;
import org.bukkit.configuration.ConfigurationSection;

public class MetaButtonFactory extends BaseButtonFactory{
    public MetaButtonFactory(ConfigurationSection buttonConfig) {
        super(buttonConfig);
    }

    @Override
    public GUIButton createButton(CharmGUIBase parentGUI) {
        GUIButton button = new MetaGUIButton(parentGUI, getButtonContextItemStack(), 0) {

        };
        setButtonContext(button);
        return button;
    }



}
