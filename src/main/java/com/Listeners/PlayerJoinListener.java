package com.Listeners;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.CheckUpdate;
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
    if (!Main.JavaPlugin.getConfig().getString("NormalJoinWorld").equalsIgnoreCase("")) {
      World world = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + Main.JavaPlugin.getConfig().getString("NormalJoinWorld"));
      event.getPlayer().teleport(world.getSpawnLocation());
    } 
    (new BukkitRunnable() {
        public void run() {
          if (Variable.wait_to_spawn_home.containsKey(event.getPlayer().getName())) {
            String command = (String)Variable.wait_to_spawn_home.get(event.getPlayer().getName());
            Bukkit.dispatchCommand((CommandSender)event.getPlayer(), command);
            Variable.wait_to_spawn_home.remove(event.getPlayer().getName());
          } 
          if (Variable.wait_to_command.containsKey(event.getPlayer().getName())) {
            String command = (String)Variable.wait_to_command.get(event.getPlayer().getName());
            Bukkit.dispatchCommand((CommandSender)event.getPlayer(), command);
            Variable.wait_to_command.remove(event.getPlayer().getName());
          } 
          if (Variable.has_already_move_world.contains(event.getPlayer().getName()))
            Variable.has_already_move_world.remove(event.getPlayer().getName()); 
        }
      }).runTaskLater((Plugin)Main.JavaPlugin, 40L);
    if (Main.JavaPlugin.getConfig().getBoolean("CheckUpdate") && 
      event.getPlayer().isOp() && CheckUpdate.new_Version != null)
      for (int i = 0; i < Variable.Lang_YML.getStringList("CheckHasNewPlugin").size(); i++) {
        String temp = Variable.Lang_YML.getStringList("CheckHasNewPlugin").get(i);
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
