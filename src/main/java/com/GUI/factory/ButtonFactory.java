package com.GUI.factory;

import com.GUI.components.ButtonClickHandler;
import com.GUI.components.CharmGUIBase;
import com.GUI.components.GUIButton;

import java.util.List;

public interface ButtonFactory {
    List<GUIButton> createButton(CharmGUIBase parentGUI);
    void injectEvent(GUIButton button);
}
