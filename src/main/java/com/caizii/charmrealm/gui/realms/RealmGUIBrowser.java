package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.button.MetaGUIButton;
import com.caizii.charmrealm.gui.components.GUIButton;
import com.caizii.charmrealm.gui.components.PagedContainer;
import com.caizii.charmrealm.gui.factory.MetaButtonFactory;
import com.caizii.charmrealm.gui.types.EButtonType;
import com.caizii.charmrealm.library.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.caizii.charmrealm.library.RealmVisualLibrary.buildPlayerHead;

public class RealmGUIBrowser extends PagedContainer {

    @Override
    public FileConfiguration getGUIConfig() {
        return CharmRealm.pluginVariable.GUI_BROWSER_YML;
    }

    public RealmGUIBrowser(Player owner) {
        super(owner);
    }

    /**
     * Set the Page data based on page
     *
     * @param page The target page
     */
    private void setDataPage(int page) {
        List<File> realmFiles = RealmConfigLibrary.getRealmFiles();
        int numberOfFiles = realmFiles.size();

        // Get Max contain elements
        int maxSize = containerRange.size();

        // Set Max page
        totalPages = (int) Math.ceil((double) numberOfFiles / maxSize);

        // Calculate cut element range
        int startRange = (page - 1) * maxSize;
        int endRange = page * maxSize;
        // currentFileIndex relative to current page
        int currentFilesIndex = 0;
        for (; startRange < endRange && startRange < numberOfFiles; startRange++) {
            MetaGUIButton playerRealmButton = (MetaGUIButton) new MetaButtonFactory(readButtonConfiguration(EButtonType.GENERATED)).createButton(this);
            playerRealmButton.setSlotIndex(containerRange.get(currentFilesIndex));
            String realmFileName = realmFiles.get(startRange).getName().replaceFirst("[.][^.]+$", "");
            playerRealmButton.setItem(buildPlayerHead(realmFileName));
            // set the meta values
            playerRealmButton.getMetaValues().put("owner", realmFileName);

            ConfigurationSection buttonConfiguration = readButtonConfiguration(EButtonType.GENERATED);
            List<String> lores = buttonConfiguration.getStringList("item.lore");
            // Get the permission and current realm file yaml
            GroupType playerPermission = RealmPermissionLibrary.getPlayerPermission(this.owner.getName(), realmFileName);
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(realmFiles.get(startRange));
            // paras internal placeholder
            for (int i = 0; i < lores.size(); i++) {
                lores.set(i, RealmVisualLibrary.parasInternalPlaceholder(yamlConfiguration, InternalPlaceholder.REALM_CREATE_DATE, lores.get(i), null));
                lores.set(i, RealmVisualLibrary.parasInternalPlaceholder(yamlConfiguration, InternalPlaceholder.REALM_CURRENT_PLAYER, lores.get(i), null));
                lores.set(i, RealmVisualLibrary.parasInternalPlaceholder(realmFileName, InternalPlaceholder.REALM_PERMISSION, lores.get(i), owner));
            }
            // Add last lore element additional text if player have permission
            if (playerPermission == GroupType.OWNER || playerPermission == GroupType.OPERATOR) {
                lores.add(RealmConfigLibrary.getLangString("button.realm.visit.OpenSettingTargetRealm"));
            }

            playerRealmButton.setButtonName(RealmVisualLibrary.getPlayerRealmDisplayName(realmFileName));
            playerRealmButton.setButtonLore(lores);

            setButton(playerRealmButton, (event) -> {
                if (event.isRightClick()) {
                    if (playerPermission == GroupType.OPERATOR || playerPermission == GroupType.OWNER) {
                        Bukkit.dispatchCommand(this.owner, "realm setting " + playerRealmButton.getMetaValues().get("owner"));
                        event.setCancelled(true);
                    }
                    return;
                }

                if (event.isLeftClick()) {
                    if (playerPermission != GroupType.BANNED) {
                        Bukkit.dispatchCommand(this.owner, "realm tp " + playerRealmButton.getMetaValues().get("owner"));
                        event.setCancelled(true);
                    }
                    return;
                }
                event.setCancelled(true);
            });

            currentFilesIndex++;
        }
    }

    @Override
    public void postCustomGUIInitialize() {
        super.postCustomGUIInitialize();
        setDataPage(1);
    }

    @Override
    public void onCustomGUIInitialize() {


        setButton(readButtons("featuredButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked featuredButton");
        });

        setButton(readButtons("settingButton"), (event) -> {
            Bukkit.dispatchCommand(this.owner, "realm setting");
            event.setCancelled(true);
        });

        setButton(readButtons("realmReturnButton"), (event) -> {
            if (event.isLeftClick()) {
                Bukkit.dispatchCommand(this.owner, "realm home");
                return;
            }
            if (event.isRightClick()) {
                Bukkit.dispatchCommand(this.owner, "realm setspawn");
            }
            event.setCancelled(true);
        });

        setButton(readButtons("filterButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked filterButton");
        });

        setButton(readButtons("nextButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked nextButton");
        });

        setButton(readButtons("previousButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked previousButton");
        });
    }
}
