package com.caizii.charmrealm.gui.handler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TitleHandler {

    private HashMap<UUID, InventoryPlayer> inventoryPlayers = new HashMap<>();

    private final ProtocolManager protocolManager;
    private final JavaPlugin plugin;

    public TitleHandler(JavaPlugin plugin, ProtocolManager protocolManager) {
        this.plugin = plugin;
        this.protocolManager = protocolManager;
    }

    // -- Listening to packets --

    public void registerPacketListeners() {
        protocolManager.addPacketListener(getOpenWindowPacketListener());
        protocolManager.addPacketListener(getCloseWindowPacketListener());
    }

    private PacketListener getOpenWindowPacketListener() {
        return new PacketAdapter(plugin, ListenerPriority.HIGH, PacketType.Play.Server.OPEN_WINDOW) {
            @Override
            public void onPacketSending(PacketEvent event) {
                final UUID uuid = event.getPlayer().getUniqueId();

                final int windowId = event.getPacket().getIntegers().read(0);
                final Object containerType = event.getPacket().getStructures().readSafely(0);

                // Create our custom holder object (defined at the end of this class) and put it in a HashMap
                InventoryPlayer player = new InventoryPlayer(windowId, containerType);
                inventoryPlayers.put(uuid, player);
            }
        };
    }

    private PacketListener getCloseWindowPacketListener() {
        return new PacketAdapter(plugin, ListenerPriority.HIGH, PacketType.Play.Client.CLOSE_WINDOW) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                final UUID uuid = event.getPlayer().getUniqueId();

                // Remove the player from logged inventories, because a menu has been closed.
                inventoryPlayers.remove(uuid);
            }
        };
    }


    // -- API --

    public void setPlayerInventoryTitle(Player player, List<BaseComponent> title) {
        final InventoryType type = player.getOpenInventory().getType();
        if (type == InventoryType.CRAFTING || type == InventoryType.CREATIVE)
            return;

        InventoryPlayer inventoryPlayer = inventoryPlayers.getOrDefault(player.getUniqueId(), null);

        if (inventoryPlayer == null)
            return;

        final int windowId = inventoryPlayer.windowId();
        if (windowId == 0)
            return;

        final Object windowType = inventoryPlayer.containerType();
        final String titleJson = ComponentSerializer.toString(title);

        // Send the packet
        sendOpenScreenPacket(player, windowId, windowType, titleJson);
        // Update the inventory for the client (to show items)
        player.updateInventory();
    }


    // -- Utility --

    private void sendOpenScreenPacket(Player player, int windowId, Object windowType, String titleJson) {
        final WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromJson(titleJson);

        PacketContainer openScreen = new PacketContainer(PacketType.Play.Server.OPEN_WINDOW);
        openScreen.getIntegers().write(0, windowId);
        openScreen.getStructures().write(0, (InternalStructure) windowType);
        openScreen.getChatComponents().write(0, wrappedChatComponent);

        protocolManager.sendServerPacket(player, openScreen);
    }

    record InventoryPlayer(int windowId, Object containerType) {

    }


}