package com.caizii.charmrealm.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.caizii.charmrealm.library.RealmVisualLibrary.updatePlayerRealmDisplayName;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onLeave(final PlayerQuitEvent event) {
        // Update player realm display name
        updatePlayerRealmDisplayName(event.getPlayer().getName());
    }
}
