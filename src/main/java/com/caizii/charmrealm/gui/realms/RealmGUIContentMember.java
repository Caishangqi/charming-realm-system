package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.components.CharmGUIPaged;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RealmGUIContentMember extends CharmGUIPaged {

    public RealmGUIContentMember(Player owner, String InteractRealmConfigName) {
        super(owner, InteractRealmConfigName);
    }

    public RealmGUIContentMember(Player owner) {
        super(owner);
    }

    @Override
    public FileConfiguration getGUIConfig() {
        return CharmRealm.pluginConfigManager.GUI_MEMBER_YML;
    }

    @Override
    public void onCustomGUIInitialize() {
        //super.onCustomGUIInitialize();
        setButton(readButtons("backButton"), (event) -> {
            CharmRealm.charmGuiHandler.openGUI(owner,new RealmGUISetting(owner,InteractRealmConfigName));
            event.getWhoClicked().sendMessage("You clicked backButton");
        });

        setButton(readButtons("manageBanned"), (event) -> {
            CharmRealm.charmGuiHandler.openGUI(owner,new RealmGUIContentBanned(owner,InteractRealmConfigName));
            event.getWhoClicked().sendMessage("You clicked manageBanned");
        });
    }
}
