package com.caizii.charmrealm.gui.components;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class CharmGUIPaged extends CrossRealmContainer{

    protected GUIDynamicDataComponent guiDynamicDataComponent;

    public void updateGUIDynamicData(){}

    public CharmGUIPaged(Player owner, String InteractRealmConfigName) {
        super(owner, InteractRealmConfigName);
    }

    public CharmGUIPaged(Player owner, String InteractRealmConfigName, GUIDynamicDataComponent guiDynamicDataComponent) {
        super(owner, InteractRealmConfigName);
        this.guiDynamicDataComponent = guiDynamicDataComponent;
    }

    public CharmGUIPaged(Player owner) {
        super(owner);
    }
}
