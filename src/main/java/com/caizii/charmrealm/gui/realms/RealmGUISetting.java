package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.components.ButtonClickHandler;
import com.caizii.charmrealm.gui.components.CrossRealmContainer;
import com.caizii.charmrealm.gui.components.GUIButton;
import com.caizii.charmrealm.library.RealmVisualLibrary;
import com.caizii.charmrealm.utils.Color;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

import static com.caizii.charmrealm.library.RealmVisualLibrary.getPlayerRealmDisplayName;

@Getter
@Setter
public class RealmGUISetting extends CrossRealmContainer {

    public RealmGUISetting(Player owner, String InteractRealmConfigName) {
        super(owner, InteractRealmConfigName);
    }

    public RealmGUISetting(Player owner) {
        super(owner);
    }

    /**
     * @return
     */
    @Override
    public FileConfiguration getGUIConfig() {
        return CharmRealm.pluginVariable.GUI_SETTING_YML;
    }

    @Override
    public void postCustomGUIInitialize() {
        super.postCustomGUIInitialize();
        // Set the player head for banner
        List<GUIButton> settingBanner = readButtons("settingBanner");
        for (GUIButton guiButton : settingBanner) {
            guiButton.setItem(RealmVisualLibrary.buildPlayerHead(getCrossRealmOwner()));
            String playerRealmDisplayName = getPlayerRealmDisplayName(InteractRealmConfigName);
            guiButton.setButtonName(Color.parseColor(playerRealmDisplayName));
        }
        setButton(settingBanner, (event) -> {
            event.getWhoClicked().sendMessage("You clicked settingBanner");
        });
    }

    /**
     *
     */
    @Override
    public void onCustomGUIInitialize() {
        //super.onCustomGUIInitialize();
        setButton(readButtons("manageMember"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked manageMember");
            // 这里不用放到Post应为lambda是基于点击事件的运行时函数,非构造器调用
            CharmRealm.charmGuiHandler.openGUI(owner, new RealmGUIContentMember(owner, InteractRealmConfigName));
        });

        setButton(readButtons("resetRealm"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked resetRealm");
        });

        setButton(readButtons("visitState"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked visitState");
        });

        setButton(readButtons("help"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked help");
        });

        setButton(readButtons("backButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked backButton");
            CharmRealm.charmGuiHandler.closeGUI(owner);
            Bukkit.dispatchCommand(owner, "realm");
        });

    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean rendCustomGUI(Player player) {
        return super.rendCustomGUI(player);
    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean onCustomGUIClose(Player player) {
        return super.onCustomGUIClose(player);
    }

    /**
     * @param button
     */
    @Override
    public void setButton(GUIButton button, ButtonClickHandler handler) {
        super.setButton(button, handler);

    }

    /**
     * @param player
     */
    @Override
    public void open(Player player) {
        super.open(player);
    }

    /**
     * @param event
     */
    @Override
    public void handleClick(InventoryClickEvent event) {
        super.handleClick(event);
    }

}
