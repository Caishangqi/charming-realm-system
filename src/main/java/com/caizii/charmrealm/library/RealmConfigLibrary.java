package com.caizii.charmrealm.library;

import com.caizii.charmrealm.CharmRealm;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

import static com.caizii.charmrealm.CharmRealm.JavaPlugin;

public final class RealmConfigLibrary {

    public static String pluginPrefix = "§8[§6CharmRealms§8]§7";
    public static String fileSeparator = File.separator;

    private RealmConfigLibrary() {
        throw new IllegalStateException("Utility class");
    }

    public static String getLangString(String path) {
        return CharmRealm.pluginConfigManager.languageConfig.getString(path);
    }

    public static String getTemplatePath(String templateName) {
        return (CharmRealm.pluginVariable.worldFinal) + RealmConfigLibrary.fileSeparator + RealmCreateLibrary.templateFolderName + RealmConfigLibrary.fileSeparator + templateName;
    }


    public static void checkAndSaveResource(String resourcePath) {
        if (!(new File(JavaPlugin.getDataFolder() + fileSeparator + resourcePath)).exists())
            JavaPlugin.saveResource(resourcePath, false);
        Logger.log(false, true, Level.INFO, OperateType.ADD, "已找到配置文件 " + resourcePath);
    }

    public static FileConfiguration loadConfig(String configPath) {
        Logger.log(false, true, Level.INFO, OperateType.ADD, "已加载配置文件 " + configPath);
        return YamlConfiguration.loadConfiguration(new File(JavaPlugin.getDataFolder() + fileSeparator + configPath));

    }

    public static void displayPluginBanner() {
        Bukkit.getConsoleSender().sendMessage("\n" +
                "§9   ___ _                   ___          _              \n" +
                "§9  / __| |_  __ _ _ _ _ __ | _ \\___ __ _| |_ __          Running §bv2.3-BETA (v1_20)\n" +
                "§b | (__| ' \\/ _` | '_| '  \\|   / -_) _` | | '  \\         Server §9Mohist §bv1.20.1-820 (MC: 1.20.1)\n" +
                "§b  \\___|_||_\\__,_|_| |_|_|_|_|_\\___\\__,_|_|_|_|_|     \n" +
                "                  §9Developed by §bCaizii                  \n");

    }

}
