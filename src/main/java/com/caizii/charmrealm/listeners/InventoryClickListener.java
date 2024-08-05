package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.gui.*;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onOpen(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null)
            return;
        if (event.getSlot() == -1 || event.getSlot() == 999)
            return;
        Player p = (Player) event.getWhoClicked();
        boolean check_gui_in_plugins = false;
        String holder = "";
        if (event.getInventory().getHolder() instanceof CheckGui) {
            holder = "Check";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof CreateGui) {
            holder = "Create";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof DenyGui) {
            holder = "Deny";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof InviteGui) {
            holder = "Invite";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof MainGui) {
            holder = "CharmRealm";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof ManageGui) {
            holder = "Manage";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof ManageGui2) {
            holder = "Manage2";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof ManageGui3) {
            holder = "Manage3";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof ManageGui4) {
            holder = "Manage4";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof ManageGui5) {
            holder = "Manage5";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof TrustGui) {
            holder = "Trust";
            check_gui_in_plugins = true;
        } else if (event.getInventory().getHolder() instanceof VisitGui) {
            holder = "Visit";
            check_gui_in_plugins = true;
        }
        if (!check_gui_in_plugins)
            return;
        event.setCancelled(true);
        ItemStack i = event.getCurrentItem();
        if (i == null)
            return;
        if (!i.hasItemMeta())
            return;
        if (i.getItemMeta().getDisplayName() == null)
            return;
        if (i.getItemMeta().getDisplayName().equalsIgnoreCase(CharmRealm.pluginVariable.GUI_YML.getString("Next"))) {
            if (event.getClickedInventory().getHolder() instanceof DenyGui) {
                DenyGui gui = (DenyGui) event.getClickedInventory().getHolder();
                gui.OpenNextInventory(p);
            }
            if (event.getClickedInventory().getHolder() instanceof InviteGui) {
                InviteGui gui = (InviteGui) event.getClickedInventory().getHolder();
                gui.OpenNextInventory(p);
            }
            if (event.getClickedInventory().getHolder() instanceof TrustGui) {
                TrustGui gui = (TrustGui) event.getClickedInventory().getHolder();
                gui.OpenNextInventory(p);
            }
            if (event.getClickedInventory().getHolder() instanceof VisitGui) {
                VisitGui gui = (VisitGui) event.getClickedInventory().getHolder();
                gui.OpenNextInventory(p);
            }
        } else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(CharmRealm.pluginVariable.GUI_YML.getString("Prev"))) {
            if (event.getClickedInventory().getHolder() instanceof DenyGui) {
                DenyGui gui = (DenyGui) event.getClickedInventory().getHolder();
                gui.OpenPrevInventory(p);
            }
            if (event.getClickedInventory().getHolder() instanceof InviteGui) {
                InviteGui gui = (InviteGui) event.getClickedInventory().getHolder();
                gui.OpenPrevInventory(p);
            }
            if (event.getClickedInventory().getHolder() instanceof TrustGui) {
                TrustGui gui = (TrustGui) event.getClickedInventory().getHolder();
                gui.OpenPrevInventory(p);
            }
            if (event.getClickedInventory().getHolder() instanceof VisitGui) {
                VisitGui gui = (VisitGui) event.getClickedInventory().getHolder();
                gui.OpenPrevInventory(p);
            }
        }
        String name = i.getItemMeta().getDisplayName();

        // 使用nbt api区获得按钮内部属性,忽略其display
        String ButtonClass = NBT.get(i, nbt -> (String) nbt.getString("ButtonClass"));
        String Owner = NBT.get(i, nbt -> (String) nbt.getString("Owner"));

        if (ButtonClass.equalsIgnoreCase("VisitGUI.PlayerHead")) {
            p.closeInventory(); // 尝试直接关闭这里的GUI
            Bukkit.dispatchCommand((CommandSender) p, "realm v " + Owner);
            return;
        }

        if (name.contains(CharmRealm.pluginVariable.Lang_YML.getString("CheckGuiHomePrefix")) &&
                name.contains(CharmRealm.pluginVariable.Lang_YML.getString("CheckGuiHomeSuffix"))) {
            name = name.replace(CharmRealm.pluginVariable.Lang_YML.getString("CheckGuiHomePrefix"), "");
            name = name.replace(CharmRealm.pluginVariable.Lang_YML.getString("CheckGuiHomeSuffix"), "");
            Bukkit.dispatchCommand((CommandSender) p, "realm v " + name);
            return;
        }
        if (name.contains(CharmRealm.pluginVariable.Lang_YML.getString("TrustGuiPrefix"))) {
            name = name.replace(CharmRealm.pluginVariable.Lang_YML.getString("TrustGuiPrefix"), "");
            if (event.getClick() == ClickType.LEFT) {
                Bukkit.dispatchCommand((CommandSender) p, "realm trust " + name);
            } else {
                Bukkit.dispatchCommand((CommandSender) p, "realm remove " + name);
            }
            return;
        }
        if (name.contains(CharmRealm.pluginVariable.Lang_YML.getString("InviteGuiPrefix"))) {
            name = name.replace(CharmRealm.pluginVariable.Lang_YML.getString("InviteGuiPrefix"), "");
            if (event.getClick() == ClickType.LEFT) {
                Bukkit.dispatchCommand((CommandSender) p, "realm invite " + name);
            } else {
                Bukkit.dispatchCommand((CommandSender) p, "realm kick " + name);
            }
            return;
        }
        if (name.contains(CharmRealm.pluginVariable.Lang_YML.getString("DenyGuiPrefix"))) {
            name = name.replace(CharmRealm.pluginVariable.Lang_YML.getString("DenyGuiPrefix"), "");
            if (event.getClick() == ClickType.LEFT) {
                Bukkit.dispatchCommand((CommandSender) p, "realm deny " + name);
            } else {
                Bukkit.dispatchCommand((CommandSender) p, "realm undeny " + name);
            }
            return;
        }
        if (getBtnID(i, holder) == null)
            return;
        String BTNID = getBtnID(i, holder);
        if (event.getClick() == ClickType.LEFT) {
            Bukkit.dispatchCommand((CommandSender) p, CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(BTNID)) + ".LeftInTo"));
            if (CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(BTNID)) + ".LeftInTo") != null && !CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(BTNID)) + ".LeftInTo").equalsIgnoreCase("") && !CharmRealm.pluginVariable.GUI_YML.getBoolean(String.valueOf(String.valueOf(BTNID)) + ".KeepOpen"))
                p.closeInventory();
        } else {
            Bukkit.dispatchCommand((CommandSender) p, CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(BTNID)) + ".RightInTo"));
            if (event.getClick() == ClickType.RIGHT && CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(BTNID)) + ".LeftInTo") != null && !CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(BTNID)) + ".RightInTo").equalsIgnoreCase("") && !CharmRealm.pluginVariable.GUI_YML.getBoolean(String.valueOf(String.valueOf(BTNID)) + ".KeepOpen"))
                p.closeInventory();
        }
    }

    public static String getBtnID(ItemStack i, String now) {
        String result = null;
        ConfigurationSection cs = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("");
        for (String temp : cs.getKeys(false)) {
            if (CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(temp)) + ".InMenu") == null)
                continue;
            if (CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(temp)) + ".CustomName").equalsIgnoreCase(i.getItemMeta().getDisplayName()) &&
                    now.equalsIgnoreCase(CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(String.valueOf(temp)) + ".InMenu"))) {
                result = temp;
                break;
            }
        }
        return result;
    }
}
