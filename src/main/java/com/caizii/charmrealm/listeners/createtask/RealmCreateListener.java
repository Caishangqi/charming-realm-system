package com.caizii.charmrealm.listeners.createtask;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.events.RealmCreateEvent;
import com.caizii.charmrealm.events.RealmFinishCreateEvent;
import com.caizii.charmrealm.library.GroupType;
import com.caizii.charmrealm.library.RealmConfigLibrary;
import com.caizii.charmrealm.library.RealmPermissionLibrary;
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
                instigator.sendMessage(RealmConfigLibrary.getLangString("message.realm.create.ProcessRealmCreation"));
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

                    // Set Permission
                    RealmPermissionLibrary.setPlayerPermission(player.getName(), player.getName(), GroupType.OWNER);

                    player.sendMessage(RealmConfigLibrary.getLangString("message.realm.create.FinishRealmCreation"));
                }
                String string = MessageFormat.format("§8[§6CharmRealms§8] §8(§a+§8) §7任务 <§a{0}§7> §7已完成 创建者 §7<§a{1}§7>", realmCreateTask.getTaskUUID(), Bukkit.getOfflinePlayer(realmCreateTask.getPlayerUUID()).getName());
                Bukkit.getConsoleSender().sendMessage(string);
            }
        });
    }
}
