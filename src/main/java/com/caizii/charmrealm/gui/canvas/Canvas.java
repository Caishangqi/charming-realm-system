package com.caizii.charmrealm.gui.canvas;

import com.caizii.charmrealm.gui.components.CharmGUIBase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Canvas is the personal "viewport" for each player, when player open gui that
 * belongs to the plugin, the canvas will create and save the gui to cachedGUI.
 * Also, canvas will keep-track a additional currently displayedGUI although bukkit
 * does
 *
 * All manipulation of gui should executed by canvas through GUI handler, all gui internal
 * button logic for switching gui should executed by GUI handler too!
 *
 */
public class Canvas {

    @Getter
    @Setter
    private Player Owner;
    @Getter
    @Setter
    private CharmGUIBase displayedGUI = null;
    /**
     * cachedGUI that save for object creation but may need more memory
     */
    protected List<CharmGUIBase> cachedGUI = new ArrayList<>();

    public Canvas(Player owner) {
        this.Owner = owner;
    }

    public void closeDisplayedGUI(Player targetPlayer) {
        displayedGUI.close(targetPlayer);
        displayedGUI = null;
    }

    public void guiOpenEvent(Player targetPlayer,
                             CharmGUIBase targetGUI) {
        if (displayedGUI != targetGUI) {
            throw new RuntimeException("玩家打开的GUI和画板展示的GUI不符,理论上这不应该");
        }
        targetGUI.onCustomGUIDisplay(targetPlayer, targetGUI);
    }

    public void guiClickEvent(InventoryClickEvent event, CharmGUIBase targetGUI) {
        if (displayedGUI != targetGUI) {
            throw new RuntimeException("玩家打开的GUI和画板展示的GUI不符,理论上这不应该");
        }
        targetGUI.handleClick(event);
    }

    public <T extends CharmGUIBase> void displayGUIToViewport(Class<T> guiclass) {

        CharmGUIBase guiByType = getGUIByType(guiclass);

        if (guiByType == null) {
            try {
                Constructor<T> constructor = guiclass.getConstructor(Player.class);
                guiByType = constructor.newInstance(Owner);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (displayedGUI != null) {
            displayedGUI.close(Owner);
            displayedGUI = guiByType;
            displayedGUI.open(Owner);
        }

        displayedGUI = guiByType;
        displayedGUI.open(Owner);

    }

    private <T extends CharmGUIBase> CharmGUIBase getGUIByType(Class<T> guiclass) {
        for (CharmGUIBase gui : cachedGUI) {
            if (guiclass.isAssignableFrom(gui.getClass())) {
                return gui;
            }
        }
        return null;
    }


}
