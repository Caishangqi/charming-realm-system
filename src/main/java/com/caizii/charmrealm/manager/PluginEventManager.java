package com.caizii.charmrealm.manager;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.library.Logger;
import com.caizii.charmrealm.library.OperateType;
import com.caizii.charmrealm.listeners.FarmProtectListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.text.MessageFormat;
import java.util.Set;
import java.util.logging.Level;

public class PluginEventManager {

    private static final String LISTENER_PACKAGE_NAME = "listeners";

    public boolean registerEventsByPackage(String packageName) {
        String listenerPath =CharmRealm.packageName + '.' + LISTENER_PACKAGE_NAME + '.' + packageName;
        // Debug Path
        //System.out.println(listenerPath);
        Set<Class<? extends Listener>> classes = new Reflections(listenerPath).getSubTypesOf(Listener.class);
        classes.forEach(var -> {
            try {
                if (var.getDeclaredConstructor().getParameterCount() == 0) {
                    Listener listener = var.getDeclaredConstructor().newInstance();
                    Bukkit.getServer().getPluginManager().registerEvents(listener, CharmRealm.getInstance());
                    Logger.log(false,true, Level.INFO, OperateType.ADD,MessageFormat.format("§7注册包 {0} 下的类 {1} 成功！", packageName, var.getName()));
                }
            } catch (Exception e) {
                Logger.log(true, MessageFormat.format("§c注册包 {0} 内的事件出现错误 {1}", packageName, e.getMessage()));
            }
        });
        return false;
    }

}
