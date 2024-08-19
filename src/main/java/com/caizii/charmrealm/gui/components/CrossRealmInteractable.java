package com.caizii.charmrealm.gui.components;


import java.util.UUID;

public interface CrossRealmInteractable {

    void setPanelTargetRealmConfig();
    void savePanelTargetRealmConfig();
    UUID getCrossRealmOwner();
}
