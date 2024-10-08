package com.caizii.charmrealm.gui;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.hookers.VisitGUIHook;
import com.caizii.charmrealm.utils.Color;
import com.caizii.charmrealm.utils.*;
import de.tr7zw.nbtapi.NBT;
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

import static com.caizii.charmrealm.library.RealmVisualLibrary.getPlayerRealmDisplayName;

public class VisitGui implements InventoryHolder {
    // CharmRealm.pluginVariable.GUI_YML.getString("VisitTitle")
    public Inventory MainGui = Bukkit.createInventory(this, 54, Color.parseColorAndPlaceholder(null, CharmRealm.pluginVariable.GUI_YML.getString("VisitTitle")));

    public int MaxPage = 0;

    public int NowPage = 0;

    public ArrayList<Home> players = new ArrayList<>();

    int item_add_amount = 0;

    public VisitGui() {
        (new BukkitRunnable() {
            public void run() {
                ConfigurationSection dd = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("");
                for (String temp : dd.getKeys(false)) {
                    if (CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".InMenu") == null)
                        continue;
                    if (!CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".InMenu").equalsIgnoreCase("Visit"))
                        continue;
                    VisitGui.this.item_add_amount++;
                }
                VisitGui.this.MaxPage = 0;
                VisitGui.this.NowPage = 0;
                VisitGui.this.players.clear();
                boolean next_page = false;
                int amount = 0;
                if (CharmRealm.JavaPlugin.getConfig().getBoolean("BungeeCord")) {
                    if (CharmRealm.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("Public")) {
                        for (String world : MySQL.getAllWorlds()) {
                            Home home = HomeAPI.getHome(world);
                            if (home != null && home.isAllowStranger())
                                VisitGui.this.players.add(home);
                        }
                    } else if (CharmRealm.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("ALL")) {
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
                    File folder = new File(CharmRealm.pluginVariable.Tempf);
                    byte b;
                    int j;
                    File[] arrayOfFile;
                    for (j = (arrayOfFile = folder.listFiles()).length, b = 0; b < j; ) {
                        File temp = arrayOfFile[b];
                        String want_to = temp.getPath().replace(CharmRealm.pluginVariable.Tempf, "").replace(".yml", "").replace(CharmRealm.pluginVariable.file_loc_prefix, "");
                        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(temp);
                        if (CharmRealm.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("Public")) {
                            if (HomeAPI.getHome(want_to.replace(CharmRealm.pluginVariable.world_prefix, "")).isAllowStranger())
                                VisitGui.this.players.add(HomeAPI.getHome(want_to.replace(CharmRealm.pluginVariable.world_prefix, "")));
                        } else if (CharmRealm.JavaPlugin.getConfig().getString("VisitGuiShowAll").equalsIgnoreCase("ALL")) {
                            VisitGui.this.players.add(HomeAPI.getHome(want_to.replace(CharmRealm.pluginVariable.world_prefix, "")));
                        } else if (Bukkit.getWorld(String.valueOf(CharmRealm.pluginVariable.world_prefix) + want_to) != null) {
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
                    double value = home.getFlowers() * CharmRealm.JavaPlugin.getConfig().getDouble("FlowerAdd") + home.getPopularity() * CharmRealm.JavaPlugin.getConfig().getDouble("PopularityAdd");
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
                if (CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableVisitGuiNormalPane"))
                    VisitGui.this.setupPane();
                VisitGui.this.setupNavigationButtons();
                VisitGui.this.setupMenuItems();
                amount = 0;
                // Caishangqi
                for (int c = VisitGui.this.NowPage * (21); c < VisitGui.this.players.size() && c < (VisitGui.this.NowPage + 1) * (21) && c >= 0; c++) {
                    if (Util.CheckIsHome((VisitGui.this.players.get(c)).getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
                        VisitGui.this.setupPlayerHead(VisitGui.this.players.get(c), ContentMatrixTransform.GetTransformedSlotIndex(c, 21, 7));
                }
            }
        }).runTaskAsynchronously((Plugin) CharmRealm.JavaPlugin);
    }

    private void setupPane() {
        try {
            ItemStack itemStack = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString("PaneMaterial")));
        } catch (Exception e) {
            String temp5 = CharmRealm.pluginVariable.Lang_YML.getString("GlassPaneNotFound");
            if (temp5.contains("<Material>"))
                temp5 = temp5.replace("<Material>", CharmRealm.pluginVariable.GUI_YML.getString("PaneMaterial"));
            Bukkit.getConsoleSender().sendMessage(temp5);
            return;
        }
        ItemStack blb1 = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString("PaneMaterial")));
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
        ConfigurationSection nextSection = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("NextButton");
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
        ConfigurationSection prevSection = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("PrevButton");
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
        ConfigurationSection cs = CharmRealm.pluginVariable.GUI_YML.getConfigurationSection("");
        for (String temp : cs.getKeys(false)) {
            if (CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".InMenu") == null)
                continue;
            if (!CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".InMenu").equalsIgnoreCase("Visit"))
                continue;
            try {
                ItemStack itemStack = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            } catch (Exception e) {
                String temp5 = CharmRealm.pluginVariable.Lang_YML.getString("MaterialNotFound");
                if (temp5.contains("<Material>"))
                    temp5 = temp5.replace("<Material>", CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".Material"));
                if (temp5.contains("<ID>"))
                    temp5 = temp5.replace("<ID>", temp);
                Bukkit.getConsoleSender().sendMessage(temp5);
                return;
            }
            ItemStack item = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".Material")));
            if (CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".SubID") != 0)
                item.setDurability((short) CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".SubID"));
            ItemMeta meta = item.getItemMeta();
            List<String> lores = new ArrayList<>();
            meta.setDisplayName(CharmRealm.pluginVariable.GUI_YML.getString(String.valueOf(temp) + ".CustomName"));
            for (int i = 0; i < CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").size(); i++) {
                String tempstr = CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Lores").get(i);
                try {
                    tempstr = PlaceholderAPI.setPlaceholders(null, tempstr);
                } catch (Exception exception) {
                }
                lores.add(Color.parseColorAndPlaceholder(null, tempstr));
            }
            for (int c = 0; c < CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").size(); c++) {
                String[] tempenc = ((String) CharmRealm.pluginVariable.GUI_YML.getStringList(String.valueOf(temp) + ".Enchants").get(c)).split(",");
                meta.addEnchant(Enchantment.getByName(tempenc[0]), Integer.valueOf(tempenc[1]).intValue(), true);
            }
            meta.setLore(lores);
            item.setItemMeta(meta);
            if (CharmRealm.pluginVariable.GUI_YML.contains(String.valueOf(temp) + ".CustomModelData")) {
                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setInteger("CustomModelData", CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".CustomModelData"));
                item = nbtItem.getItem();
            }
            this.MainGui.setItem(CharmRealm.pluginVariable.GUI_YML.getInt(String.valueOf(temp) + ".Index") - 1, item);
        }
    }

    private void setupPlayerHead(Home home, int slotIndex) {


        if (CharmRealm.pluginVariable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("HEAD") || CharmRealm.pluginVariable.GUI_YML.getString("HeadMaterial").toUpperCase().contains("SKULL")) {
            try {
                ItemStack itemStack = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString("HeadMaterial")), 1, (short) SkullType.PLAYER.ordinal());
            } catch (Exception e) {
                String temp5 = CharmRealm.pluginVariable.Lang_YML.getString("PlayerHeadMaterialNotFound");
                if (temp5.contains("<Material>"))
                    temp5 = temp5.replace("<Material>", CharmRealm.pluginVariable.GUI_YML.getString("HeadMaterial"));
                Bukkit.getConsoleSender().sendMessage(temp5);
                return;
            }
            ItemStack skull = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString("HeadMaterial")), 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta player_SKULL = (SkullMeta) skull.getItemMeta();
            Player player = Bukkit.getPlayer(home.getName().replace(CharmRealm.pluginVariable.world_prefix, ""));


            if (CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableSkullSkin")) {
                if (player != null) {
                    // player online
                    player_SKULL.setOwningPlayer(player);
                } else {
                    // player offline
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(home.getName().replace(CharmRealm.pluginVariable.world_prefix, ""));
                    player_SKULL.setOwningPlayer(offlinePlayer);
                }
            }

            /* 这里必须传入玩家实例让luck-perms区调用,如果你想使用其他插件的 placeholders */
            if (player != null) {

                // player online
                String PlaceholderParsed = PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(home.getPlayerOwnerUUID()), (CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomePrefix") + home.getName().replace(CharmRealm.pluginVariable.world_prefix, "") + CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomeSuffix")));
                player_SKULL.setDisplayName(Color.parseColor(PlaceholderParsed));

            } else {
                
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(home.getName().replace(CharmRealm.pluginVariable.world_prefix, ""));
                String playerRealmDisplayName = getPlayerRealmDisplayName(offlinePlayer.getName());
                player_SKULL.setDisplayName(Color.parseColor(playerRealmDisplayName));

            }

            List<String> lores = new ArrayList<>();
            for (int i = 0; i < CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").size() - 1; i++) {
                String str = Color.parseColor(((String) CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").get(i)).replace("<Name>", home.getName().replace(CharmRealm.pluginVariable.world_prefix, "")));
                str = PlaceholderAPI.setPlaceholders(null, str);
                lores.add(str);
            }
            if (CharmRealm.pluginVariable.GUI_YML.getBoolean("VisitSlogan"))
                for (String str : home.getAdvertisement())
                    lores.add(str);
            String temp = Color.parseColor(((String) CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").get(CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").size() - 1)).replace("<Name>", home.getName().replace(CharmRealm.pluginVariable.world_prefix, "")));
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
            NBT.modify(skull, nbt -> {
                nbt.setString("ButtonClass", "VisitGUI.PlayerHead");
                nbt.setString("Owner", home.getName());
            });
            this.MainGui.setItem(slotIndex, skull);
            //this.MainGui.addItem(new ItemStack[]{skull});
        } else {
            ItemStack item = new ItemStack(Material.valueOf(CharmRealm.pluginVariable.GUI_YML.getString("HeadMaterial")));
            ItemMeta i_meta = item.getItemMeta();


            /* 这里必须传入玩家实例让luck-perms区调用,如果你想使用其他插件的 placeholders */
            String PlaceholderParsed = PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(home.getPlayerOwnerUUID()), (String.valueOf(CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomePrefix")) + home.getName().replace(CharmRealm.pluginVariable.world_prefix, "") + CharmRealm.pluginVariable.Lang_YML.getString("VisitGuiHomeSuffix")));

            i_meta.setDisplayName(Color.parseColor(PlaceholderParsed));
            List<String> lores = new ArrayList<>();
            for (int i = 0; i < CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").size() - 1; i++) {
                String str = (Color.parseColor((String) CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").get(i)).replace("<Name>", home.getName().replace(CharmRealm.pluginVariable.world_prefix, "")));
                str = PlaceholderAPI.setPlaceholders(null, str);
                lores.add(str);
            }
            if (CharmRealm.pluginVariable.GUI_YML.getBoolean("VisitSlogan"))
                for (String str : home.getAdvertisement())
                    lores.add(str);
            String temp = Color.parseColor(((String) CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").get(CharmRealm.pluginVariable.GUI_YML.getStringList("VisitGuiLores").size() - 1)).replace("<Name>", home.getName().replace(CharmRealm.pluginVariable.world_prefix, "")));
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
            NBT.modify(item, nbt -> {
                nbt.setString("ButtonClass", "VisitGUI.PlayerHead");
                nbt.setString("Owner", home.getName());
            });

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
                if (CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableVisitGuiNormalPane"))
                    VisitGui.this.setupPane();
                VisitGui.this.setupNavigationButtons();
                VisitGui.this.setupMenuItems();
                int amount = 0;
                // Caishangqi
                for (int c = VisitGui.this.NowPage * (21); c < VisitGui.this.players.size() && c < (VisitGui.this.NowPage + 1) * (21) && c >= 0; c++) {
                    if (Util.CheckIsHome((VisitGui.this.players.get(c)).getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
                        VisitGui.this.setupPlayerHead(VisitGui.this.players.get(c), ContentMatrixTransform.GetTransformedSlotIndex(c, 21, 7));
                }
                (new BukkitRunnable() {
                    public void run() {
                        p.openInventory(VisitGui.this.MainGui);
                    }
                }).runTask((Plugin) CharmRealm.JavaPlugin);
            }
        }).runTaskAsynchronously((Plugin) CharmRealm.JavaPlugin);
    }

    public void OpenPrevInventory(final Player p) {
        (new BukkitRunnable() {
            public void run() {
                if (VisitGui.this.NowPage - 1 < 0)
                    return;
                VisitGui.this.NowPage--;
                VisitGui.this.MainGui.clear();
                if (CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableVisitGuiNormalPane"))
                    VisitGui.this.setupPane();
                VisitGui.this.setupNavigationButtons();
                VisitGui.this.setupMenuItems();
                int amount = 0;
                // Caishangqi
                for (int c = VisitGui.this.NowPage * (21); c < VisitGui.this.players.size() && c < (VisitGui.this.NowPage + 1) * (21) && c >= 0; c++) {
                    if (Util.CheckIsHome((VisitGui.this.players.get(c)).getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
                        VisitGui.this.setupPlayerHead(VisitGui.this.players.get(c), ContentMatrixTransform.GetTransformedSlotIndex(c, 21, 7));
                }
                (new BukkitRunnable() {
                    public void run() {
                        p.openInventory(VisitGui.this.MainGui);
                    }
                }).runTask((Plugin) CharmRealm.JavaPlugin);
            }
        }).runTaskAsynchronously((Plugin) CharmRealm.JavaPlugin);
    }

    public Inventory getInventory() {
        return this.MainGui;
    }
}
