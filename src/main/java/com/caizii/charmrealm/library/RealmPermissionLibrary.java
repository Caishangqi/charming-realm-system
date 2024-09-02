package com.caizii.charmrealm.library;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.caizii.charmrealm.library.RealmCreateLibrary.getPlayerRealmYMLFile;

public final class RealmPermissionLibrary {
    private RealmPermissionLibrary() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取目标领域的目标玩家权限组
     *
     * @param playerName 目标玩家名称
     * @param realmName  目标领域
     * @return GroupType 返回目标领域中玩家所属的权限组
     */
    public static GroupType getPlayerPermission(String playerName, String realmName) {
        File playerRealmYMLFile = getPlayerRealmYMLFile(realmName);
        if (playerRealmYMLFile.exists()) {
        /*
        permissions:
            group:
                OWNER: {}
                OPERATOR: {}
                MEMBER: {}
                VISITOR: {}
                BANNED: {}
        */
            YamlConfiguration playerRealmYMLFileYaml = YamlConfiguration.loadConfiguration(playerRealmYMLFile);
            ConfigurationSection groupSection = playerRealmYMLFileYaml.getConfigurationSection("permissions.group");

            if (groupSection != null) {
                Set<String> groupKeys = groupSection.getKeys(false);
                for (String groupKey : groupKeys) {
                    List<?> list = playerRealmYMLFileYaml.getList("permissions.group" + "." + groupKey);
                    if (list != null && list.contains(playerName)) {
                        try {
                            return GroupType.valueOf(groupKey);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();  // 记录日志或处理异常
                        }
                    }
                }
            }
        }

        return GroupType.VISITOR; // 默认返回VISITOR组
    }

    /**
     * 设置目标领域的目标玩家权限组
     * @param playerName    目标玩家名称
     * @param realmName     目标领域名称
     * @param type      权限组类型
     * @return      是否设置成功
     */
    public static boolean setPlayerPermission(String playerName, String realmName, GroupType type) {
        File playerRealmYMLFile = getPlayerRealmYMLFile(realmName);
        if (playerRealmYMLFile.exists()) {
            YamlConfiguration playerRealmYMLFileYaml = YamlConfiguration.loadConfiguration(playerRealmYMLFile);
            String path = "permissions.group" + "." + type.name();
            ConfigurationSection configurationSection = playerRealmYMLFileYaml.getConfigurationSection(path);

            if (configurationSection == null) {
                // 如果配置段不存在，创建一个新的
                configurationSection = playerRealmYMLFileYaml.createSection(path);
            }

            // 获取当前组的玩家列表
            List<String> playerList = configurationSection.getStringList("");

            // 如果玩家不在列表中，添加玩家
            if (!playerList.contains(playerName)) {
                playerList.add(playerName);
                playerRealmYMLFileYaml.set(path, playerList);  // 使用完整路径

                try {
                    // 保存更改
                    playerRealmYMLFileYaml.save(playerRealmYMLFile);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


}
