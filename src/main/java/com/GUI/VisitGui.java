package com.GUI;

import com.Hookers.VisitGUIHook;
import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.Util.*;
import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VisitGui implements InventoryHolder {
    public Inventory MainGui = Bukkit.createInventory(this, 54, Variable.GUI_YML.getString("VisitTitle"));

    public int MaxPage = 0;

    public int NowPage = 0;

    public ArrayList<Home> players = new ArrayList<>();

    int item_add_amount = 0;

    public VisitGui() {
        (new BukkitRunnable() {
            public void run() {
                ConfigurationSection dd = Variable.GUI_YML.getConfigurationSection("");
                for (String temp : dd.getKeys(false)) {
                    if (Variable.GUI_YML.getString(String.valueOf(temp) + ".InMenu") == null)
                        continue;
                    if (!Variable.GUI_YML.getString(String.valueOf(temp) + ".InMenu").equalsIgnoreCase("Visit"))
                        continue;
                    VisitGui.this.item_add_amount++;
                }
                VisitGui.this.MaxPage = 0;
                VisitGui.this.NowPage = 0;
                VisitGui.this.players.clear();
                boolean next_page = false;
                int amount = 0;
                if (Main.JavaPlugin.getConfig().getBoolean("BungeeCord")) {
                    if (Main.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("Public")) {
                        for (String world : MySQL.getAllWorlds()) {
                            Home home = HomeAPI.getHome(world);
                            if (home != null && home.isAllowStranger())
                                VisitGui.this.players.add(home);
                        }
                    } else if (Main.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("ALL")) {
                        for (String world : MySQL.getAllWorlds()) {
                            Home home = HomeAPI.getHome(world);
                            if (home != null)
                                VisitGui.this.players.add(home);
                        }
                    } else {
                        for (World world : Bukkit.getWorlds()) {
                            if (Util.CheckIsHome(world.getName())) {
                                Home home = HomeAPI.getHome(world.getName());
                                VisitGui.this.players.add(home);
                            }
                        }
                    }
                    VisitGui.this.MaxPage = (int) Math.ceil(Math.ceil(VisitGui.this.players.size()) / 21);
                } else {
                    File folder = new File(Variable.Tempf);
                    byte b;
                    int j;
                    File[] arrayOfFile;
                    for (j = (arrayOfFile = folder.listFiles()).length, b = 0; b < j; ) {
                        File temp = arrayOfFile[b];
                        String want_to = temp.getPath().replace(Variable.Tempf, "").replace(".yml", "").replace(Variable.file_loc_prefix, "");
                        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(temp);
                        if (Main.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("Public")) {
                            if (HomeAPI.getHome(want_to.replace(Variable.world_prefix, "")).isAllowStranger())
                                VisitGui.this.players.add(HomeAPI.getHome(want_to.replace(Variable.world_prefix, "")));
                        } else if (Main.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("ALL")) {
                            VisitGui.this.players.add(HomeAPI.getHome(want_to.replace(Variable.world_prefix, "")));
                        } else if (Bukkit.getWorld(String.valueOf(Variable.world_prefix) + want_to) != null) {
                            Home home = HomeAPI.getHome(want_to);
                            VisitGui.this.players.add(home);
                        }
                        b++;
                    }
                    // Caishangqi
                    /*
                     * 19-25
                     * 28-35
                     * 37-43
                     */
                    // 21 is the size of allocated container number of players
                    VisitGui.this.MaxPage = (int) Math.ceil((double) VisitGui.this.players.size() / 21);
                }
                List<VisitStatistic> vst_list = new ArrayList<>();
                for (Home home : VisitGui.this.players) {
                    double value = home.getFlowers() * Main.JavaPlugin.getConfig().getDouble("FlowerAdd") + home.getPopularity() * Main.JavaPlugin.getConfig().getDouble("PopularityAdd");
                    VisitStatistic vst = new VisitStatistic(home, value);
                    vst_list.add(vst);
                }
                for (int i = 0; i < vst_list.size() - 1; i++) {
                    for (int j = 0; j < vst_list.size() - 1 - i; j++) {
                        if (((VisitStatistic) vst_list.get(j)).value < ((VisitStatistic) vst_list.get(j + 1)).value) {
                            VisitStatistic temp = vst_list.get(j);
                            vst_list.set(j, vst_list.get(j + 1));
                            vst_list.set(j + 1, temp);
                        }
                    }
                }
                VisitGui.this.players.clear();
                for (VisitStatistic vst : vst_list)
                    VisitGui.this.players.add(vst.home);
                VisitGui.this.MainGui.clear();
                if (Variable.GUI_YML.getBoolean("EnableVisitGuiNormalPane"))
                    VisitGui.this.setupPane();
                VisitGui.this.setupNavigationButtons();
                VisitGui.this.setupMenuItems();
                amount = 0;
                // Caishangqi
                for (int c = VisitGui.this.NowPage * (21); c < VisitGui.this.players.size() && c < (VisitGui.this.NowPage + 1) * (21) && c >= 0; c++) {
                    if (Util.CheckIsHome((VisitGui.this.players.get(c)).getName().replace(Variable.world_prefix, "")))
                        VisitGui.this.setupPlayerHead(VisitGui.this.players.get(c), ContentMatrixTransform.GetTransformedSlotIndex(c, 21, 7));
                }
            }
        }).runTaskAsynchronously((Plugin) Main.JavaPlugin);
    }

    private void setupPane() {
        try {
            ItemStack itemStack = new ItemStack(Material.valueOf(Variable.GUI_YML.getString("PaneMaterial")));
        } catch (Exception e) {
            String temp5 = Variable.Lang_YML.getString("GlassPaneNotFound");
            if (temp5.contains("<Material>"))
                temp5 = temp5.replace("<Material>", Variable.GUI_YML.getString("PaneMaterial"));
            Bukkit.getConsoleSender().sendMessage(temp5);
            return;
        }
        ItemStack blb1 = new ItemStack(Material.valueOf(Variable.GUI_YML.getString("PaneMaterial")));
        blb1.setDurability((short) 15);
        ItemMeta i1 = blb1.getItemMeta();
        i1.setDisplayName("");
        blb1.setItemMeta(i1);
        int i;
        for (i = 0; i < 9; i++)
            this.MainGui.setItem(i, blb1);
        this.MainGui.setItem(9, blb1);
        this.MainGui.setItem(18, blb1);
        this.MainGui.setItem(27, blb1);
        this.MainGui.setItem(17, blb1);
        this.MainGui.setItem(26, blb1);
        this.MainGui.setItem(35, blb1);
        this.MainGui.setItem(36, blb1);
        this.MainGui.setItem(44, blb1);
        for (i = 45; i < 54; i++) {
            if (i != 49)
                this.MainGui.setItem(i, blb1);
        }
    }

    private void setupNavigationButtons() {
        ConfigurationSection nextSection = Variable.GUI_YML.getConfigurationSection("NextButton");
        if (nextSection != null) {
            ItemStack next = new ItemStack(Material.valueOf(nextSection.getString("Material")));
            ItemMeta nextMeta = next.getItemMeta();
            nextMeta.setDisplayName(nextSection.getString("CustomName"));
            next.setItemMeta(nextMeta);
            if (nextSection.contains("CustomModelData")) {
                NBTItem nbtNextItem = new NBTItem(next);
                nbtNextItem.setInteger("CustomModelData", Integer.valueOf(nextSection.getInt("CustomModelData")));
                next = nbtNextItem.getItem();
            }
            // Caishangqi
            this.MainGui.setItem(52, VisitGUIHook.HookNextButtonState(next, this));
        }
        ConfigurationSection prevSection = Variable.GUI_YML.getConfigurationSection("PrevButton");
        if (prevSection != null) {
            ItemStack prev = new ItemStack(Material.valueOf(prevSection.getString("Material")));
            ItemMeta prevMeta = prev.getItemMeta();
            prevMeta.setDisplayName(prevSection.getString("CustomName"));
            prev.setItemMeta(prevMeta);
            if (prevSection.contains("CustomModelData")) {
                NBTItem nbtPrevItem = new NBTItem(prev);
                nbtPrevItem.setInteger("CustomModelData", Integer.valueOf(prevSection.getInt("CustomModelData")));
                prev = nbtPrevItem.getItem();
            }
            // Caishangqi
            this.MainGui.setItem(46, VisitGUIHook.HookPrevButtonState(prev, this));
        }
    }

    private void setupMenuItems() {
        ConfigurationSection cs = Variable.GUI_YML.getConfigurationSection("");
        for (String temp : cs.getKeys(false)) {
            if (Variable.GUI_YML.getString(String.valueOf(temp) + ".InMenu") == null)
                continue;
            if (!Variable.GUI_YML.getString(String.valueOf(temp) + ".InMenu").equalsIgnoreCase("Visit"))
                continue;
            try {
                ItemStack itemStack = new ItemStack(Material.valueOf(Variable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            } catch (Exception e) {
                String temp5 = Variable.Lang_YML.getString("MaterialNotFound");
                if (temp5.contains("<Material>"))
                    temp5 = temp5.replace("<Material>", Variable.GUI_YML.getString(String.valueOf(temp) + ".Material"));
                if (temp5.contains("<ID>"))
                    temp5 = temp5.replace("<ID>", temp);
                Bukkit.getConsoleSender().sendMessage(temp5);
                return;
            }
            ItemStack item = new ItemStack(Material.valueOf(Variable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            if (Variable.GUI_YML.getInt(String.valueOf(temp) + ".SubID") != 0)
                item.setDurability((short) Variable.GUI_YML.getInt(String.valueOf(temp) + ".SubID"));
            ItemMeta meta = item.getItemMeta();
            List<String> lores = new ArrayList<>();
            meta.setDisplayName(Variable.GUI_YML.getString(String.valueOf(temp) + ".CustomName"));
            for (int i = 0; i < Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").size(); i++) {
                String tempstr = Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").get(i);
                try {
                    tempstr = PlaceholderAPI.setPlaceholders(null, tempstr);
                } catch (Exception exception) {
                }
                lores.add(tempstr);
            }
            for (int c = 0; c < Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").size(); c++) {
                String[] tempenc = ((String) Variable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").get(c)).split(",");
                meta.addEnchant(Enchantment.getByName(tempenc[0]), Integer.valueOf(tempenc[1]).intValue(), true);
            }
            meta.setLore(lores);
            item.setItemMeta(meta);
            if (Variable.GUI_YML.contains(String.valueOf(temp) + ".CustomModelData")) {
                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setInteger("CustomModelData", Integer.valueOf(Variable.GUI_YML.getInt(String.valueOf(temp) + ".CustomModelData")));
                item = nbtItem.getItem();
            }
            this.MainGui.setItem(Variable.GUI_YML.getInt(String.valueOf(temp) + ".Index") - 1, item);
        }
    }

    private void setupPlayerHead(Home home, int slotIndex) {


        if (Variable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("HEAD") || Variable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("SKULL")) {
            try {
                ItemStack itemStack = new ItemStack(Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")), 1, (short) SkullType.PLAYER.ordinal());
            } catch (Exception e) {
                String temp5 = Variable.Lang_YML.getString("PlayerHeadMaterialNotFound");
                if (temp5.contains("<Material>"))
                    temp5 = temp5.replace("<Material>", Variable.GUI_YML.getString("HeadMaterial"));
                Bukkit.getConsoleSender().sendMessage(temp5);
                return;
            }
            ItemStack skull = new ItemStack(Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")), 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta player_SKULL = (SkullMeta) skull.getItemMeta();
            Player temp_p = Bukkit.getPlayer(home.getName().replace(Variable.world_prefix, ""));
            if (Variable.GUI_YML.getBoolean("EnableSkullSkin") && temp_p != null)
                try {
                    player_SKULL.setOwningPlayer((OfflinePlayer) temp_p);
                } catch (Exception exception) {
                }
            player_SKULL.setDisplayName(String.valueOf(Variable.Lang_YML.getString("VisitGuiHomePrefix")) + home.getName().replace(Variable.world_prefix, "") + Variable.Lang_YML.getString("VisitGuiHomeSuffix"));
            List<String> lores = new ArrayList<>();
            for (int i = 0; i < Variable.GUI_YML.getStringList("VisitGuiLores").size() - 1; i++) {
                String str = ((String) Variable.GUI_YML.getStringList("VisitGuiLores").get(i)).replace("<Name>", home.getName().replace(Variable.world_prefix, ""));
                str = PlaceholderAPI.setPlaceholders(null, str);
                lores.add(str);
            }
            if (Variable.GUI_YML.getBoolean("VisitSlogan"))
                for (String str : home.getAdvertisement())
                    lores.add(str);
            String temp = ((String) Variable.GUI_YML.getStringList("VisitGuiLores").get(Variable.GUI_YML.getStringList("VisitGuiLores").size() - 1)).replace("<Name>", home.getName().replace(Variable.world_prefix, ""));
            temp = PlaceholderAPI.setPlaceholders(null, temp);
            lores.add(temp);
            player_SKULL.setLore(lores);
            skull.setItemMeta((ItemMeta) player_SKULL);
            if (!home.getIcon().equalsIgnoreCase("")) {
                String[] item_info = home.getIcon().split(":");
                skull.setType(Material.valueOf(item_info[0]));
                skull.setDurability(Short.valueOf(item_info[1]).shortValue());
            }
            skull.setAmount(1);
            this.MainGui.setItem(slotIndex, skull);
            //this.MainGui.addItem(new ItemStack[]{skull});
        } else {
            ItemStack item = new ItemStack(Material.valueOf(Variable.GUI_YML.getString("HeadMaterial")));
            ItemMeta i_meta = item.getItemMeta();
            i_meta.setDisplayName(String.valueOf(Variable.Lang_YML.getString("VisitGuiHomePrefix")) + home.getName().replace(Variable.world_prefix, "") + Variable.Lang_YML.getString("VisitGuiHomeSuffix"));
            List<String> lores = new ArrayList<>();
            for (int i = 0; i < Variable.GUI_YML.getStringList("VisitGuiLores").size() - 1; i++) {
                String str = ((String) Variable.GUI_YML.getStringList("VisitGuiLores").get(i)).replace("<Name>", home.getName().replace(Variable.world_prefix, ""));
                str = PlaceholderAPI.setPlaceholders(null, str);
                lores.add(str);
            }
            if (Variable.GUI_YML.getBoolean("VisitSlogan"))
                for (String str : home.getAdvertisement())
                    lores.add(str);
            String temp = ((String) Variable.GUI_YML.getStringList("VisitGuiLores").get(Variable.GUI_YML.getStringList("VisitGuiLores").size() - 1)).replace("<Name>", home.getName().replace(Variable.world_prefix, ""));
            temp = PlaceholderAPI.setPlaceholders(null, temp);
            lores.add(temp);
            i_meta.setLore(lores);
            item.setItemMeta(i_meta);
            if (!home.getIcon().equalsIgnoreCase("")) {
                String[] item_info = home.getIcon().split(":");
                item.setType(Material.valueOf(item_info[0]));
                item.setDurability(Short.valueOf(item_info[1]).shortValue());
            }
            item.setAmount(1);
            this.MainGui.setItem(slotIndex, item);
            //this.MainGui.addItem(new ItemStack[]{item});
        }
    }

    public void OpenNextInventory(final Player p) {
        (new BukkitRunnable() {
            public void run() {
                if (VisitGui.this.NowPage + 2 > VisitGui.this.MaxPage)
                    return;
                VisitGui.this.NowPage++;
                VisitGui.this.MainGui.clear();
                if (Variable.GUI_YML.getBoolean("EnableVisitGuiNormalPane"))
                    VisitGui.this.setupPane();
                VisitGui.this.setupNavigationButtons();
                VisitGui.this.setupMenuItems();
                int amount = 0;
                // Caishangqi
                for (int c = VisitGui.this.NowPage * (21); c < VisitGui.this.players.size() && c < (VisitGui.this.NowPage + 1) * (21) && c >= 0; c++) {
                    if (Util.CheckIsHome((VisitGui.this.players.get(c)).getName().replace(Variable.world_prefix, "")))
                        VisitGui.this.setupPlayerHead(VisitGui.this.players.get(c), ContentMatrixTransform.GetTransformedSlotIndex(c, 21, 7));
                }
                (new BukkitRunnable() {
                    public void run() {
                        p.openInventory(VisitGui.this.MainGui);
                    }
                }).runTask((Plugin) Main.JavaPlugin);
            }
        }).runTaskAsynchronously((Plugin) Main.JavaPlugin);
    }

    public void OpenPrevInventory(final Player p) {
        (new BukkitRunnable() {
            public void run() {
                if (VisitGui.this.NowPage - 1 < 0)
                    return;
                VisitGui.this.NowPage--;
                VisitGui.this.MainGui.clear();
                if (Variable.GUI_YML.getBoolean("EnableVisitGuiNormalPane"))
                    VisitGui.this.setupPane();
                VisitGui.this.setupNavigationButtons();
                VisitGui.this.setupMenuItems();
                int amount = 0;
                // Caishangqi
                for (int c = VisitGui.this.NowPage * (21); c < VisitGui.this.players.size() && c < (VisitGui.this.NowPage + 1) * (21) && c >= 0; c++) {
                    if (Util.CheckIsHome((VisitGui.this.players.get(c)).getName().replace(Variable.world_prefix, "")))
                        VisitGui.this.setupPlayerHead(VisitGui.this.players.get(c), ContentMatrixTransform.GetTransformedSlotIndex(c, 21, 7));
                }
                (new BukkitRunnable() {
                    public void run() {
                        p.openInventory(VisitGui.this.MainGui);
                    }
                }).runTask((Plugin) Main.JavaPlugin);
            }
        }).runTaskAsynchronously((Plugin) Main.JavaPlugin);
    }

    public Inventory getInventory() {
        return this.MainGui;
    }
}
