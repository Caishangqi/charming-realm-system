package com.caizii.charmrealm.listeners.plugin;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.events.PluginReloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginReloadListener implements Listener {
    @EventHandler
    public void onPluginReload(PluginReloadEvent pluginReloadEvent) {
        CharmRealm.init();
        CharmRealm.charmGuiHandler.cleanCache(true, null);
    }
}
