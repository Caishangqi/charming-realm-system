package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.Util;
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
                        event.getEntity().getWorld().getName().replace(Variable.world_prefix, "")))
                    return;
                String worldname = event.getEntity().getWorld().getName().replace(Variable.world_prefix, "");
                if (!Main.JavaPlugin.getConfig().getBoolean("EnableBlackEntities"))
                    return;
                LivingEntity livingEntity = event.getEntity();
                String type = null;

                type = event.getEntity().getType().toString().toUpperCase();

                for (int d = 0; d < Main.JavaPlugin.getConfig().getStringList("BlackEntitiesList").size(); d++) {
                    String temp = Main.JavaPlugin.getConfig().getStringList("BlackEntitiesList").get(d);
                    if (type.toUpperCase().equalsIgnoreCase(temp.toUpperCase())) {
                        livingEntity.remove();
                        break;
                    }
                }
            }
        }).runTaskAsynchronously((Plugin) Main.JavaPlugin);
    }
}
