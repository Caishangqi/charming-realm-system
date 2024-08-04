package com.GUI.realms;

import com.GUI.components.CharmGUIBase;
import com.SelfHome.Variable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RealmGUICreate extends CharmGUIBase {
    public RealmGUICreate(Player owner) {
        super(owner);
    }

    /**
     * @return
     */
    @Override
    public FileConfiguration getGUIConfig() {
        return Variable.GUI_CREATE_YML;
    }

    /**
     *
     */
    @Override
    public void onCustomGUIInitialize() {
        super.onCustomGUIInitialize();
    }
}
