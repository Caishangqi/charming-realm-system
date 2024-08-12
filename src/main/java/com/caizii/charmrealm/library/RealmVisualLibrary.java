package com.caizii.charmrealm.library;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;

public final class RealmVisualLibrary {

    public static void updatePlayerRealmDisplayName(String playerName) {
        File playerRealmConfigFile = new File(CharmRealm.pluginVariable.Tempf, playerName + ".yml");
        if (!playerRealmConfigFile.exists()) {
            throw new RuntimeException("无法找到玩家" + playerName + "的领域文件");
        }
        YamlConfiguration playerRealmConfig = YamlConfiguration.loadConfiguration(playerRealmConfigFile);

        Player player = Bukkit.getPlayer(playerName);

        if (!playerRealmConfig.contains("saves.DisplayName")) {

            Logger.log(false, true, Level.WARNING, OperateType.ADD, MessageFormat.format("玩家 <§a{0}§7> 的领域文件不存在saves.DisplayName 创建中...", playerName));
            playerRealmConfig.createSection("saves");
            playerRealmConfig.createSection("saves.DisplayName");
            try {
                playerRealmConfig.save(playerRealmConfigFile);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
            }

        }

        String parsedRealmDisplayName = Color.parseColorAndPlaceholder(player, (CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomePrefix") + player.getName() + CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomeSuffix")));

        playerRealmConfig.set("saves.DisplayName", parsedRealmDisplayName);
        Logger.log(false, true, Level.WARNING, OperateType.ADD, MessageFormat.format("玩家 <§a{0}§7> 的领域更新中完毕", playerName));
    }

    public static void updatePlayerRealmDisplayName(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        updatePlayerRealmDisplayName(player.getName());
    }

    public static String getPlayerRealmDisplayName(String playerName) {

        File playerRealmConfigFile = new File(CharmRealm.pluginVariable.Tempf, playerName + ".yml");
        if (!playerRealmConfigFile.exists()) {
            throw new RuntimeException("无法找到玩家" + playerName + "的领域文件");
        }
        YamlConfiguration playerRealmConfig = YamlConfiguration.loadConfiguration(playerRealmConfigFile);
        if (!playerRealmConfig.contains("saves.DisplayName")) {
            Logger.log(false, true, Level.WARNING, OperateType.ADD, MessageFormat.format("玩家 <§a{0}§7> 的领域文件不存在saves.DisplayName 等待下次更新", playerName));
            playerRealmConfig.createSection("saves");
            playerRealmConfig.createSection("saves.DisplayName");
            try {
                playerRealmConfig.save(playerRealmConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return getPlayerDefaultRealmDisplayName(playerName);
        } else {
            if (playerRealmConfig.getConfigurationSection("saves.DisplayName").getKeys(false).isEmpty())
                return getPlayerDefaultRealmDisplayName(playerName);
            return playerRealmConfig.getString("saves.DisplayName");
        }
    }

    public static String getPlayerDefaultRealmDisplayName(String playerName) {
        String outDateColor = CharmRealm.pluginConfigManager.realmConfig.getString("state.out-date.color");
        String outDateTag = CharmRealm.pluginConfigManager.realmConfig.getString("state.out-date.tag");
        return Color.parseColor("§f" + outDateTag + outDateColor + playerName);
    }

}
