package com.caizii.charmrealm.events;

import com.caizii.charmrealm.task.RealmCreateTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

@Setter
@Getter
public class RealmCreateEvent extends Event implements Cancellable {

    private RealmCreateTask realmCreateTask;

    private Player instigator;

    private boolean cancelledFlag = false;

    private static final HandlerList handlers = new HandlerList();

    public RealmCreateEvent(RealmCreateTask realmCreateTask,Player instigator) {
        this.realmCreateTask = realmCreateTask;
        this.instigator = instigator;
        String string = MessageFormat.format("§8[§6CharmRealms§8] §8(§a+§8) §7任务 <§a{0}§7> §7已创建 创建者 §7<§a{1}§7>", realmCreateTask.getTaskUUID(), Bukkit.getOfflinePlayer(realmCreateTask.getPlayerUUID()).getName());
        Bukkit.getConsoleSender().sendMessage(string);
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
