package com.caizii.charmrealm.listeners.createtask;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.events.RealmCreateEvent;
import com.caizii.charmrealm.events.RealmFinishCreateEvent;
import com.caizii.charmrealm.task.RealmCreateTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.MessageFormat;

public class RealmCreateListener implements Listener {

    @EventHandler
    public void onRealmCreate(RealmCreateEvent realmCreateEvent) {

        Bukkit.getScheduler().runTaskAsynchronously(CharmRealm.getInstance(), new Runnable() {
            @Override
            public void run() {
                Player instigator = realmCreateEvent.getInstigator();
                instigator.sendMessage("§7我們正在生成你的領域, 請稍等片刻...");
                RealmCreateTask realmCreateTask = realmCreateEvent.getRealmCreateTask();
                CharmRealm.realmGeneratorManager.addRealmCreateTask(realmCreateTask.getPlayerUUID(), realmCreateTask);
            }
        });
    }

    @EventHandler
    public void onRealmFinishCreate(RealmFinishCreateEvent realmFinishCreateEvent) {

        Bukkit.getScheduler().runTaskAsynchronously(CharmRealm.getInstance(), new Runnable() {
            @Override
            public void run() {
                RealmCreateTask realmCreateTask = realmFinishCreateEvent.getRealmCreateTask();
                Player player = Bukkit.getPlayer(realmCreateTask.getPlayerUUID());
                if (player != null) {
                    player.sendMessage("§7你的領域已創建完畢,輸入指令 §6/realm h §7來訪問");
                }
                String string = MessageFormat.format("§8[§6CharmRealms§8] §8(§a+§8) §7任务 <§a{0}§7> §7已完成 创建者 §7<§a{1}§7>", realmCreateTask.getTaskUUID(), Bukkit.getOfflinePlayer(realmCreateTask.getPlayerUUID()).getName());
                Bukkit.getConsoleSender().sendMessage(string);
            }
        });
    }
}
