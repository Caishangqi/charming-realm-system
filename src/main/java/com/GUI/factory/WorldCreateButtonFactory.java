package com.GUI.factory;

import org.bukkit.configuration.ConfigurationSection;

public class WorldCreateButtonFactory extends BaseButtonFactory{
    public WorldCreateButtonFactory(ConfigurationSection buttonConfig) {
        super(buttonConfig);
    }

    /**
     *  for world create button, overwrite the event
     *  to handle click event for world creation logic!
     */
    @Override
    public void injectEvent() {
        super.injectEvent();
    }
}
