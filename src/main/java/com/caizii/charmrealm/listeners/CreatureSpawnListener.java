package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CreatureSpawnListener implements Listener {
    @EventHandler
    public void onSpawm(final CreatureSpawnEvent event) {
        (new BukkitRunnable() {
            public void run() {
                if (!Util.CheckIsHome(
                        event.getEntity().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
                    return;
                String worldname = event.getEntity().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "");
                if (!CharmRealm.JavaPlugin.getConfig().getBoolean("EnableBlackEntities"))
                    return;
                LivingEntity livingEntity = event.getEntity();
                String type = null;

                type = event.getEntity().getType().toString().toUpperCase();

                for (int d = 0; d < CharmRealm.JavaPlugin.getConfig().getStringList("BlackEntitiesList").size(); d++) {
                    String temp = CharmRealm.JavaPlugin.getConfig().getStringList("BlackEntitiesList").get(d);
                    if (type.toUpperCase().equalsIgnoreCase(temp.toUpperCase())) {
                        livingEntity.remove();
                        break;
                    }
                }
            }
        }).runTaskAsynchronously((Plugin) CharmRealm.JavaPlugin);
    }
}
