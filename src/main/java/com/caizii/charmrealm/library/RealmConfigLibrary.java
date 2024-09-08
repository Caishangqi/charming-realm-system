package com.caizii.charmrealm.library;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.gui.types.EButtonType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static List<Integer> getButtonRange(EButtonType buttonType, CharmGUIBase guiContext) {
        ConfigurationSection buttonConfiguration = guiContext.readButtonConfiguration(buttonType);
        String slotRange = buttonConfiguration.getString("slot");
        return convertRange(slotRange);
    }

    public static List<Integer> getButtonRange(String ButtonKey, CharmGUIBase guiContext) {
        ConfigurationSection buttonConfiguration = guiContext.readButtonConfiguration(ButtonKey);
        String slotRange = buttonConfiguration.getString("slot");
        return convertRange(slotRange);
    }

    public static List<File> getRealmFiles() {
        File folder = new File(CharmRealm.pluginVariable.Tempf);
        File[] listedFiles = folder.listFiles();
        if (listedFiles == null) {
            return null;
        }
        return Arrays.stream(listedFiles).toList();
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

    public static List<Integer> convertRange(String range) {
        List<Integer> result = new ArrayList<>();

        // 移除方括号（如果存在）
        range = range.replaceAll("[\\[\\]]", "");

        // 以逗号分隔
        String[] parts = range.split(",");

        for (String part : parts) {
            part = part.trim(); // 去除首尾空格

            if (part.contains("-")) {
                String[] rangeParts = part.split("-");
                if (rangeParts.length != 2) {
                    throw new IllegalArgumentException("Invalid range: " + part);
                }

                try {
                    int start = Integer.parseInt(rangeParts[0]);
                    int end = Integer.parseInt(rangeParts[1]);

                    // 确保范围是有效的
                    if (start > end) {
                        throw new IllegalArgumentException("Invalid range: " + part);
                    }

                    for (int i = start; i <= end; i++) {
                        result.add(i);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number in range: " + part, e);
                }
            } else {
                try {
                    int number = Integer.parseInt(part);
                    result.add(number);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number: " + part, e);
                }
            }
        }
        return result;
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
