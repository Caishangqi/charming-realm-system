package com.caizii.charmrealm.library;

import com.caizii.charmrealm.CharmRealm;
import org.bukkit.entity.Player;

import java.io.File;

public final class RealmCreateLibrary {

    public static final String createdFolderName = "playerrealms";
    public static final String templateFolderName = "template";

    private RealmCreateLibrary() {
        throw new IllegalStateException("Utility class");
    }

    public static String getRealmWorldPath(String playerName) {
        if (!playerName.isEmpty()) {
            return CharmRealm.pluginVariable.world_prefix + RealmCreateLibrary.createdFolderName + "/" + playerName;
        } else {
            throw new RuntimeException("playerName is empty");
        }
    }

    public static String getRealmWorldPath(Player player) {
        if (player != null && !player.getName().isEmpty()) {
            return CharmRealm.pluginVariable.world_prefix + RealmCreateLibrary.createdFolderName + "/" + player.getName();
        } else {
            throw new RuntimeException("playerName is empty");
        }
    }

    public static String getRealmYMLFileName(String worldName) {
        String key = "playerrealms/";
        int startIndex = worldName.indexOf(key);

        if (worldName.isEmpty())
            throw new RuntimeException("worldName is empty");

        if (startIndex != -1) {
            // + key.length() 表示从 "playerrealms/" 后面的字符开始截取
            return worldName.substring(startIndex + key.length());
        } else {
            return worldName;
        }
    }

    public static File getPlayerRealmYMLFile(Player player) {
        File playerRealmConfigFile = new File(CharmRealm.pluginVariable.Tempf, player.getName() + ".yml");
        return playerRealmConfigFile.exists() ? playerRealmConfigFile : null;
    }

    public static boolean IsPlayerHasRealm(Player player) {
        boolean realmConfigValid = false;
        realmConfigValid = getPlayerRealmYMLFile(player) != null;
        boolean realmWorldFileValid = false;

        File worldFile = new File(CharmRealm.pluginVariable.single_server_gen + CharmRealm.pluginVariable.world_prefix);
        File createdWorldPath = new File(worldFile.getPath() + "/" + RealmCreateLibrary.createdFolderName + "/" + player.getName());
        if (createdWorldPath.exists()) {
            realmWorldFileValid = true;
        }

        return realmConfigValid && realmWorldFileValid;

    }


}
