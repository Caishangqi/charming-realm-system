package com.caizii.charmrealm.task;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.library.GroupType;
import com.caizii.charmrealm.utils.Color;
import com.caizii.charmrealm.utils.FirstBorderShaped;
import com.caizii.charmrealm.utils.Util;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static com.caizii.charmrealm.library.RealmConfigLibrary.getTemplatePath;
import static com.caizii.charmrealm.library.RealmCreateLibrary.createdFolderName;

@Getter
@Setter
public class RealmCreateTask implements Runnable {

    private UUID taskUUID;
    private UUID playerUUID;
    private String targetWorldFilePath;
    private String worldTemplate;

    private File worldFile;
    private String playerName;
    private World createdWorld;


    public RealmCreateTask(UUID playerUUID, String worldTemplate) {
        this.playerUUID = playerUUID;
        this.worldTemplate = worldTemplate;
        taskUUID = UUID.randomUUID();

    }

    private void generateWorldFile() {

        // TODO: pack into method
        String worldTemplatePath = getTemplatePath(worldTemplate);
        //System.out.println(worldTemplatePath);
        worldFile = new File(String.valueOf(CharmRealm.pluginVariable.single_server_gen) + CharmRealm.pluginVariable.world_prefix);
        String createdWorldPath = worldFile.getPath() + "/" + createdFolderName + "/" + playerName;

        File exist_template_file = new File(worldTemplatePath);
        if (!exist_template_file.exists()) {
            String temp = CharmRealm.pluginVariable.Lang_YML.getString("WorldFileNotExist");
            if (temp.contains("<name>"))
                temp = temp.replace("<name>", worldTemplate);
            Bukkit.getPlayer(playerUUID).sendMessage(temp);
            terminateTask();
        }

        Util.copyDir(worldTemplatePath, createdWorldPath);
        WorldCreator creator = new WorldCreator(String.valueOf(CharmRealm.pluginVariable.world_prefix) + createdFolderName + "/" + playerName);
        if (CharmRealm.JavaPlugin.getConfig().getBoolean("generateStructures")) {
            creator.generateStructures(true);
        } else {
            creator.generateStructures(false);
        }

        CharmRealm.pluginVariable.create_list_home.add(String.valueOf(CharmRealm.pluginVariable.world_prefix) + playerName);

        // 计时锁, 默认分配 1 主线程创建完毕世界后让锁打开
        // 继续执行该线程的后续操作
        final CountDownLatch latch = new CountDownLatch(1);

        Bukkit.getScheduler().runTask(CharmRealm.getInstance(), () -> {

            Bukkit.broadcastMessage(CharmRealm.pluginConfigManager.languageConfig.getString("message.realm.create.PerformanceWarningServer"));
            createdWorld = Bukkit.createWorld(creator);

            if (createdWorld != null) {
                String message = MessageFormat.format("              §8(§a+§8) §7世界 <§a{0}§7> 已成功创建!", createdWorld.getName());
                Bukkit.getConsoleSender().sendMessage(message);
                latch.countDown();  // 世界创建成功，继续执行任务
            } else {
                String errorMessage = "              §8(§c-§8) §7世界创建失败!";
                Bukkit.getConsoleSender().sendMessage(errorMessage);
                Thread.currentThread().interrupt();
            }
        });

        try {
            latch.await();  // 等待世界创建完成
            setWorldGameRule();
            syncRealmConfig();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        playerName = Bukkit.getOfflinePlayer(playerUUID).getName();

        String string = MessageFormat.format("§8[§6CharmRealms§8] §8(§a+§8) §7执行任务 <§a{0}§7> §7创建者 <§a{1}§7>", taskUUID, playerName);
        Bukkit.getConsoleSender().sendMessage(string);

        generateWorldFile();
    }

    public void setWorldGameRule() {
        createdWorld.setGameRule(GameRule.DO_MOB_SPAWNING, true);
        createdWorld.setGameRule(GameRule.KEEP_INVENTORY, true);
        createdWorld.setGameRule(GameRule.MOB_GRIEFING, false);
        createdWorld.setGameRule(GameRule.DO_FIRE_TICK, false);
        createdWorld.setDifficulty(Difficulty.HARD);

        String message = MessageFormat.format("              §8(§a+§8) §7世界 <§a{0}§7> 已设置默认 GameRule", createdWorld.getName());
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public void syncRealmConfig() {
        File playerRealmConfigFile = new File(CharmRealm.pluginVariable.Tempf, playerName + ".yml");
        if (!playerRealmConfigFile.exists()) {
            try {
                playerRealmConfigFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            YamlConfiguration playerRealmConfig = YamlConfiguration.loadConfiguration(playerRealmConfigFile);
            // Add playerOwnerUUID section
            playerRealmConfig.createSection("playerOwnerUUID");
            playerRealmConfig.createSection("CreatedDate");
            playerRealmConfig.createSection("Members");
            playerRealmConfig.createSection("OP");
            playerRealmConfig.createSection("Denys");
            playerRealmConfig.createSection("Public");
            playerRealmConfig.createSection("Level");
            playerRealmConfig.createSection("pvp");
            playerRealmConfig.createSection("pickup");
            playerRealmConfig.createSection("drop");
            playerRealmConfig.createSection("Server");
            playerRealmConfig.createSection("locktime");
            playerRealmConfig.createSection("lockweather");
            playerRealmConfig.createSection("time");
            playerRealmConfig.createSection("saves");
            playerRealmConfig.createSection("saves.display-name");

            // permission and groups
            playerRealmConfig.createSection("permissions.group");
            for (GroupType groupType : GroupType.values()) {
                playerRealmConfig.createSection("permissions.group" + "." +groupType);
            }

            if (!playerRealmConfig.contains("NowID"))
                playerRealmConfig.set("NowID", Integer.valueOf(0));
            if (!playerRealmConfig.contains("MaxID"))
                playerRealmConfig.set("MaxID", Integer.valueOf(1000));

            try {
                playerRealmConfig.save(CharmRealm.pluginVariable.f_log);
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            // Add playerOwnerUUID section
            Player creator = Bukkit.getPlayer(playerUUID);
            // Get Current Date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月 d号, yyyy年", Locale.CHINESE);
            String formattedDate = currentDate.format(formatter);

            playerRealmConfig.set("playerOwnerUUID", playerUUID.toString());
            playerRealmConfig.set("CreatedDate", formattedDate);
            playerRealmConfig.set("Public", Boolean.valueOf(CharmRealm.JavaPlugin.getConfig().getBoolean("NormalPublic")));
            playerRealmConfig.set("pickup", Boolean.valueOf(CharmRealm.JavaPlugin.getConfig().getBoolean("NormalPVP")));
            playerRealmConfig.set("drop", Boolean.valueOf(CharmRealm.JavaPlugin.getConfig().getBoolean("NormalPickup")));
            playerRealmConfig.set("pvp", Boolean.valueOf(CharmRealm.JavaPlugin.getConfig().getBoolean("NormalDrop")));
            playerRealmConfig.set("locktime", Boolean.valueOf(false));
            playerRealmConfig.set("time", Integer.valueOf(0));
            playerRealmConfig.set("lockweather", Boolean.valueOf(false));

            // save the style of realm name because when player is offline, lp can not fetch player data
            String parsedRealmDisplayName = Color.parseColorAndPlaceholder(creator, (CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomePrefix") + creator.getName() + CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomeSuffix")));
            playerRealmConfig.set("saves.display-name", parsedRealmDisplayName);

            int set_level = 1;
            for (int i = CharmRealm.JavaPlugin.getConfig().getInt("MaxLevel"); i > 0; i--) {
                if (creator.hasPermission("SelfHome.Level." + i) && !creator.isOp()) {
                    set_level = i;
                    break;
                }
            }
            playerRealmConfig.set("Level", Integer.valueOf(set_level));
            playerRealmConfig.set("Server", CharmRealm.JavaPlugin.getConfig().getString("Server"));

            if (CharmRealm.JavaPlugin.getConfig().getInt("MaxSpawnMonstersAmount") != -1)
                createdWorld.setMonsterSpawnLimit(CharmRealm.JavaPlugin.getConfig().getInt("MaxSpawnMonstersAmount"));
            if (CharmRealm.JavaPlugin.getConfig().getInt("MaxSpawnAnimalsAmount") != -1)
                createdWorld.setMonsterSpawnLimit(CharmRealm.JavaPlugin.getConfig().getInt("MaxSpawnAnimalsAmount"));

            playerRealmConfig.createSection("X");
            playerRealmConfig.createSection("Y");
            playerRealmConfig.createSection("Z");
            Location loc = createdWorld.getSpawnLocation();
            playerRealmConfig.set("X", Double.valueOf(loc.getX()));
            playerRealmConfig.set("Y", Double.valueOf(loc.getY()));
            playerRealmConfig.set("Z", Double.valueOf(loc.getZ()));
            playerRealmConfig.createSection("flowers");
            playerRealmConfig.createSection("popularity");
            playerRealmConfig.createSection("gifts");
            playerRealmConfig.createSection("icon");
            playerRealmConfig.createSection("advertisement");
            playerRealmConfig.createSection("limitblock");
            playerRealmConfig.set("flowers", Integer.valueOf(0));
            playerRealmConfig.set("popularity", Integer.valueOf(0));
            playerRealmConfig.set("gifts", new ArrayList());
            playerRealmConfig.set("icon", "");
            playerRealmConfig.set("advertisement", new ArrayList());
            playerRealmConfig.set("limitblock", new ArrayList());
            try {
                playerRealmConfig.save(playerRealmConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FirstBorderShaped.ShapeBorder(createdWorld);
            String message = MessageFormat.format("              §8(§a+§8) §7领域 <§a{0}§7> 的配置文件同步已同步至 §aplayerdata§7 中", createdWorld.getName());
            Bukkit.getConsoleSender().sendMessage(message);
        }
    }

    public void terminateTask() {
        Thread.currentThread().interrupt();
    }


}
