package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HomeProtectInteractListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {

        //Logger.log(false,true, Level.INFO, OperateType.ADD,event.getClickedBlock().toString());
        try {
            if (event.getClickedBlock().getType().toString().toUpperCase().contains("SIGN"))
                return;
            if (event.getClickedBlock().getType() == Material.valueOf(CharmRealm.pluginVariable.Soil) && event.getAction() == Action.PHYSICAL)
                return;
            String worldNameCurrent = event.getPlayer().getWorld().getName().replaceAll(CharmRealm.pluginVariable.world_prefix, "");
            if (!Util.CheckIsHome(worldNameCurrent))
                return;
            if (!Util.Check(event.getPlayer(), worldNameCurrent)) {
                String temp = CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionInteract");
                event.getPlayer().sendMessage(temp);
                event.setCancelled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
