package com.SelfHome;

import com.Listeners.BlockBreakListener;
import com.Listeners.BlockPlaceListener;
import com.Listeners.CreatureSpawnListener;
import com.Listeners.EntityInteractByEntityListener;
import com.Listeners.FarmProtectListener;
import com.Listeners.FrameProtectListener;
import com.Listeners.GiftGuiClickItemListener;
import com.Listeners.GiftGuiCloseListener;
import com.Listeners.HomeProtectInteractListener;
import com.Listeners.HomeProtectPlaceListener;
import com.Listeners.InteractBlackListener;
import com.Listeners.InteractMenuListener;
import com.Listeners.InventoryClickListener;
import com.Listeners.InventoryDragListener;
import com.Listeners.InventoryMoveItemListener;
import com.Listeners.InventoryOpenListener;
import com.Listeners.InventoryPickupItemListener;
import com.Listeners.LivingEntityProtectInHomeListener;
import com.Listeners.MaxHeightPlaceListener;
import com.Listeners.PlayerChatListener;
import com.Listeners.PlayerDamageInHomeListener;
import com.Listeners.PlayerDeathListener;
import com.Listeners.PlayerDropListener;
import com.Listeners.PlayerJoinListener;
import com.Listeners.PlayerMoveListener;
import com.Listeners.PlayerPickupListener;
import com.Listeners.PlayerQuitListener;
import com.Listeners.PlayerRespawnListener;
import com.Listeners.PlayerTeleportListener;
import com.Listeners.PortalCreateListener;
import com.Listeners.TeleportHomeProtectListener;
import com.Listeners.WeatherChangeListener;
import com.Listeners.WorldBlockPlaceListener;
import com.Listeners.WorldInitListener;
import com.Listeners.WorldLoadListener;
import com.PlaceHolder.API;
import com.Util.CheckUpdate;
import com.Util.ConfigUpdate;
import com.Util.HikariCPUtils;
import com.Util.MySQL;
import com.Util.TimeAsync;
import com.Util.Util;
import com.Util.WaitToLoad;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
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

public class Main extends JavaPlugin implements PluginMessageListener {
  public static JavaPlugin JavaPlugin;
  
  public static boolean isOSLinux() {
    Properties prop = System.getProperties();
    String os = prop.getProperty("os.name");
    if (os != null && os.toLowerCase().indexOf("linux") > -1)
      return true; 
    return false;
  }
  
  public void onLoad() {
    JavaPlugin = this;
    init();
  }
  
