package com.GUI.realms;

import com.GUI.components.CharmGUIBase;
import com.GUI.components.ClickSelectGUI;
import com.GUI.components.GUIButton;
import com.GUI.factory.BaseItemStackFactory;
import com.GUI.types.EButtonType;
import com.SelfHome.Variable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RealmGUICreate extends CharmGUIBase implements ClickSelectGUI {

    protected String selectedTemplate = "NONE";
    protected GUIButton selectedButton = null;

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
        selectedTemplate = getGUIConfig().getString("default-select");
        selectedButton = getDefaultSelectedButton();
        setButtonSelect(selectedButton, true);
        System.out.println("selected button index is: " + selectedButton.getSlotIndex());
    }

    protected GUIButton getDefaultSelectedButton() {
        for (GUIButton guiButton : buttons.values()) {
            if (guiButton.getButtonConfig() == null)
                throw new RuntimeException("No button config found");
            String templateString = guiButton.getButtonConfig().getString("template");
            if (templateString != null && templateString.equalsIgnoreCase(selectedTemplate)) {
                return guiButton;
            }
        }
        throw new RuntimeException("No default selected button found");
    }

    /**
     * @param button
     */
    @Override
    public void onButtonClicked(GUIButton button) {
        System.out.println("clicked button index is: " + button.getSlotIndex());
        switch (button.buttonType) {
            case WORLD_CREATE:
                String buttonSavedTemplate = button.getButtonConfig().getString("template");
                if (button != selectedButton) {
                    selectedButton = button;
                    setButtonSelect(selectedButton, true);
                    getOwner().sendMessage("Selected template: " + buttonSavedTemplate);
                    onUpdateGUI();
                }
        }
    }

    /**
     * We want to update other button name and lore
     * also update the GUI title
     *
     * @return
     */
    @Override
    public boolean onUpdateGUI() {
        updateSelectTypeButtonStyle();
        return super.onUpdateGUI();
    }

    protected void updateSelectTypeButtonStyle() {
        for (GUIButton guiButton : buttons.values()) {
            if (guiButton.getButtonType() == EButtonType.WORLD_CREATE) {
                setButtonSelect(guiButton, guiButton == selectedButton);
            }
        }
    }

    protected void setButtonSelect(GUIButton button, boolean bIsSelect) {
        if (button.getButtonType() != EButtonType.WORLD_CREATE) {
            throw new RuntimeException("Invalid button type: " + button.getButtonType());
        }

        ConfigurationSection styleItem = bIsSelect
                ? button.getButtonConfig().getConfigurationSection("selected-style.item")
                : button.getButtonConfig().getConfigurationSection("item");

        updateButtonStyle(button, styleItem);
    }

    private void updateButtonStyle(GUIButton button, ConfigurationSection styleItem) {
        button.setItem(new BaseItemStackFactory(styleItem).createItemStack());
        button.rendButton(getOwner());
    }

    /**
     * @param clickedComponents
     * @param offset
     */
    @Override
    public void clickSelect(GUIButton clickedComponents, String offset) {

    }


}
