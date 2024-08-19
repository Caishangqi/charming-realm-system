package com.caizii.charmrealm.gui.canvas;

import com.caizii.charmrealm.gui.components.CharmGUIBase;
import com.caizii.charmrealm.library.Logger;
import com.caizii.charmrealm.library.OperateType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.logging.Level;

/**
 * Canvas is the personal "viewport" for each player, when player open gui that
 * belongs to the plugin, the canvas will create and save the gui to cachedGUI.
 * Also, canvas will keep-track a additional currently displayedGUI although bukkit
 * does
 * <p>
 * All manipulation of gui should executed by canvas through GUI handler, all gui internal
 * button logic for switching gui should executed by GUI handler too!
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
    protected HashSet<CharmGUIBase> cachedGUI = new HashSet<>() {
    };

    public Canvas(Player owner) {
        this.Owner = owner;
    }

    public void closeDisplayedGUI(Player targetPlayer) {
        if (displayedGUI != null) {
            displayedGUI.close(targetPlayer);
        } else {
            targetPlayer.getOpenInventory().close();
            Logger.log(true, false, Level.WARNING, OperateType.CAUTION, "你关闭了插件的GUI但是该GUI并不在GUI管理器中");
        }

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
                cachedGUI.add(guiByType);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException();
            }
        }

        if (displayedGUI != null) {
            displayedGUI.close(Owner);
            displayedGUI = guiByType;
            displayedGUI.open(Owner);
            return;
        }

        displayedGUI = guiByType;
        displayedGUI.open(Owner);

    }

    public void displayGUIToViewport(CharmGUIBase guiInstance) {

        CharmGUIBase guiByType = getGUIByType(guiInstance.getClass());

        // 如果能找到同类型的GUI则开始判断GUI内部元素是否相等
        if (guiByType != null && !guiByType.equals(guiInstance)) {
            removeGUIByType(guiInstance.getClass());    // 把要被替换的GUI缓存清除
            guiByType = guiInstance;
            cachedGUI.add(guiInstance);
        }

        if (guiByType == null) {
            cachedGUI.add(guiInstance);
            guiByType = guiInstance;
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

    private <T extends CharmGUIBase> void removeGUIByType(Class<T> guiclass) {
        cachedGUI.removeIf(gui -> guiclass.isAssignableFrom(gui.getClass()));
    }

}
