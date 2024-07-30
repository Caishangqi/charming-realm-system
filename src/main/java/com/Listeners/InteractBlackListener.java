package com.Listeners;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractBlackListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getName().toUpperCase().contains("FAKEPLAYER") ||
                event.getPlayer().getName().toUpperCase().contains("AS-FakePlayer"))
            return;
        if (event.getPlayer().isOp() && event.getPlayer().getItemInHand().getType() == Material.APPLE) {
            if (event.getRightClicked() == null)
                return;

            event.getPlayer().sendMessage("§e§l§m--------------§7[§eDeBug§7]§e§l§m--------------");
            TextComponent Send_Block_Message = new TextComponent(
                    "§eType:" + event.getRightClicked().getType().toString().toUpperCase() + " §b>> §dCopy");
            Send_Block_Message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                    event.getRightClicked().getType().toString().toUpperCase()));
            event.getPlayer().spigot().sendMessage((BaseComponent) Send_Block_Message);
            event.getPlayer().sendMessage("§e§l§m--------------§7[§eDeBug§7]§e§l§m--------------");

        }
    }
}
