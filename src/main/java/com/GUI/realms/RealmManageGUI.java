package com.GUI.realms;

import com.GUI.components.CustomGUI;
import com.GUI.components.GUIButton;
import com.GUI.factory.BaseButtonFactory;
import com.GUI.factory.ButtonFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class RealmManageGUI extends CustomGUI {
    public RealmManageGUI(String title, int size) {
        super(title, size);
    }

    /**
     *
     */
    @Override
    public void onCustomGUIInitialize() {
        super.onCustomGUIInitialize();
        ButtonFactory diamondButtonFactory = new BaseButtonFactory(
                Material.ANVIL,
                "Example Item",
                Arrays.asList("§7This is a", "§7special diamond"),
                (event) -> {
                    event.getWhoClicked().sendMessage("You Clicked this button");
                }
        );
        setButton(5, diamondButtonFactory.createButton(this));
    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean onCustomGUIDisplay(Player player) {
        return super.onCustomGUIDisplay(player);
    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean onCustomGUIClose(Player player) {
        return super.onCustomGUIClose(player);
    }

    /**
     * @param slot
     * @param button
     */
    @Override
    public void setButton(int slot, GUIButton button) {
        super.setButton(slot, button);
    }

    /**
     * @param player
     */
    @Override
    public void open(Player player) {
        super.open(player);
    }

    /**
     * @param event
     */
    @Override
    public void handleClick(InventoryClickEvent event) {
        super.handleClick(event);
    }
}
