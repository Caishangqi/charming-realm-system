package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.components.PagedContainer;
import com.caizii.charmrealm.gui.types.EButtonType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RealmGUIBrowser extends PagedContainer {

    @Override
    public FileConfiguration getGUIConfig() {
        return CharmRealm.pluginVariable.GUI_BROWSER_YML;
    }

    public RealmGUIBrowser(Player owner) {
        super(owner);
    }

    @Override
    public void onCustomGUIInitialize() {

        setButton(readButtons(EButtonType.GENERATED), (event) -> {
            event.getWhoClicked().sendMessage("You clicked EButtonType.GENERATED");
        });

        setButton(readButtons("featuredButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked featuredButton");
        });

        setButton(readButtons("settingButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked settingButton");
        });

        setButton(readButtons("realmReturnButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked realmReturnButton");
        });

        setButton(readButtons("filterButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked filterButton");
        });

        setButton(readButtons("nextButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked nextButton");
        });

        setButton(readButtons("previousButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked previousButton");
        });
    }
}
