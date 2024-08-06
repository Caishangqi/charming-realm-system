package com.caizii.charmrealm.gui.components;

import com.caizii.charmrealm.gui.factory.BaseButtonFactory;
import com.caizii.charmrealm.gui.factory.WorldCreateButtonFactory;
import com.caizii.charmrealm.gui.types.EButtonType;
import com.caizii.charmrealm.utils.Color;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CharmGUIBase implements InventoryHolder {
    private final Inventory inventory;
    protected Map<Integer, GUIButton> buttons = new HashMap<>();
    @Getter
    private final boolean bIsAllowEmptyClick = false;
    @Getter
    protected Player owner;
    @Getter
    protected FileConfiguration GUIConfigYML;
    @Setter
    @Getter
    protected String title;
    @Getter
    protected int size;
    @Getter
    @Setter
    public HashMap<String, String> internalData = new HashMap<>();

    public CharmGUIBase(Player owner) {
        this.GUIConfigYML = getGUIConfig();
        this.owner = owner;
        String GUITitle = GUIConfigYML.getString("name");
        this.title = GUITitle;
        size = GUIConfigYML.getInt("size");
        String parsedTitle = Color.parseColorAndPlaceholder(owner, GUITitle);
        assert GUITitle != null;
        this.setTitle(parsedTitle);
        this.inventory = Bukkit.createInventory(this, size, parsedTitle);
        onCustomGUIInitialize();
    }

    // Set the layouts and buttons but not render papi and color
    public void onCustomGUIInitialize() {
        // read all buttons from config yml and create button instance
        List<GUIButton> buttonArrayList = readButtons();
        setButton(buttonArrayList);
    }

    // Start rend the buttons and papi, color to the native bukkit inventory
    public boolean rendCustomGUI(Player player) {
        for (GUIButton button : buttons.values()) {
            button.rendButton(player);
        }
        return player.isValid();
    }

    /*
        Called when the custom GUI is displayed to player -> this method is ensure the
        player actually see the GUI
     */
    public void onCustomGUIDisplay(Player targetPlayer, CharmGUIBase targetGUI) {
        //String string = MessageFormat.format("onCustomGUIDisplay({0}, {1})", targetPlayer.getDisplayName(), targetGUI.getTitle());
        //        System.out.println(string);
    }

    public boolean onCustomGUIClose(Player player) {
        player.closeInventory();
        return player.isValid();
    }

    /*
    Only put the Button instance on List (Layout) later onDisplay will rend those
    buttons into the bukkit inventory
     */
    public void setButton(GUIButton button, ButtonClickHandler handler) {
        buttons.put(button.slotIndex, button);
        button.setHandler(handler);
    }

    /*
    Only put the Button instance on List (Layout) later onDisplay will rend those
    buttons into the bukkit inventory
     */
    public void setButton(GUIButton button) {
        buttons.put(button.slotIndex, button);
    }

    /*
    Only put the Button instance on List (Layout) later onDisplay will rend those
    buttons into the bukkit inventory
     */
    public void setButton(List<GUIButton> buttons) {
        if (buttons.size() == 1) {
            setButton(buttons.get(0));
        } else {
            for (GUIButton button : buttons) {
                setButton(button);
            }
        }

    }

    /*
    Only put the Button instance on List (Layout) later onDisplay will rend those
    buttons into the bukkit inventory
     */
    public void setButton(List<GUIButton> buttons, ButtonClickHandler handler) {
        if (buttons.size() == 1) {
            setButton(buttons.get(0), handler);
        } else {
            for (GUIButton button : buttons) {
                setButton(button, handler);
            }
        }

    }

    public void open(Player player) {
        this.owner = player;
        rendCustomGUI(player);
        player.openInventory(inventory);
    }

    // this method is server side event that indicate server actively open
    // the gui for player, but not guarantee player will receive GUI
    public void onCustomGUIOpen(Player player, CharmGUIBase targetGUI) {

    }

    public void handleClick(InventoryClickEvent event) {

        GUIButton button = buttons.get(event.getSlot());
        if (button != null) {
            button.onClick(event);
        }
        event.setCancelled(!bIsAllowEmptyClick);
    }

    public ConfigurationSection readButtonConfiguration(String ButtonKey) {
        return this.GUIConfigYML.getConfigurationSection("items." + ButtonKey);
    }


    public List<GUIButton> readButtons(String ButtonKey) {
        ConfigurationSection buttonConfig = readButtonConfiguration(ButtonKey);

        switch (getButtonType(ButtonKey)) {
            case DEFAULT:
                return new BaseButtonFactory(buttonConfig).createButton(this);
            case WORLD_CREATE:
                return new WorldCreateButtonFactory(buttonConfig).createButton(this);
            default:
                throw new RuntimeException("Unknown button type: " + getButtonType(ButtonKey));
        }
    }

    EButtonType getButtonType(String ButtonKey) {
        ConfigurationSection buttonConfig = readButtonConfiguration(ButtonKey);
        return EButtonType.valueOf(buttonConfig.getString("type"));
    }

    public FileConfiguration getGUIConfig() {
        return this.GUIConfigYML;
    }

    /*
    Read all buttons from the GUI configuration
     */
    public List<GUIButton> readButtons() {

        ArrayList<GUIButton> buttonList = new ArrayList<>();


        Set<String> buttons = this.GUIConfigYML.getConfigurationSection("items").getKeys(false);
        for (String buttonSection : buttons) {
            buttonList.addAll(readButtons(buttonSection));
        }
        return buttonList;
    }

    /**
     * @return
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }


    public GUIButton getButton(int slot) {
        for (GUIButton button : buttons.values()) {
            if (button.slotIndex == slot) {
                return button;
            }
        }
        throw new RuntimeException("Slot " + slot + " not found");
    }

    // this is a more abstract method called by Button clicked
    // you should not directly called this methods
    public void onButtonClicked(GUIButton button) {

    }

    public boolean onUpdateGUITitle() {
        return true;
    }

    public int[] indexToRowCol(int index) {
        if (index < 0 || index > 53) {
            throw new IllegalArgumentException("Index must be between 0 and 53.");
        }
        int row = index / 9; // Calculate the row number
        int col = index % 9; // Calculate the column number
        return new int[]{row, col};
    }

}
