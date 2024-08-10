package com.caizii.charmrealm.manager;

import com.caizii.charmrealm.library.Logger;
import com.caizii.charmrealm.library.OperateType;
import com.caizii.charmrealm.library.RealmConfigLibrary;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.logging.Level;

import static com.caizii.charmrealm.CharmRealm.JavaPlugin;
import static com.caizii.charmrealm.library.RealmConfigLibrary.fileSeparator;

@Getter
@Setter
public class PluginConfigManager {

    public FileConfiguration mainConfig;
    public FileConfiguration languageConfig;


    public PluginConfigManager() {

    }

    public void initConfig() {
        Logger.log(true, "正在初始化插件所需要的配置文件...");

        JavaPlugin.saveDefaultConfig();
        JavaPlugin.reloadConfig();

        createTemplateFolder();
        copyDefaultConfig();
        loadDefaultConfig();
        Logger.log(true, "完成初始化插件所需要的配置文件!");
    }

    private void createTemplateFolder() {
        File playerDataFolder = new File(JavaPlugin.getDataFolder(), "template");
        if (!playerDataFolder.exists()) {
            // 创建文件夹
            if (playerDataFolder.mkdir()) {
                Logger.log(false, true, Level.INFO, OperateType.ADD, "已经创建模板文件夹,请将模板放入该文件夹内");
            } else {
                Logger.log(false, true, Level.WARNING, OperateType.REMOVE, "无法创建模板文件夹,请手动创建或提交Bug");
            }
        } else {
            Logger.log(false, true, Level.INFO, OperateType.ADD, "已发现模板文件夹无需创建,请将模板放入该文件夹内");
        }
    }

    private static void copyDefaultConfig() {
        RealmConfigLibrary.checkAndSaveResource("lang" + fileSeparator + "message_cn.yml");
        RealmConfigLibrary.checkAndSaveResource("template");
    }

    private void loadDefaultConfig() {
        languageConfig = RealmConfigLibrary.loadConfig("lang" + fileSeparator + "message_cn.yml");
    }

}
