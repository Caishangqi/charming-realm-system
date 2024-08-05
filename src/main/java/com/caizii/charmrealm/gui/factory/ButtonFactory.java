package com.caizii.charmrealm.gui.factory;

import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.gui.components.GUIButton;

import java.util.List;

public interface ButtonFactory {
    List<GUIButton> createButton(CharmGUIBase parentGUI);
    void injectEvent(GUIButton button);
}
