package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.gui.components.CharmGUIBase;
import org.bukkit.entity.Player;


public class RealmGUIRoleAssign extends CharmGUIBase {

    public RealmGUIRoleAssign(Player owner) {
        super(owner);
    }

    /*
     * Internal Logic for Role GUI
     * If player click "Back" button:
     * - player will navigated to Manage Member context menu
     * If player click "Visitor" button:
     * - player will set the target as "visitor"
     * If player click "Banned" button:
     * - player will set the target as "Banned", the context menu will not show
     * the banned player unless they turn on the filter "banned"
     */
}
