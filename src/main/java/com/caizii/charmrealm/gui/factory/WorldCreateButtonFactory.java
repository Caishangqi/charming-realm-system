package com.caizii.charmrealm.gui.factory;


import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.gui.components.GUIButton;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class WorldCreateButtonFactory extends BaseButtonFactory {
    public WorldCreateButtonFactory(ConfigurationSection buttonConfig) {
        super(buttonConfig);
    }

    /**
     * for world create button, overwrite the event
     * to handle click event for world creation logic!
     */
    @Override
    public void injectEvent(GUIButton button) {
        //System.out.println("正在注入按钮事件");
        button.setHandler(event -> {
        });
    }

    /**
     * @param parentGUI
     * @return
     */
    @Override
    public List<GUIButton> createButton(CharmGUIBase parentGUI) {
        return super.createButton(parentGUI);
    }
}
