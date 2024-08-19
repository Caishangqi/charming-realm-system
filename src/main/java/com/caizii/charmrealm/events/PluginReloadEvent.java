package com.caizii.charmrealm.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
@Setter
@Getter
public class PluginReloadEvent extends Event implements Cancellable {

    private boolean cancelledFlag = false;

    private static final HandlerList handlers = new HandlerList();

    /**
     * @return
     */
    @Override
    public boolean isCancelled() {
        return cancelledFlag;
    }

    /**
     * @param b
     */
    @Override
    public void setCancelled(boolean b) {
        this.cancelledFlag = b;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return
     */
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
