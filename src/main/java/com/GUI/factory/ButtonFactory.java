package com.GUI.factory;

import com.GUI.components.CustomGUI;
import com.GUI.components.GUIButton;

public interface ButtonFactory {
    GUIButton createButton(CustomGUI parentGUI);
}
