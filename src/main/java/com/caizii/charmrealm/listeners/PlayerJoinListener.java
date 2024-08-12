package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.CheckUpdate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class PlayerJoinListener implements Listener {
  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {
    if (!CharmRealm.JavaPlugin.getConfig().getString("NormalJoinWorld").equalsIgnoreCase("")) {
      World world = Bukkit.getWorld(String.valueOf(String.valueOf(CharmRealm.pluginVariable.world_prefix)) + CharmRealm.JavaPlugin.getConfig().getString("NormalJoinWorld"));
      event.getPlayer().teleport(world.getSpawnLocation());
    } 
    (new BukkitRunnable() {
        public void run() {
          if (CharmRealm.pluginVariable.wait_to_spawn_home.containsKey(event.getPlayer().getName())) {
            String command = (String)CharmRealm.pluginVariable.wait_to_spawn_home.get(event.getPlayer().getName());
            Bukkit.dispatchCommand((CommandSender)event.getPlayer(), command);
            CharmRealm.pluginVariable.wait_to_spawn_home.remove(event.getPlayer().getName());
          } 
          if (CharmRealm.pluginVariable.wait_to_command.containsKey(event.getPlayer().getName())) {
            String command = (String)CharmRealm.pluginVariable.wait_to_command.get(event.getPlayer().getName());
            Bukkit.dispatchCommand((CommandSender)event.getPlayer(), command);
            CharmRealm.pluginVariable.wait_to_command.remove(event.getPlayer().getName());
          } 
          if (CharmRealm.pluginVariable.has_already_move_world.contains(event.getPlayer().getName()))
            CharmRealm.pluginVariable.has_already_move_world.remove(event.getPlayer().getName()); 
        }
      }).runTaskLater((Plugin) CharmRealm.JavaPlugin, 40L);
    if (CharmRealm.JavaPlugin.getConfig().getBoolean("CheckUpdate") &&
      event.getPlayer().isOp() && CheckUpdate.new_Version != null)
      for (int i = 0; i < CharmRealm.pluginVariable.Lang_YML.getStringList("CheckHasNewPlugin").size(); i++) {
        String temp = CharmRealm.pluginVariable.Lang_YML.getStringList("CheckHasNewPlugin").get(i);
        if (temp.contains("<Now>"))
          temp = temp.replace("<Now>", "V2.0.2.9"); 
        if (temp.contains("<New>"))
          temp = temp.replace("<New>", CheckUpdate.new_Version); 
        if (temp.contains("<Link>"))
          temp = temp.replace("<Link>", "https://gitee.com/a1242839141/SelfHomeMain/releases"); 
        event.getPlayer().sendMessage(temp);
      }  
  }
}
