package com.caizii.charmrealm.library;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;

public final class RealmVisualLibrary {

    public static void updatePlayerRealmDisplayName(String playerName) {

        Player player = Bukkit.getPlayer(playerName); // not suitable use in asynchronous because it can be null
        String parsedRealmDisplayName = Color.parseColorAndPlaceholder(player, (CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomePrefix") + player.getName() + CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomeSuffix")));

        Bukkit.getScheduler().runTaskAsynchronously(CharmRealm.JavaPlugin, () -> {
            File playerRealmConfigFile = new File(CharmRealm.pluginVariable.Tempf, playerName + ".yml");
            if (!playerRealmConfigFile.exists()) {
                Logger.log(true, false, Level.INFO, OperateType.REMOVE, "玩家" + playerName + "没有领域文件, 跳过保存");
                return;
            }
            YamlConfiguration playerRealmConfig = YamlConfiguration.loadConfiguration(playerRealmConfigFile);


            if (!playerRealmConfig.contains("saves.display-name")) {

                Logger.log(false, true, Level.WARNING, OperateType.ADD, MessageFormat.format("玩家 <§a{0}§7> 的领域文件不存在saves.display-name 创建中...", playerName));
                playerRealmConfig.createSection("saves");
                playerRealmConfig.createSection("saves.display-name");
                try {
                    playerRealmConfig.save(playerRealmConfigFile);
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
                }

            }


            if (playerRealmConfig.getString("saves.display-name").equalsIgnoreCase(parsedRealmDisplayName)) {
                return;
            }
            playerRealmConfig.set("saves.display-name", parsedRealmDisplayName);
            try {
                playerRealmConfig.save(playerRealmConfigFile);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
            }

            Logger.log(false, true, Level.WARNING, OperateType.ADD, MessageFormat.format("玩家 <§a{0}§7> 的领域更新中完毕", playerName));
        });


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
        if (!playerRealmConfig.contains("saves.display-name")) {
            Logger.log(false, true, Level.WARNING, OperateType.ADD, MessageFormat.format("玩家 <§a{0}§7> 的领域文件不存在saves.display-name 等待下次更新", playerName));
            playerRealmConfig.createSection("saves");
            playerRealmConfig.createSection("saves.display-name");
            try {
                playerRealmConfig.save(playerRealmConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return getPlayerDefaultRealmDisplayName(playerName);
        } else {
            if (playerRealmConfig.getConfigurationSection("saves").getKeys(false).isEmpty())
                return getPlayerDefaultRealmDisplayName(playerName);
            return playerRealmConfig.getString("saves.display-name");
        }
    }

    public static String getPlayerDefaultRealmDisplayName(String playerName) {
        String outDateColor = CharmRealm.pluginConfigManager.realmConfig.getString("state.out-date.color");
        String outDateTag = CharmRealm.pluginConfigManager.realmConfig.getString("state.out-date.tag");
        return Color.parseColor("§f" + outDateTag + outDateColor + playerName);
    }

    public static <T> ItemStack buildPlayerHead(T identifier) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        OfflinePlayer offlinePlayer = null;
        if (identifier instanceof String) {
            offlinePlayer = getPlayerOrOfflinePlayer((String) identifier);
        } else if (identifier instanceof UUID) {
            offlinePlayer = getPlayerOrOfflinePlayer((UUID) identifier);
        }

        if (offlinePlayer != null) {
            skullMeta.setOwningPlayer(offlinePlayer);
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

    /**
     * paras specific placeholder from the string in specify Context
     *
     * @param Context     The Context Object that possible needed by placeholder
     * @param placeholder Placeholder String
     * @return The parsed String
     */
    public static String parasInternalPlaceholder(Object Context, InternalPlaceholder placeholder, String processString, Player player) {

        switch (placeholder) {
            case REALM_CREATE_DATE:
                FileConfiguration context = (FileConfiguration) Context;
                String createdDate = context.getString("CreatedDate");
                return processString.replace("{" + placeholder.name() + "}", createdDate);
            case REALM_CURRENT_PLAYER:
                World world = Bukkit.getWorld("playerrealms/" + Context);
                if (world != null) {
                    return processString.replace("{" + placeholder.name() + "}", "" + world.getPlayers().size());
                } else {
                    return processString.replace("{" + placeholder.name() + "}", "0");
                }
            case REALM_PERMISSION:
                GroupType playerPermission = RealmPermissionLibrary.getPlayerPermission(player.getName(), (String) Context);
                return processString.replace("{" + placeholder.name() + "}", playerPermission.name());
            default:
                return processString;
        }
    }

    private static OfflinePlayer getPlayerOrOfflinePlayer(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        return (player != null) ? player : Bukkit.getOfflinePlayer(playerName);
    }

    private static OfflinePlayer getPlayerOrOfflinePlayer(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        return (player != null) ? player : Bukkit.getOfflinePlayer(playerUUID);
    }


}
