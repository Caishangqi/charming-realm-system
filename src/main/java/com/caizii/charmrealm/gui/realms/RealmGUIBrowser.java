package com.caizii.charmrealm.gui.realms;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.button.MetaGUIButton;
import com.caizii.charmrealm.gui.components.GUIButton;
import com.caizii.charmrealm.gui.components.PagedContainer;
import com.caizii.charmrealm.gui.factory.MetaButtonFactory;
import com.caizii.charmrealm.gui.types.EButtonType;
import com.caizii.charmrealm.library.RealmConfigLibrary;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        List<GUIButton> buttonsToSet = new ArrayList<>();
        List<File> realmFiles = RealmConfigLibrary.getRealmFiles();
        int numberOfFiles = realmFiles.size();

        // Get Max contain elements
        int maxSize = containerRange.size();

        // Set Max page
        totalPages = (int) Math.ceil((double) numberOfFiles / maxSize);

        // Calculate cut element range
        // 假设总共 129 个， 当前在第二页， 一页总共 21 个
        int startRange = (page - 1) * maxSize;
        int endRange = page * maxSize;

        int currentFilesIndex = 0;
        for (; startRange < endRange && startRange < numberOfFiles; startRange++) {
            MetaGUIButton playerRealmButton = (MetaGUIButton) new MetaButtonFactory(readButtonConfiguration(EButtonType.GENERATED)).createButton(this);
            playerRealmButton.setSlotIndex(containerRange.get(currentFilesIndex));
            String realmFileName = realmFiles.get(startRange).getName().replaceFirst("[.][^.]+$", "");
            playerRealmButton.setItem(buildPlayerHead(realmFileName));
            // set the meta values
            playerRealmButton.getMetaValues().put("owner", realmFileName);

            buttonsToSet.add(playerRealmButton);
            currentFilesIndex++;
        }

        setButton(buttonsToSet, (event) -> {
            event.getWhoClicked().sendMessage("You clicked EButtonType.GENERATED");
        });
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
            event.getWhoClicked().sendMessage("You clicked settingButton");
        });

        setButton(readButtons("realmReturnButton"), (event) -> {
            event.getWhoClicked().sendMessage("You clicked realmReturnButton");
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