  public void onDisable() {
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p.getOpenInventory() != null) {
        InventoryHolder inv = p.getOpenInventory().getTopInventory().getHolder();
        if (inv instanceof com.GUI.CheckGui || inv instanceof com.GUI.CreateGui || inv instanceof com.GUI.DenyGui || 
          inv instanceof com.GUI.InviteGui || inv instanceof com.GUI.MainGui || inv instanceof com.GUI.ManageGui || 
          inv instanceof com.GUI.ManageGui2 || inv instanceof com.GUI.TrustGui || inv instanceof com.GUI.VisitGui || inv instanceof com.GUI.GiftGui) {
          p.sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
          p.sendMessage(Variable.Lang_YML.getString("CloseGuiWhenPluginReload"));
          p.sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
          p.closeInventory();
        } 
      } 
    } 
    for (World temp : Bukkit.getWorlds()) {
      if (Variable.hololist.containsKey(String.valueOf(Variable.prefix_p) + temp.getName()))
        for (Hologram temp2 : Variable.hololist.get(String.valueOf(Variable.prefix_p) + temp.getName()))
          temp2.delete();  
    } 
    for (World temp : Bukkit.getWorlds()) {
      boolean is_jump = false;
      for (int i = 0; i < JavaPlugin.getConfig().getStringList("UnAutoSaveWorlds").size(); i++) {
        String str = JavaPlugin.getConfig().getStringList("UnAutoSaveWorlds").get(i);
        if (str.equalsIgnoreCase(String.valueOf(Variable.prefix_p) + temp.getName().replace(Variable.world_prefix, ""))) {
          is_jump = true;
          break;
        } 
      } 
      if (!is_jump)
        temp.save(); 
    } 
    Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("AutoSaveSuccess"));
    getServer().getPluginManager().disablePlugin((Plugin)this);
    Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisablePlugin"));
  }
  
  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null)
      return false; 
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null)
      return false; 
    Variable.econ = (Economy)rsp.getProvider();
    return (Variable.econ != null);
  }
  
  public static boolean setupPlayerPoints() {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PlayerPoints");
    Variable.playerPoints = PlayerPoints.class.cast(plugin);
    return (Variable.playerPoints != null);
  }
  
  public void onEnable() {
    JavaPlugin = this;
    int pluginId = 19436;
    Metrics ms = new Metrics(JavaPlugin, pluginId);
    Variable.NMS_Version = Bukkit.getServer().getClass().getPackage().toString().substring(Bukkit.getServer().getClass().getPackage().toString().lastIndexOf(".") + 1, Bukkit.getServer().getClass().getPackage().toString().length()).replace("V", "v");
    boolean isArclight = Bukkit.getServer().getName().equalsIgnoreCase("arclight");
    if (isArclight)
      Bukkit.getConsoleSender().sendMessage("检测到 Arclight 服务器,启用适用于Arclight的方式"); 
    if (!JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport")) {
      Bukkit.getPluginManager().registerEvents((Listener)new CreatureSpawnListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new InteractBlackListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new BlockBreakListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new BlockPlaceListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new HomeProtectInteractListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new InventoryOpenListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new LivingEntityProtectInHomeListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerDamageInHomeListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerDropListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerPickupListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerTeleportListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new WeatherChangeListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new WorldLoadListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new BlockPlaceListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new WorldBlockPlaceListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new HomeProtectPlaceListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new InteractMenuListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new TeleportHomeProtectListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerDeathListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerRespawnListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new EntityInteractByEntityListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new FarmProtectListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new GiftGuiCloseListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new GiftGuiClickItemListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerQuitListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerChatListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerJoinListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new PortalCreateListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new WorldInitListener(), (Plugin)this);
      Bukkit.getPluginManager().registerEvents((Listener)new FrameProtectListener(), (Plugin)this);
      if (JavaPlugin.getConfig().getBoolean("EnableAsnycTime"))
        TimeAsync.asnycTime(); 
    } 
    Bukkit.getPluginManager().registerEvents((Listener)new InventoryClickListener(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new InventoryDragListener(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new InventoryPickupItemListener(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new InventoryMoveItemListener(), (Plugin)this);
    if (JavaPlugin.getConfig().getBoolean("EnableHeightLimit")) {
      Bukkit.getPluginManager().registerEvents((Listener)new MaxHeightPlaceListener(), (Plugin)this);
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableHeightLimit"));
    } 
    if (JavaPlugin.getConfig().getBoolean("BungeeCord")) {
      if (JavaPlugin.getConfig().getBoolean("AutoReCreateInLowerLagHome") && !JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
        MySQL.autoUpdateServer(); 
      getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
      getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "BungeeCord", this);
    } 
    if (!JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport") && 
      JavaPlugin.getConfig().getBoolean("EnableMoveListener")) {
      Bukkit.getPluginManager().registerEvents((Listener)new PlayerMoveListener(), (Plugin)this);
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableMoveListener"));
    } 
    Bukkit.getPluginCommand("SH").setExecutor(new CommandListener());
    Bukkit.getPluginCommand("SH").setTabCompleter((TabCompleter)new CommandListener());
    Variable.Prefix = getConfig().getString("Prefix");
    if (!JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport")) {
      if (JavaPlugin.getServer().getPluginManager().getPlugin("Vault") == null) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("NotHookVault"));
        getServer().getPluginManager().disablePlugin((Plugin)this);
        return;
      } 
      setupEconomy();
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("HookVault"));
      if (JavaPlugin.getServer().getPluginManager().getPlugin("PlayerPoints") == null) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("NotHookPlayerPoints"));
      } else {
        setupPlayerPoints();
        Variable.PlyaerPointsModule = true;
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("HookPlayerPoints"));
      } 
      if (JavaPlugin.getServer().getPluginManager().getPlugin("NBTAPI") == null) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("NotHookNBTAPI"));
      } else {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("HookNBTAPI"));
      } 
      if (JavaPlugin.getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("NotHookProtocolLib"));
      } else {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("HookProtocolLib"));
      } 
      if (JavaPlugin.getServer().getPluginManager().getPlugin("HolographicDisplays") == null) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("NotHookHolographicDisplays"));
        Variable.Hologram_switch = false;
      } else {
        Variable.Hologram_switch = true;
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("HookHolographicDisplays"));
      } 
      if (JavaPlugin.getServer().getPluginManager().getPlugin("Multiverse-Core") != null && JavaPlugin.getConfig().getBoolean("MultiverseCoreCompability")) {
        Variable.hook_multiverseCore = true;
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("MultiverseCoreCompability"));
      } 
      if (JavaPlugin.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit") != null && JavaPlugin.getConfig().getBoolean("FaweSwitch")) {
        Variable.hook_FastAsyncWorldEdit = true;
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("FaweAndWorldEditCompability"));
      } 
    } 
    if (JavaPlugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("NotHookPlaceholderAPI"));
    } else {
      API api = new API();
      api.register();
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("HookPlaceholderAPI"));
    } 
    init.init();
    if (JavaPlugin.getConfig().getBoolean("CheckUpdate"))
      CheckUpdate.checkUpdate(); 
    Bukkit.getConsoleSender().sendMessage("§7[SelfHomeMain] §aAuthor - Tencent - QQ :  §d1242839141");
    Bukkit.getConsoleSender().sendMessage("§7[SelfHomeMain] §aTencent - Group : §d777509868");
    Bukkit.getConsoleSender().sendMessage("§7");
    Bukkit.getConsoleSender().sendMessage("§7[SelfHomeMain] §a二次维护者 - Tencent - QQ :  §d761070659");
    Bukkit.getConsoleSender().sendMessage("§7[SelfHomeMain] §a二次维护者 - Tencent - Group :  §d299852340");
  }
  
  public static void init() {
    if (isOSLinux()) {
      Variable.linux_os = true;
      Variable.file_loc_prefix = "/";
    } else {
      Variable.file_loc_prefix = "\\";
    } 
    Variable.prefix_p = JavaPlugin.getConfig().getString("WorldPrefix");
    if (Bukkit.getVersion().toString().toUpperCase().contains("THERMOS"))
      Variable.has_no_click_message = true; 
    if (Bukkit.getVersion().toString().toUpperCase().contains("CATSERVER") || 
      Bukkit.getVersion().toString().toUpperCase().contains("URANIUM") || 
      Bukkit.getVersion().toString().toUpperCase().contains("KCAULDRON") || 
      Bukkit.getVersion().toString().toUpperCase().contains("THERMOS") || 
      Bukkit.getVersion().toString().toUpperCase().contains("MOHIST")) {
      Variable.world_prefix = "";
      Variable.Cat_Check = true;
    } else {
      Variable.world_prefix = "SelfHomeWorld/";
    } 
    if (Bukkit.getVersion().toString().contains("1.7.10")) {
      Variable.world_prefix = "";
      Variable.Cat_Check = true;
    } 
    if (Bukkit.getVersion().toString().contains("1.16.5")) {
      Variable.world_prefix = "";
      Variable.Cat_Check = true;
    } 
    if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT")) {
      Variable.world_prefix = "";
      Variable.Cat_Check = false;
    } 
    if (Bukkit.getVersion().toString().contains("1.20.1") && Bukkit.getVersion().toString().toUpperCase().contains("1.20.1")) {
      Variable.world_prefix = "";
      Variable.Cat_Check = true;
    } 
    if (Bukkit.getVersion().toString().contains("1.20.1") && Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT")) {
      Variable.world_prefix = "";
      Variable.Cat_Check = true;
    } 
    if (Bukkit.getVersion().toString().contains("Banner") && Bukkit.getVersion().toString().toUpperCase().contains("1.20.1"))
      Variable.world_prefix = "SelfHomeWorld/"; 
    JavaPlugin.saveDefaultConfig();
    JavaPlugin.reloadConfig();
    if (!(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "GUI.yml")).exists())
      JavaPlugin.saveResource("GUI.yml", false); 
    if (!(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "GUI_en.yml")).exists())
      JavaPlugin.saveResource("GUI_en.yml", false); 
    if (!(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "log.yml")).exists())
      JavaPlugin.saveResource("log.yml", false); 
    if (!(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "Language" + Variable.file_loc_prefix + "Chinese.yml")).exists())
      JavaPlugin.saveResource("Language" + Variable.file_loc_prefix + "Chinese.yml", false); 
    if (!(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "Language" + Variable.file_loc_prefix + "Chinese_TW.yml")).exists())
      JavaPlugin.saveResource("Language" + Variable.file_loc_prefix + "Chinese_TW.yml", false); 
    if (!(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "Language" + Variable.file_loc_prefix + "English.yml")).exists())
      JavaPlugin.saveResource("Language" + Variable.file_loc_prefix + "English.yml", false); 
    Variable.Lang_YML = 
      (FileConfiguration)YamlConfiguration.loadConfiguration(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "Language" + 
          Variable.file_loc_prefix + JavaPlugin.getConfig().getString("Language") + ".yml"));
    File f = new File("");
    String Tempf0 = null;
    try {
      Tempf0 = f.getCanonicalPath();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Variable.Final = "";
    if (Variable.linux_os) {
      Variable.ab = Tempf0.split(Variable.file_loc_prefix);
    } else {
      Variable.ab = Tempf0.split(String.valueOf(String.valueOf(Variable.file_loc_prefix)) + Variable.file_loc_prefix);
    } 
    if (isOSLinux()) {
      String[] args = Tempf0.split(Variable.file_loc_prefix);
      for (int i = 0; i < args.length - 1; i++)
        Variable.Final = String.valueOf(String.valueOf(Variable.Final)) + Variable.file_loc_prefix + args[i]; 
    } else {
      String[] args = Tempf0.split(String.valueOf(String.valueOf(Variable.file_loc_prefix)) + Variable.file_loc_prefix);
      for (int i = 0; i < args.length - 1; i++)
        Variable.Final = String.valueOf(String.valueOf(Variable.Final)) + Variable.file_loc_prefix + args[i]; 
    } 
    Variable.Final = String.valueOf(String.valueOf(Variable.Final)) + Variable.file_loc_prefix;
    if (isOSLinux()) {
      Variable.Final = Variable.Final.replaceFirst(Variable.file_loc_prefix, "");
    } else {
      Variable.Final = Variable.Final.replaceFirst(String.valueOf(String.valueOf(Variable.file_loc_prefix)) + Variable.file_loc_prefix, "");
    } 
    if (JavaPlugin.getConfig().getBoolean("BungeeCord")) {
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableBungeeCord"));
      Variable.bungee = true;
      HikariCPUtils.setSqlConnectionPool();
      if (JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableFunctionButTeleport")); 
    } else {
      Variable.bungee = false;
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableBungeeCord"));
    } 
    Variable.custom_playerdata_location = String.valueOf(String.valueOf(Variable.Final)) + Variable.ab[Variable.ab.length - 1] + 
      Variable.file_loc_prefix + "plugins" + Variable.file_loc_prefix + "SelfHomeMain" + 
      Variable.file_loc_prefix + "playerdata";
    Variable.custom_autobackup_location = String.valueOf(String.valueOf(Variable.Final)) + Variable.ab[Variable.ab.length - 1] + 
      Variable.file_loc_prefix + "plugins" + Variable.file_loc_prefix + "SelfHomeMain" + 
      Variable.file_loc_prefix + "backup";
    Variable.server_file_world = Variable.Final;
    Variable.worldFinal = String.valueOf(String.valueOf(Tempf0)) + Variable.file_loc_prefix + "plugins" + Variable.file_loc_prefix + "SelfHomeMain" + 
      Variable.file_loc_prefix;
    Variable.Log_All = String.valueOf(String.valueOf(Tempf0)) + Variable.file_loc_prefix + "plugins" + Variable.file_loc_prefix + "SelfHomeMain" + 
      Variable.file_loc_prefix;
    Variable.single_server_gen = String.valueOf(String.valueOf(Variable.Final)) + Variable.file_loc_prefix + Variable.ab[Variable.ab.length - 1] + 
      Variable.file_loc_prefix;
    Variable.Final = Variable.custom_playerdata_location;
    File check_file = new File(Variable.Final);
    if (!check_file.isDirectory())
      check_file.mkdir(); 
    File autobackup = new File(Variable.custom_autobackup_location);
    if (!autobackup.isDirectory())
      autobackup.mkdir(); 
    Variable.CheckIsHome = Variable.Final;
    File aaa = new File(Variable.Final);
    if (!aaa.isDirectory())
      aaa.mkdir(); 
    Variable.Tempf = Variable.Final;
    Variable.Tempf2 = Variable.Temp;
    Variable.f_log = new File(Variable.Log_All, "log.yml");
    if (!Variable.f_log.exists()) {
      try {
        Variable.f_log.createNewFile();
      } catch (IOException e1) {
        e1.printStackTrace();
      } 
      Variable.f_log = new File(Variable.Log_All, "log.yml");
    } 
    Variable.Papi_world = JavaPlugin.getConfig().getString("WorldName");
    File f2 = new File(Variable.worldFinal, "config.yml");
    Variable.getName_yml = (FileConfiguration)YamlConfiguration.loadConfiguration(f2);
    Variable.GUI_YML = 
      (FileConfiguration)YamlConfiguration.loadConfiguration(new File(JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "GUI.yml"));
    ConfigUpdate.update();
    Variable.Soil = JavaPlugin.getConfig().getString("SoilType");
    initHome.init();
  }
  
  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    if (!channel.equals("BungeeCord"))
      return; 
    ByteArrayDataInput in = ByteStreams.newDataInput(message);
    String subchannel = in.readUTF();
    if (subchannel.equals("SelfHomeMain")) {
      short len = in.readShort();
      byte[] msgbytes = new byte[len];
      in.readFully(msgbytes);
      DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
      try {
        String[] somedata = msgin.readUTF().split(",");
        if (somedata[0].equalsIgnoreCase("waitDelayToHome")) {
          Variable.wait_to_spawn_home.put(somedata[1], somedata[2]);
        } else if (somedata[0].equalsIgnoreCase("waitToCommand")) {
          Variable.wait_to_command.put(somedata[1], somedata[2]);
        } else if (somedata[0].equalsIgnoreCase("waitToLoad")) {
          final WaitToLoad wt = new WaitToLoad();
          wt.home_name = somedata[2];
          wt.file_loc = somedata[3];
          MySQL.setServer(wt.home_name, JavaPlugin.getConfig().getString("Server"));
          File new_f = null;
          if (Variable.world_prefix.equalsIgnoreCase("")) {
            if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT") || Bukkit.getVersion().toString().contains("1.20.1")) {
              new_f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + wt.home_name);
            } else {
              new_f = new File(
                  String.valueOf(String.valueOf(Variable.single_server_gen)) + "world" + Variable.file_loc_prefix + wt.home_name);
            } 
          } else {
            new_f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + wt.home_name);
          } 
          if (new_f.isDirectory())
            Util.deleteFile(new_f); 
          Util.copyDir(String.valueOf(String.valueOf(wt.file_loc)) + Variable.file_loc_prefix + wt.home_name, new_f.getAbsolutePath());
          Variable.wait_to_command.put(somedata[1], "sh v " + wt.home_name);
          Variable.has_already_move_world.add(somedata[1]);
          (new BukkitRunnable() {
              public void run() {
                Util.deleteFile(new File(String.valueOf(String.valueOf(wt.file_loc)) + Variable.file_loc_prefix + wt.home_name));
              }
            }).runTaskLater((Plugin)JavaPlugin, 20L);
        } 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
}
