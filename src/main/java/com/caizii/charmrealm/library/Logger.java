package com.caizii.charmrealm.library;


import com.caizii.charmrealm.utils.Color;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public final class Logger {

    public static String tabOperant = "              ";

    private Logger() {
        throw new IllegalStateException("Logger class");
    }

    public static void log(boolean havePrefix, String message) {
        log(havePrefix, false, Level.INFO, OperateType.NONE, message);

    }

    public static void log(boolean havePrefix, Level logLevel, String message) {
        log(havePrefix, false, logLevel, OperateType.NONE, message);

    }

    public static void log(boolean havePrefix, boolean operateTab, Level logLevel, OperateType operateType, String message) {

        String prefix = "";
        String operate = "";
        String operateTabString = "";

        if (operateTab)
            operateTabString = tabOperant;

        if (havePrefix) {
            prefix = RealmConfigLibrary.pluginPrefix;
        }

        switch (operateType) {
            case ADD:
                operate = "§8(§a+§8)§7";
                break;
            case REMOVE:
                operate = "§8(§c-§8)§7";
                break;
            case DELETE:
                operate = "§8(§c-§8)§7";
                break;
            case CAUTION:
                operate = "§8(§6!§8)§7";
                break;
            case NONE:
                operate = "§7";
                break;
        }


        Bukkit.getConsoleSender().sendMessage(Color.parseColor(prefix + " " + operateTabString + operate + message));
        //Bukkit.getLogger().log(logLevel, prefix + " " + operateTabString + operate + message);
    }

}
