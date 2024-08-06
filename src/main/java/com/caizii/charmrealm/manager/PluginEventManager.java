package com.caizii.charmrealm.manager;

import com.caizii.charmrealm.CharmRealm;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.text.MessageFormat;
import java.util.Set;

public class PluginEventManager {

    private static final String LISTENER_PACKAGE_NAME = "listeners";

    public boolean registerEventsByPackage(String packageName) {
        String listenerPath = getPath(CharmRealm.packageName, LISTENER_PACKAGE_NAME + '.' + packageName);
        Set<Class<? extends Listener>> classes = new Reflections(listenerPath).getSubTypesOf(Listener.class);
        classes.forEach(var -> {
            try {
                if (var.getDeclaredConstructor().getParameterCount() == 0) {
                    Listener listener = var.getDeclaredConstructor().newInstance();
                    Bukkit.getServer().getPluginManager().registerEvents(listener, CharmRealm.getInstance());
                }
            } catch (Exception e) {
                String string = MessageFormat.format("§8[§6CharmRealms§8]  §c注册包 {0} 内的事件出现错误 {1}", packageName, e.getMessage());
                Bukkit.getConsoleSender().sendMessage(string);
            }
        });
        return false;
    }

    String getPath(String path, String append) {
        return path + '.' + append;
    }
}
