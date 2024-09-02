package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.components.CrossRealmContainer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RealmGUIContentBanned extends CrossRealmContainer {
    public RealmGUIContentBanned(Player owner, String InteractRealmConfigName) {
        super(owner, InteractRealmConfigName);
    }

    public RealmGUIContentBanned(Player owner) {
        super(owner);
    }

    @Override
    public FileConfiguration getGUIConfig() {
        return CharmRealm.pluginConfigManager.GUI_BANNED_YML;
    }

    @Override
    public void onCustomGUIInitialize() {
        //super.onCustomGUIInitialize();
        setButton(readButtons("backButton"), (event) -> {
            CharmRealm.charmGuiHandler.openGUI(owner,new RealmGUISetting(owner,InteractRealmConfigName));
            event.getWhoClicked().sendMessage("You clicked backButton");
        });

        setButton(readButtons("manageMember"), (event) -> {
            CharmRealm.charmGuiHandler.openGUI(owner,new RealmGUIContentMember(owner,InteractRealmConfigName));
            event.getWhoClicked().sendMessage("You clicked manageMember");
        });
    }

}
