package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
  public void onChat(AsyncPlayerChatEvent event) {
    if (!CharmRealm.JavaPlugin.getConfig().getBoolean("EnableChatPrefix"))
      return; 
    String temp = event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "");
    if (Util.CheckIsHome(event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))) {
      temp = CharmRealm.pluginVariable.Lang_YML.getString("PlaceHolders.WorldName");
      if (temp.contains("<PlayerName>"))
        temp = temp.replace("<PlayerName>", 
            event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")); 
      if (temp.contains("<WorldName>"))
        temp = temp.replace("<WorldName>", 
            event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")); 
    } else if (Util.getAliasName(event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")) != null) {
      temp = Util.getAliasName(event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""));
    } else if (!PlaceholderAPI.setPlaceholders(event.getPlayer(), "%multiverse_world_alias%").equalsIgnoreCase("%multiverse_world_alias%")) {
      temp = PlaceholderAPI.setPlaceholders(event.getPlayer(), "%multiverse_world_alias%");
    } 
    event.setFormat(String.valueOf(String.valueOf(temp)) + event.getFormat());
  }
}
