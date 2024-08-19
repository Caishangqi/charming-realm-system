package com.caizii.charmrealm;

import com.caizii.charmrealm.gui.*;
import com.caizii.charmrealm.gui.handler.CharmGUIHandler;
import com.caizii.charmrealm.gui.handler.TitleHandler;
import com.caizii.charmrealm.library.RealmConfigLibrary;
import com.caizii.charmrealm.listeners.*;
import com.caizii.charmrealm.listeners.createtask.RealmCreateListener;
import com.caizii.charmrealm.listeners.plugin.PluginReloadListener;
import com.caizii.charmrealm.manager.PluginConfigManager;
import com.caizii.charmrealm.manager.PluginEventManager;
import com.caizii.charmrealm.manager.RealmGeneratorManager;
import com.caizii.charmrealm.placeHolder.API;
import com.caizii.charmrealm.placeHolder.RealmExpansion;
import com.caizii.charmrealm.utils.*;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class CharmRealm extends JavaPlugin implements PluginMessageListener {

    public static JavaPlugin JavaPlugin;
    public static CharmRealm plugin;

    public final static String packageName = CharmRealm.class.getPackageName();

    private ProtocolManager protocolManager;
    public static TitleHandler titleHandler;
    public static PluginEventManager pluginEventManager;
    public static CharmGUIHandler charmGuiHandler;
    public static RealmGeneratorManager realmGeneratorManager;
    public static PluginConfigManager pluginConfigManager;

    public static Variable pluginVariable;

    public static boolean isOSLinux() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1)
            return true;
        return false;
    }

    public static CharmRealm getInstance() {
        return plugin;
    }

    @Override
    public void onLoad() {
        JavaPlugin = this;
        // Get protocol manager
        this.protocolManager = ProtocolLibrary.getProtocolManager();

    }

    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory() != null) {
                InventoryHolder inv = p.getOpenInventory().getTopInventory().getHolder();
                if (inv instanceof CheckGui || inv instanceof CreateGui || inv instanceof DenyGui ||
                        inv instanceof InviteGui || inv instanceof MainGui || inv instanceof ManageGui ||
                        inv instanceof ManageGui2 || inv instanceof TrustGui || inv instanceof VisitGui || inv instanceof GiftGui) {
                    p.sendMessage("§a§l§m--------------" + CharmRealm.pluginVariable.Prefix + "§a§l§m--------------");
                    p.sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("CloseGuiWhenPluginReload"));
                    p.sendMessage("§a§l§m--------------" + CharmRealm.pluginVariable.Prefix + "§a§l§m--------------");
                    p.closeInventory();
                }
            }
        }
        for (World temp : Bukkit.getWorlds()) {
            if (CharmRealm.pluginVariable.hololist.containsKey(String.valueOf(CharmRealm.pluginVariable.prefix_p) + temp.getName()))
                for (Hologram temp2 : CharmRealm.pluginVariable.hololist.get(String.valueOf(CharmRealm.pluginVariable.prefix_p) + temp.getName()))
                    temp2.delete();
        }
        for (World temp : Bukkit.getWorlds()) {
            boolean is_jump = false;
            for (int i = 0; i < JavaPlugin.getConfig().getStringList("UnAutoSaveWorlds").size(); i++) {
                String str = JavaPlugin.getConfig().getStringList("UnAutoSaveWorlds").get(i);
                if (str.equalsIgnoreCase(String.valueOf(CharmRealm.pluginVariable.prefix_p) + temp.getName().replace(CharmRealm.pluginVariable.world_prefix, ""))) {
                    is_jump = true;
                    break;
                }
            }
            if (!is_jump)
                temp.save();
        }

        realmGeneratorManager.cleanAllTasks();
        realmGeneratorManager.shutdown();
        TimeAsync.cancelAsyncTime();

        Bukkit.getConsoleSender().sendMessage("\n");
        Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("AutoSaveSuccess"));
        getServer().getPluginManager().disablePlugin((Plugin) this);
        Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("DisablePlugin"));
        Bukkit.getConsoleSender().sendMessage("\n");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        CharmRealm.pluginVariable.econ = (Economy) rsp.getProvider();
        return (CharmRealm.pluginVariable.econ != null);
    }

    public static boolean setupPlayerPoints() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("PlayerPoints");
        CharmRealm.pluginVariable.playerPoints = PlayerPoints.class.cast(plugin);
        return (CharmRealm.pluginVariable.playerPoints != null);
    }

    public void onEnable() {

        JavaPlugin = this;
        plugin = this;

        RealmConfigLibrary.displayPluginBanner();

        // 注册指定包下的事件监听器
        pluginEventManager = new PluginEventManager();
        Bukkit.getPluginManager().registerEvents(new RealmCreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PluginReloadListener(), this);
        //pluginEventManager.registerEventsByPackage("createtask");

        charmGuiHandler = new CharmGUIHandler();
        titleHandler = new TitleHandler(this, protocolManager);
        titleHandler.registerPacketListeners();

        realmGeneratorManager = new RealmGeneratorManager(5);
        pluginConfigManager = new PluginConfigManager();


        pluginVariable = new Variable();
        init();

        int pluginId = 19436;
        Metrics ms = new Metrics(JavaPlugin, pluginId);
        CharmRealm.pluginVariable.NMS_Version = Bukkit.getServer().getClass().getPackage().toString().substring(Bukkit.getServer().getClass().getPackage().toString().lastIndexOf(".") + 1, Bukkit.getServer().getClass().getPackage().toString().length()).replace("V", "v");
        boolean isArclight = Bukkit.getServer().getName().equalsIgnoreCase("arclight");
        if (isArclight)
            Bukkit.getConsoleSender().sendMessage("检测到 Arclight 服务器,启用适用于Arclight的方式");
        if (!JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport")) {
            Bukkit.getPluginManager().registerEvents((Listener) new CreatureSpawnListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) charmGuiHandler, (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new InteractBlackListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new BlockBreakListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new BlockPlaceListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new HomeProtectInteractListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new InventoryOpenListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new LivingEntityProtectInHomeListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerDamageInHomeListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerDropListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerPickupListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerTeleportListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new WeatherChangeListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new WorldLoadListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new BlockPlaceListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new WorldBlockPlaceListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new HomeProtectPlaceListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new InteractMenuListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new TeleportHomeProtectListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerDeathListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerRespawnListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new EntityInteractByEntityListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new FarmProtectListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new GiftGuiCloseListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new GiftGuiClickItemListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerQuitListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerChatListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerJoinListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new PortalCreateListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new WorldInitListener(), (Plugin) this);
            Bukkit.getPluginManager().registerEvents((Listener) new FrameProtectListener(), (Plugin) this);
            if (JavaPlugin.getConfig().getBoolean("EnableAsnycTime"))
                TimeAsync.asyncTime();
        }
        Bukkit.getPluginManager().registerEvents((Listener) new InventoryClickListener(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new InventoryDragListener(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new InventoryPickupItemListener(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new InventoryMoveItemListener(), (Plugin) this);
        if (JavaPlugin.getConfig().getBoolean("EnableHeightLimit")) {
            Bukkit.getPluginManager().registerEvents((Listener) new MaxHeightPlaceListener(), (Plugin) this);
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("EnableHeightLimit"));
        }
        if (JavaPlugin.getConfig().getBoolean("BungeeCord")) {
            if (JavaPlugin.getConfig().getBoolean("AutoReCreateInLowerLagHome") && !JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
                MySQL.autoUpdateServer();
            getServer().getMessenger().registerOutgoingPluginChannel((Plugin) this, "BungeeCord");
            getServer().getMessenger().registerIncomingPluginChannel((Plugin) this, "BungeeCord", this);
        }
        if (!JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport") &&
                JavaPlugin.getConfig().getBoolean("EnableMoveListener")) {
            Bukkit.getPluginManager().registerEvents((Listener) new PlayerMoveListener(), (Plugin) this);
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("EnableMoveListener"));
        }
        Bukkit.getPluginCommand("realm").setExecutor(new CommandListener());
        Bukkit.getPluginCommand("realm").setTabCompleter((TabCompleter) new CommandListener());
        CharmRealm.pluginVariable.Prefix = getConfig().getString("Prefix");
        if (!JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport")) {
            if (JavaPlugin.getServer().getPluginManager().getPlugin("Vault") == null) {
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("NotHookVault"));
                getServer().getPluginManager().disablePlugin((Plugin) this);
                return;
            }
            setupEconomy();
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("HookVault"));
            if (JavaPlugin.getServer().getPluginManager().getPlugin("PlayerPoints") == null) {
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("NotHookPlayerPoints"));
            } else {
                setupPlayerPoints();
                CharmRealm.pluginVariable.PlyaerPointsModule = true;
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("HookPlayerPoints"));
            }
            if (JavaPlugin.getServer().getPluginManager().getPlugin("NBTAPI") == null) {
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("NotHookNBTAPI"));
            } else {
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("HookNBTAPI"));
            }
            if (JavaPlugin.getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("NotHookProtocolLib"));
            } else {
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("HookProtocolLib"));
            }
            if (JavaPlugin.getServer().getPluginManager().getPlugin("HolographicDisplays") == null) {
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("NotHookHolographicDisplays"));
                CharmRealm.pluginVariable.Hologram_switch = false;
            } else {
                CharmRealm.pluginVariable.Hologram_switch = true;
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("HookHolographicDisplays"));
            }
            if (JavaPlugin.getServer().getPluginManager().getPlugin("Multiverse-Core") != null && JavaPlugin.getConfig().getBoolean("MultiverseCoreCompability")) {
                CharmRealm.pluginVariable.hook_multiverseCore = true;
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("MultiverseCoreCompability"));
            }
            if (JavaPlugin.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit") != null && JavaPlugin.getConfig().getBoolean("FaweSwitch")) {
                CharmRealm.pluginVariable.hook_FastAsyncWorldEdit = true;
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("FaweAndWorldEditCompability"));
            }
        }
        if (JavaPlugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("NotHookPlaceholderAPI"));
        } else {
            API api = new API();
            RealmExpansion timeExpansionApi = new RealmExpansion();
            timeExpansionApi.register();
            api.register();
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("HookPlaceholderAPI"));
        }
        init.init();
        if (JavaPlugin.getConfig().getBoolean("CheckUpdate"))
            CheckUpdate.checkUpdate();
    }

    public static void init() {
        if (isOSLinux()) {
            CharmRealm.pluginVariable.linux_os = true;
            CharmRealm.pluginVariable.file_loc_prefix = "/";
        } else {
            CharmRealm.pluginVariable.file_loc_prefix = "\\";
        }
        CharmRealm.pluginVariable.prefix_p = JavaPlugin.getConfig().getString("WorldPrefix");
        if (Bukkit.getVersion().toString().toUpperCase().contains("THERMOS"))
            CharmRealm.pluginVariable.has_no_click_message = true;
        if (Bukkit.getVersion().toString().toUpperCase().contains("CATSERVER") ||
                Bukkit.getVersion().toString().toUpperCase().contains("URANIUM") ||
                Bukkit.getVersion().toString().toUpperCase().contains("KCAULDRON") ||
                Bukkit.getVersion().toString().toUpperCase().contains("THERMOS") ||
                Bukkit.getVersion().toString().toUpperCase().contains("MOHIST")) {
            CharmRealm.pluginVariable.world_prefix = "";
            CharmRealm.pluginVariable.Cat_Check = true;
        } else {
            CharmRealm.pluginVariable.world_prefix = "CharmRealm/";
        }
        if (Bukkit.getVersion().toString().contains("1.7.10")) {
            CharmRealm.pluginVariable.world_prefix = "";
            CharmRealm.pluginVariable.Cat_Check = true;
        }
        if (Bukkit.getVersion().toString().contains("1.16.5")) {
            CharmRealm.pluginVariable.world_prefix = "";
            CharmRealm.pluginVariable.Cat_Check = true;
        }
        if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT")) {
            CharmRealm.pluginVariable.world_prefix = "";
            CharmRealm.pluginVariable.Cat_Check = false;
        }
        if (Bukkit.getVersion().toString().contains("1.20.1") && Bukkit.getVersion().toString().toUpperCase().contains("1.20.1")) {
            CharmRealm.pluginVariable.world_prefix = "";
            CharmRealm.pluginVariable.Cat_Check = true;
        }
        if (Bukkit.getVersion().toString().contains("1.20.1") && Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT")) {
            CharmRealm.pluginVariable.world_prefix = "";
            CharmRealm.pluginVariable.Cat_Check = true;
        }
        if (Bukkit.getVersion().toString().contains("Banner") && Bukkit.getVersion().toString().toUpperCase().contains("1.20.1"))
            CharmRealm.pluginVariable.world_prefix = "CharmRealm/";
        pluginConfigManager.initConfig();
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "guis" + CharmRealm.pluginVariable.file_loc_prefix + "gui_setting.yml")).exists())
            JavaPlugin.saveResource("guis" + CharmRealm.pluginVariable.file_loc_prefix + "gui_setting.yml", false);
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "guis" + CharmRealm.pluginVariable.file_loc_prefix + "gui_create.yml")).exists())
            JavaPlugin.saveResource("guis" + CharmRealm.pluginVariable.file_loc_prefix + "gui_create.yml", false);
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "GUI.yml")).exists())
            JavaPlugin.saveResource("GUI.yml", false);
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "GUI_en.yml")).exists())
            JavaPlugin.saveResource("GUI_en.yml", false);
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "log.yml")).exists())
            JavaPlugin.saveResource("log.yml", false);
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "Language" + CharmRealm.pluginVariable.file_loc_prefix + "Chinese.yml")).exists())
            JavaPlugin.saveResource("Language" + CharmRealm.pluginVariable.file_loc_prefix + "Chinese.yml", false);
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "Language" + CharmRealm.pluginVariable.file_loc_prefix + "Chinese_TW.yml")).exists())
            JavaPlugin.saveResource("Language" + CharmRealm.pluginVariable.file_loc_prefix + "Chinese_TW.yml", false);
        if (!(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "Language" + CharmRealm.pluginVariable.file_loc_prefix + "English.yml")).exists())
            JavaPlugin.saveResource("Language" + CharmRealm.pluginVariable.file_loc_prefix + "English.yml", false);
        CharmRealm.pluginVariable.Lang_YML =
                (FileConfiguration) YamlConfiguration.loadConfiguration(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "Language" +
                        CharmRealm.pluginVariable.file_loc_prefix + JavaPlugin.getConfig().getString("Language") + ".yml"));
        File f = new File("");
        String Tempf0 = null;
        try {
            Tempf0 = f.getCanonicalPath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        CharmRealm.pluginVariable.Final = "";
        if (CharmRealm.pluginVariable.linux_os) {
            CharmRealm.pluginVariable.ab = Tempf0.split(CharmRealm.pluginVariable.file_loc_prefix);
        } else {
            CharmRealm.pluginVariable.ab = Tempf0.split(String.valueOf(String.valueOf(CharmRealm.pluginVariable.file_loc_prefix)) + CharmRealm.pluginVariable.file_loc_prefix);
        }
        if (isOSLinux()) {
            String[] args = Tempf0.split(CharmRealm.pluginVariable.file_loc_prefix);
            for (int i = 0; i < args.length - 1; i++)
                CharmRealm.pluginVariable.Final = String.valueOf(String.valueOf(CharmRealm.pluginVariable.Final)) + CharmRealm.pluginVariable.file_loc_prefix + args[i];
        } else {
            String[] args = Tempf0.split(String.valueOf(String.valueOf(CharmRealm.pluginVariable.file_loc_prefix)) + CharmRealm.pluginVariable.file_loc_prefix);
            for (int i = 0; i < args.length - 1; i++)
                CharmRealm.pluginVariable.Final = String.valueOf(String.valueOf(CharmRealm.pluginVariable.Final)) + CharmRealm.pluginVariable.file_loc_prefix + args[i];
        }
        CharmRealm.pluginVariable.Final = String.valueOf(String.valueOf(CharmRealm.pluginVariable.Final)) + CharmRealm.pluginVariable.file_loc_prefix;
        if (isOSLinux()) {
            CharmRealm.pluginVariable.Final = CharmRealm.pluginVariable.Final.replaceFirst(CharmRealm.pluginVariable.file_loc_prefix, "");
        } else {
            CharmRealm.pluginVariable.Final = CharmRealm.pluginVariable.Final.replaceFirst(String.valueOf(String.valueOf(CharmRealm.pluginVariable.file_loc_prefix)) + CharmRealm.pluginVariable.file_loc_prefix, "");
        }
        if (JavaPlugin.getConfig().getBoolean("BungeeCord")) {
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("EnableBungeeCord"));
            CharmRealm.pluginVariable.bungee = true;
            HikariCPUtils.setSqlConnectionPool();
            if (JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
                Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("DisableFunctionButTeleport"));
        } else {
            CharmRealm.pluginVariable.bungee = false;
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("DisableBungeeCord"));
        }
        CharmRealm.pluginVariable.custom_playerdata_location = String.valueOf(String.valueOf(CharmRealm.pluginVariable.Final)) + CharmRealm.pluginVariable.ab[CharmRealm.pluginVariable.ab.length - 1] +
                CharmRealm.pluginVariable.file_loc_prefix + "plugins" + CharmRealm.pluginVariable.file_loc_prefix + "CharmRealm" +
                CharmRealm.pluginVariable.file_loc_prefix + "playerdata";
        CharmRealm.pluginVariable.custom_autobackup_location = String.valueOf(String.valueOf(CharmRealm.pluginVariable.Final)) + CharmRealm.pluginVariable.ab[CharmRealm.pluginVariable.ab.length - 1] +
                CharmRealm.pluginVariable.file_loc_prefix + "plugins" + CharmRealm.pluginVariable.file_loc_prefix + "CharmRealm" +
                CharmRealm.pluginVariable.file_loc_prefix + "backup";
        CharmRealm.pluginVariable.server_file_world = CharmRealm.pluginVariable.Final;
        CharmRealm.pluginVariable.worldFinal = String.valueOf(String.valueOf(Tempf0)) + CharmRealm.pluginVariable.file_loc_prefix + "plugins" + CharmRealm.pluginVariable.file_loc_prefix + "CharmRealm" +
                CharmRealm.pluginVariable.file_loc_prefix;
        CharmRealm.pluginVariable.Log_All = String.valueOf(String.valueOf(Tempf0)) + CharmRealm.pluginVariable.file_loc_prefix + "plugins" + CharmRealm.pluginVariable.file_loc_prefix + "CharmRealm" +
                CharmRealm.pluginVariable.file_loc_prefix;
        CharmRealm.pluginVariable.single_server_gen = String.valueOf(String.valueOf(CharmRealm.pluginVariable.Final)) + CharmRealm.pluginVariable.file_loc_prefix + CharmRealm.pluginVariable.ab[CharmRealm.pluginVariable.ab.length - 1] +
                CharmRealm.pluginVariable.file_loc_prefix;
        CharmRealm.pluginVariable.Final = CharmRealm.pluginVariable.custom_playerdata_location;
        File check_file = new File(CharmRealm.pluginVariable.Final);
        if (!check_file.isDirectory())
            check_file.mkdir();
        File autobackup = new File(CharmRealm.pluginVariable.custom_autobackup_location);
        if (!autobackup.isDirectory())
            autobackup.mkdir();
        CharmRealm.pluginVariable.CheckIsHome = CharmRealm.pluginVariable.Final;
        File aaa = new File(CharmRealm.pluginVariable.Final);
        if (!aaa.isDirectory())
            aaa.mkdir();
        CharmRealm.pluginVariable.Tempf = CharmRealm.pluginVariable.Final;
        CharmRealm.pluginVariable.Tempf2 = CharmRealm.pluginVariable.Temp;
        CharmRealm.pluginVariable.f_log = new File(CharmRealm.pluginVariable.Log_All, "log.yml");
        if (!CharmRealm.pluginVariable.f_log.exists()) {
            try {
                CharmRealm.pluginVariable.f_log.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            CharmRealm.pluginVariable.f_log = new File(CharmRealm.pluginVariable.Log_All, "log.yml");
        }
        CharmRealm.pluginVariable.Papi_world = JavaPlugin.getConfig().getString("WorldName");
        File f2 = new File(CharmRealm.pluginVariable.worldFinal, "config.yml");
        CharmRealm.pluginVariable.getName_yml = (FileConfiguration) YamlConfiguration.loadConfiguration(f2);
        CharmRealm.pluginVariable.GUI_YML =
                (FileConfiguration) YamlConfiguration.loadConfiguration(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "GUI.yml"));
        CharmRealm.pluginVariable.GUI_SETTING_YML = (FileConfiguration) YamlConfiguration.loadConfiguration(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "guis" + CharmRealm.pluginVariable.file_loc_prefix + "gui_setting.yml"));
        CharmRealm.pluginVariable.GUI_CREATE_YML = (FileConfiguration) YamlConfiguration.loadConfiguration(new File(JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "guis" + CharmRealm.pluginVariable.file_loc_prefix + "gui_create.yml"));
        ConfigUpdate.update();
        CharmRealm.pluginVariable.Soil = JavaPlugin.getConfig().getString("SoilType");
        initHome.init();
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord"))
            return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("CharmRealm")) {
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);
            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            try {
                String[] somedata = msgin.readUTF().split(",");
                if (somedata[0].equalsIgnoreCase("waitDelayToHome")) {
                    CharmRealm.pluginVariable.wait_to_spawn_home.put(somedata[1], somedata[2]);
                } else if (somedata[0].equalsIgnoreCase("waitToCommand")) {
                    CharmRealm.pluginVariable.wait_to_command.put(somedata[1], somedata[2]);
                } else if (somedata[0].equalsIgnoreCase("waitToLoad")) {
                    final WaitToLoad wt = new WaitToLoad();
                    wt.home_name = somedata[2];
                    wt.file_loc = somedata[3];
                    MySQL.setServer(wt.home_name, JavaPlugin.getConfig().getString("Server"));
                    File new_f = null;
                    if (CharmRealm.pluginVariable.world_prefix.equalsIgnoreCase("")) {
                        if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT") || Bukkit.getVersion().toString().contains("1.20.1")) {
                            new_f = new File(String.valueOf(String.valueOf(CharmRealm.pluginVariable.single_server_gen)) + CharmRealm.pluginVariable.world_prefix + wt.home_name);
                        } else {
                            new_f = new File(
                                    String.valueOf(String.valueOf(CharmRealm.pluginVariable.single_server_gen)) + "world" + CharmRealm.pluginVariable.file_loc_prefix + wt.home_name);
                        }
                    } else {
                        new_f = new File(String.valueOf(String.valueOf(CharmRealm.pluginVariable.single_server_gen)) + CharmRealm.pluginVariable.world_prefix + wt.home_name);
                    }
                    if (new_f.isDirectory())
                        Util.deleteFile(new_f);
                    Util.copyDir(String.valueOf(String.valueOf(wt.file_loc)) + CharmRealm.pluginVariable.file_loc_prefix + wt.home_name, new_f.getAbsolutePath());
                    CharmRealm.pluginVariable.wait_to_command.put(somedata[1], "realm v " + wt.home_name);
                    CharmRealm.pluginVariable.has_already_move_world.add(somedata[1]);
                    (new BukkitRunnable() {
                        public void run() {
                            Util.deleteFile(new File(String.valueOf(String.valueOf(wt.file_loc)) + CharmRealm.pluginVariable.file_loc_prefix + wt.home_name));
                        }
                    }).runTaskLater((Plugin) JavaPlugin, 20L);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
