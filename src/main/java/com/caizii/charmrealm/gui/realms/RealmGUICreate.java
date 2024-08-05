package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.gui.components.ClickSelectGUI;
import com.caizii.charmrealm.gui.components.GUIButton;
import com.caizii.charmrealm.gui.factory.BaseItemStackFactory;
import com.caizii.charmrealm.gui.types.EButtonType;
import com.caizii.charmrealm.utils.Color;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RealmGUICreate extends CharmGUIBase implements ClickSelectGUI {

    protected String selectedTemplate = "NONE";
    protected GUIButton selectedButton = null;

    /*============= Select Style ============*/
    protected String rowFontImageWidth = "";
    protected List<String> rowFontImage = new ArrayList<>();
    protected String selectStyleTitleComponent = "";
    /*============= Select Style ============*/

    public RealmGUICreate(Player owner) {
        super(owner);
    }


    /**
     * @return
     */
    @Override
    public FileConfiguration getGUIConfig() {
        return CharmRealm.pluginVariable.GUI_CREATE_YML;
    }

    /**
     *
     */
    @Override
    public void onCustomGUIInitialize() {
        super.onCustomGUIInitialize();
        selectedTemplate = getGUIConfig().getString("default-select");
        rowFontImage = getGUIConfig().getStringList("dynamic-title.rowFontImage");
        rowFontImageWidth = getGUIConfig().getString("dynamic-title.rowFontImageWidth");
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

        // we use this method to confirm selectStyleString
        // and let gui title append this
        clickSelect(selectedButton);
        String parsedSelectStyleTitle = Color.parseColorAndPlaceholder(owner, selectStyleTitleComponent);
        // append parsedSelectStyleTitle to original saved gui title from config
        String updatedGUITitle = getTitle() + parsedSelectStyleTitle;

        // we do not append select style to gui title because it is client data
        // we want the updatedGUITitle only send to client not saved by server side
        List<BaseComponent> components = new ArrayList<>();
        components.add(new TextComponent(updatedGUITitle));
        CharmRealm.titleHandler.setPlayerInventoryTitle(getOwner(), components);

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
     */
    @Override
    public void clickSelect(GUIButton clickedComponents) {

        int clickedComponentsSlotIndex = clickedComponents.getSlotIndex();
        int[] indexToRowCol = indexToRowCol(clickedComponentsSlotIndex);

        // suppose i clicked 40 (index)
        // to row and col is [4,4]

        System.out.println(Arrays.toString(indexToRowCol));
        System.out.println(getGUIConfig().getStringList("dynamic-title.rowFontImage"));
        System.out.println(rowFontImage);
        // we choose the vertical offset font image
        String verticalOffsetFont = rowFontImage.get(indexToRowCol[0]);

        // we calculate how many horizontal offset String
        String horizontalOffset = rowFontImageWidth.repeat(indexToRowCol[1]);

        // then we update GUI Create selection part of UI
        selectStyleTitleComponent = horizontalOffset + verticalOffsetFont;

    }


}
