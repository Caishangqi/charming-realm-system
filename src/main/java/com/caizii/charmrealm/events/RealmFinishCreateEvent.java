package com.caizii.charmrealm.events;

import com.caizii.charmrealm.task.RealmCreateTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class RealmFinishCreateEvent extends Event implements Cancellable {

    private RealmCreateTask realmCreateTask;
    private boolean cancelledFlag = false;

    private static final HandlerList handlers = new HandlerList();

    public RealmFinishCreateEvent(RealmCreateTask realmCreateTask) {
        this.realmCreateTask = realmCreateTask;
    }


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
