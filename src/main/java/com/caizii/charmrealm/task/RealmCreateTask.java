package com.caizii.charmrealm.task;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.text.MessageFormat;
import java.util.UUID;

@Getter
@Setter
public class RealmCreateTask implements Runnable {

    private UUID taskUUID;
    private UUID playerUUID;
    private String targetWorldFilePath;
    private String worldTemplate;

    public RealmCreateTask(UUID playerUUID, String worldTemplate) {
        this.playerUUID = playerUUID;
        this.worldTemplate = worldTemplate;
        taskUUID = UUID.randomUUID();

    }

    @Override
    public void run() {

        String string = MessageFormat.format("§8[§6CharmRealms§8] §8(§a+§8) §7执行任务 <§a{0}§7> §7创建者 <§a{1}§7>", taskUUID, Bukkit.getOfflinePlayer(playerUUID).getName());
        Bukkit.getConsoleSender().sendMessage(string);

    }
}
