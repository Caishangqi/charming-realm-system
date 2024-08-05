package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.components.ButtonClickHandler;
import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.gui.components.GUIButton;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RealmGUISetting extends CharmGUIBase {


    public RealmGUISetting(Player owner) {
        super(owner);
    }

    /**
     * @return
     */
    @Override
    public FileConfiguration getGUIConfig() {
        return CharmRealm.pluginVariable.GUI_SETTING_YML;
    }

    /**
     *
     */
    @Override
    public void onCustomGUIInitialize() {
        //super.onCustomGUIInitialize();
        setButton(readButtons("manageMember"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked manageMember");
        });

        setButton(readButtons("resetRealm"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked resetRealm");
        });

        setButton(readButtons("visitState"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked visitState");
        });

        setButton(readButtons("help"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked help");
        });
    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean rendCustomGUI(Player player) {
        return super.rendCustomGUI(player);
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
     * @param button
     */
    @Override
    public void setButton(GUIButton button, ButtonClickHandler handler) {
        super.setButton(button, handler);

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
