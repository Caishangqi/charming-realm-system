package com.SelfHome;

import WorldBorder.WBControl;
import com.Util.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class init implements Listener {
  public static void refreshWorldStatics(boolean broad) {
    Variable.list_home.clear();
    if (Variable.bungee) {
      for (String str : MySQL.getAllWorlds())
        Variable.list_home.add(str); 
    } else {
      File folder = new File(String.valueOf(String.valueOf(Main.JavaPlugin.getDataFolder().getPath().toString())) + Variable.file_loc_prefix + "playerdata");
      File[] arrayOfFile;
      for (int k = (arrayOfFile = folder.listFiles()).length, b = 0; b < k; ) {
        File temp = arrayOfFile[b];
        String want_to = temp.getPath().replace(String.valueOf(String.valueOf(Main.JavaPlugin.getDataFolder().getPath().toString())) + Variable.file_loc_prefix + "playerdata", "").replace(Variable.file_loc_prefix, "").replace(".yml", "");
        Variable.list_home.add(want_to);
        b++;
      } 
    } 
    Variable.world_StaticsTick.clear();
    boolean check_has = false;
    for (World world : Bukkit.getWorlds()) {
      if (!Util.CheckIsHome(world.getName()))
        continue; 
      check_has = true;
      int chunks = 0;
      int tiles = 0;
      int entity = 0;
      int dropitem = 0; int b; int k;
      Chunk[] arrayOfChunk;
      for (k = (arrayOfChunk = world.getLoadedChunks()).length, b = 0; b < k; ) {
        Chunk chunk = arrayOfChunk[b];
        chunks++;
        int b1; int m; BlockState[] arrayOfBlockState;
        for (m = (arrayOfBlockState = chunk.getTileEntities()).length,b1 = 0;b1 < m; ) {
          BlockState bs = arrayOfBlockState[b1];
          tiles++;
          b1++;
        } 
        Entity[] arrayOfEntity;
        for (int e = (arrayOfEntity = chunk.getEntities()).length; b1 < e; ) {
          Entity et = arrayOfEntity[b1];
          if (et.getType() != EntityType.DROPPED_ITEM) {
            entity++;
          } else {
            Item i = (Item)et;
            dropitem += i.getItemStack().getAmount();
          } 
          b1++;
        } 
        b++;
      } 
      double calc_tps = tiles * Main.JavaPlugin.getConfig().getDouble("OneTileTick") + entity * Main.JavaPlugin.getConfig().getDouble("OneEntityTick") + dropitem * Main.JavaPlugin.getConfig().getDouble("OneDropTick") + chunks * Main.JavaPlugin.getConfig().getDouble("OneChunkTick");
      StaticsTick temp = new StaticsTick(world.getName().replaceAll(Variable.world_prefix, ""), tiles, chunks, entity, dropitem, calc_tps);
      Variable.world_StaticsTick.add(temp);
    } 
    if (!check_has)
      return; 
    if (broad) {
      for (int j = 0; j < Main.JavaPlugin.getConfig().getStringList("StatisticsTop").size(); j++) {
        String a = Main.JavaPlugin.getConfig().getStringList("StatisticsTop").get(j);
        Bukkit.broadcastMessage(a);
      } 
      int i;
      for (i = 0; i < Variable.world_StaticsTick.size() - 1; i++) {
        for (int k = 0; k < Variable.world_StaticsTick.size() - 1 - i; k++) {
          if (((StaticsTick)Variable.world_StaticsTick.get(k)).tps < ((StaticsTick)Variable.world_StaticsTick.get(k + 1)).tps) {
            StaticsTick temp = Variable.world_StaticsTick.get(k);
            Variable.world_StaticsTick.set(k, Variable.world_StaticsTick.get(k + 1));
            Variable.world_StaticsTick.set(k + 1, temp);
          } 
        } 
      } 
      for (i = 0; i < Variable.world_StaticsTick.size() && i < Main.JavaPlugin.getConfig().getInt("ShowAmount"); i++) {
        StaticsTick s = Variable.world_StaticsTick.get(i);
        if (s.tps != 0.0D) {
          String temp = Main.JavaPlugin.getConfig().getString("ShowFormat");
          if (temp.contains("<index>"))
            temp = temp.replace("<index>", String.valueOf(i + 1)); 
          if (temp.contains("<world>"))
            temp = temp.replace("<world>", s.name); 
          if (temp.contains("<tile>"))
            temp = temp.replace("<tile>", String.valueOf(s.tile)); 
          if (temp.contains("<chunk>"))
            temp = temp.replace("<chunk>", String.valueOf(s.chunk)); 
          if (temp.contains("<entity>"))
            temp = temp.replace("<entity>", String.valueOf(s.entity)); 
          if (temp.contains("<drop>"))
            temp = temp.replace("<drop>", String.valueOf(s.drop)); 
          if (temp.contains("<tps>"))
            temp = temp.replace("<tps>", String.format(Main.JavaPlugin.getConfig().getString("FormatInfo"), new Object[] { Double.valueOf(s.tps) })); 
          Bukkit.broadcastMessage(temp);
        } 
      } 
      for (int c = 0; c < Main.JavaPlugin.getConfig().getStringList("StatisticsEnd").size(); c++) {
        String a = Main.JavaPlugin.getConfig().getStringList("StatisticsEnd").get(c);
        Bukkit.broadcastMessage(a);
      } 
    } 
  }
  
  public static void init() {
    if (Main.JavaPlugin.getConfig().getBoolean("BorderSwitch"))
      (new BukkitRunnable() {
          public void run() {
            for (World world : Bukkit.getWorlds()) {
              if (!Util.CheckIsHome(world.getName().replace(Variable.world_prefix, "")))
                continue; 
              Home home = HomeAPI.getHome(world.getName().replace(Variable.world_prefix, ""));
              if (Main.JavaPlugin.getConfig().getBoolean("BorderSwitch"))
                try {
                  world.getWorldBorder().setCenter(world.getSpawnLocation());
                  world.getWorldBorder().setSize((
                      Main.JavaPlugin.getConfig().getInt("WorldBoard") + (home.getLevel() - 1) * 
                      Main.JavaPlugin.getConfig().getInt("UpdateRadius")));
                  for (Player p : world.getPlayers()) {
                    if (Variable.has_already_hide_border.contains(p.getName())) {
                      WBControl.setEnable(p);
                      continue;
                    } 
                    WBControl.setDisable(p);
                  } 
                } catch (NoSuchMethodError e) {
                  Bukkit.getConsoleSender()
                    .sendMessage(Variable.Lang_YML.getString("BorderException"));
                }  
            } 
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, 60L); 
    (new BukkitRunnable() {
        public void run() {
          Calendar cal = Calendar.getInstance();
          int hour = cal.getTime().getHours();
          int minute = cal.getTime().getMinutes();
          int seconds = cal.getTime().getSeconds();
          if (hour == 0 && minute == 0 && seconds == 0) {
            Variable.popularity_list.clear();
            Variable.flowers_list.clear();
          } 
        }
      }).runTaskTimerAsynchronously((Plugin)Main.JavaPlugin, 0L, 20L);
    (new BukkitRunnable() {
        public void run() {
          Variable.list_home.clear();
          if (Variable.bungee) {
            for (String str : MySQL.getAllWorlds())
              Variable.list_home.add(str); 
          } else {
            File folder = new File(String.valueOf(String.valueOf(Main.JavaPlugin.getDataFolder().getPath().toString())) + Variable.file_loc_prefix + "playerdata");
            File[] arrayOfFile;
            for (int i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) {
              File temp = arrayOfFile[b];
              String want_to = temp.getPath().replace(String.valueOf(String.valueOf(Main.JavaPlugin.getDataFolder().getPath().toString())) + Variable.file_loc_prefix + "playerdata", "").replace(Variable.file_loc_prefix, "").replace(".yml", "");
              Variable.list_home.add(want_to);
              b++;
            } 
          } 
        }
      }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, 100L);
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport") && 
      Main.JavaPlugin.getConfig().getBoolean("EnableBlackItemsUseInNoPermission"))
      (new BukkitRunnable() {
          public void run() {
            for (World world : Bukkit.getWorlds()) {
              if (!Util.CheckIsHome(world.getName().replace(Variable.world_prefix, "")))
                continue; 
              for (Player p : world.getPlayers()) {
                if (Util.Check(p, world.getName().replace(Variable.world_prefix, "")).booleanValue())
                  continue; 
                int b;
                int k;
                ItemStack[] arrayOfItemStack;
                label33: for (k = (arrayOfItemStack = p.getInventory().getContents()).length, b = 0; b < k; ) {
                  ItemStack i = arrayOfItemStack[b];
                  String nbt = Util.getItemNBTString(i);
                  for (int j = 0; j < Main.JavaPlugin.getConfig().getStringList("BlackItems").size(); j++) {
                    if (nbt.toUpperCase().contains(((String)Main.JavaPlugin.getConfig().getStringList("BlackItems").get(j)).toUpperCase())) {
                      String command = Main.JavaPlugin.getConfig().getString("BeKickedCommand");
                      if (command.contains("<Name>"))
                        command = command.replace("<Name>", p.getName()); 
                      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), command);
                      String message = Variable.Lang_YML.getString("TakeBlackItemsInNoPermissionHome");
                      if (message.contains("<type>"))
                        message = message.replace("<type>", ((String)Main.JavaPlugin.getConfig().getStringList("BlackItems").get(j)).toUpperCase()); 
                      p.sendMessage(message);
                      break label33;
                    } 
                  } 
                  b++;
                } 
              } 
            } 
          }
        }).runTaskTimerAsynchronously((Plugin)Main.JavaPlugin, 0L, 20L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport") && 
      Main.JavaPlugin.getConfig().getBoolean("CustomEntityMax"))
      (new BukkitRunnable() {
          public void run() {
            for (World world : Bukkit.getWorlds()) {
              if (!Util.CheckIsHome(world.getName().replace(Variable.world_prefix, "")))
                continue; 
              HashMap<String, Integer> entity_map = new HashMap<>();
              for (Entity entity : world.getEntities()) {
                String type = null;
                if (Bukkit.getBukkitVersion().toString().contains("1.12.2")) {
                  type = R1_12_2.getName(entity);
                } else if (Bukkit.getBukkitVersion().toString().contains("1.7.10")) {
                  type = R1_7_10.getName(entity);
                } else {
                  type = entity.getType().toString().toUpperCase();
                } 
                if (entity instanceof org.bukkit.entity.Animals)
                  type = "Animals"; 
                if (!entity_map.containsKey(type)) {
                  entity_map.put(type, Integer.valueOf(1));
                  continue;
                } 
                int now_amount = ((Integer)entity_map.get(type)).intValue();
                for (int c = 0; c < Main.JavaPlugin.getConfig().getStringList("EntityList").size(); c++) {
                  String[] args = ((String)Main.JavaPlugin.getConfig().getStringList("EntityList").get(c)).split("\\|");
                  if (args[0].toUpperCase().contains(type.toUpperCase())) {
                    int Max_Amount = Integer.valueOf(args[1]).intValue();
                    if (now_amount > Max_Amount) {
                      entity.remove();
                    } else {
                      entity_map.put(type, Integer.valueOf(now_amount + 1));
                    } 
                  } 
                } 
              } 
            } 
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("CheckEntityInterval") * 20L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport") && 
      Main.JavaPlugin.getConfig().getBoolean("EnableTilesAndChunksAndDropItemsStatisticsTop"))
      (new BukkitRunnable() {
          public void run() {
            init.refreshWorldStatics(true);
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("ShowTimes") * 20L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
      if (Main.JavaPlugin.getConfig().getLong("SaveTime") != 0L) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableAutoSaveWorld"));
        (new BukkitRunnable() {
            public void run() {
              for (World temp : Bukkit.getWorlds()) {
                boolean is_jump = false;
                for (int i = 0; i < Main.JavaPlugin.getConfig().getStringList("UnAutoSaveWorlds").size(); i++) {
                  String str = Main.JavaPlugin.getConfig().getStringList("UnAutoSaveWorlds").get(i);
                  if (str.equalsIgnoreCase(temp.getName().replace(Variable.world_prefix, ""))) {
                    is_jump = true;
                    break;
                  } 
                } 
                if (!is_jump)
                  temp.save(); 
              } 
              Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("AutoSaveSuccess"));
            }
          }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("SaveTime") * 20L);
      } else {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableAutoSaveWorld"));
      }  
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
      (new BukkitRunnable() {
          public void run() {
            if (Main.JavaPlugin.getConfig().getInt("ArmorStand") == -1)
              return; 
            for (World world : Bukkit.getWorlds()) {
              if (!Util.CheckIsHome(world.getName().replaceAll(Variable.world_prefix, "")))
                continue; 
              int amount = 0;
              for (Entity entity : world.getEntities()) {
                amount++;
                if (entity.getType() == EntityType.ARMOR_STAND && amount > Main.JavaPlugin.getConfig().getInt("ArmorStand")) {
                  entity.remove();
                  amount--;
                } 
              } 
            } 
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, 100L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
      if (Main.JavaPlugin.getConfig().getLong("AutoBackup") != 0L) {
        (new BukkitRunnable() {
            public void run() {
              if (Variable.check_first_start) {
                Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableAutoBackup"));
                Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableAutoBackupButFirstTime"));
                Variable.check_first_start = false;
              } else {
                LocalDateTime now = LocalDateTime.now();
                String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
                if (Variable.bungee) {
                  File f = null;
                  String OriginalBackup_location = String.valueOf(String.valueOf(Variable.custom_autobackup_location)) + Variable.file_loc_prefix + time;
                  if (!Main.JavaPlugin.getConfig().getString("CustomBackupLocation").equalsIgnoreCase(""))
                    OriginalBackup_location = String.valueOf(String.valueOf(Main.JavaPlugin.getConfig().getString("CustomBackupLocation"))) + time; 
                  boolean check_has_copy = true;
                  String folderToCompress = "";
                  for (String worldname : MySQL.getAllWorlds()) {
                    if (!MySQL.getServer(worldname).equalsIgnoreCase(Main.JavaPlugin.getConfig().getString("Server")))
                      continue; 
                    if (MySQL.getVisitTime(worldname).equalsIgnoreCase(""))
                      MySQL.setVisitTime(worldname, String.valueOf(System.currentTimeMillis())); 
                    long before_time = Long.valueOf(MySQL.getVisitTime(worldname)).longValue();
                    long distance = (System.currentTimeMillis() - before_time) / 86400000L;
                    if (distance > Main.JavaPlugin.getConfig().getLong("NoBackup"))
                      continue; 
                    if (Variable.world_prefix.equalsIgnoreCase("")) {
                      if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT")) {
                        f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + worldname);
                      } else {
                        f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + "world" + Variable.file_loc_prefix + worldname);
                      } 
                    } else {
                      f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + worldname);
                    } 
                    String oldDir = String.valueOf(OriginalBackup_location) + Variable.file_loc_prefix + worldname;
                    try {
                      Util.copyDir(f.getPath(), oldDir);
                      folderToCompress = String.valueOf(String.valueOf(Variable.custom_autobackup_location)) + Variable.file_loc_prefix + time;
                    } catch (Exception e) {
                      check_has_copy = false;
                    } 
                  } 
                  if (check_has_copy) {
                    String zipFileName = String.valueOf(String.valueOf(OriginalBackup_location)) + ".zip";
                    try {
                      ZIP.zipFolder(OriginalBackup_location, zipFileName);
                    } catch (IOException e) {
                      e.printStackTrace();
                    } 
                    Util.deleteFile(new File(OriginalBackup_location));
                    Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("BungeeCordModuleAutoBackupSuccess"));
                  } 
                } else {
                  File folder = new File(Variable.Tempf);
                  String OriginalBackup_location = String.valueOf(String.valueOf(Variable.custom_autobackup_location)) + Variable.file_loc_prefix + time;
                  if (!Main.JavaPlugin.getConfig().getString("CustomBackupLocation").equalsIgnoreCase(""))
                    OriginalBackup_location = String.valueOf(String.valueOf(Main.JavaPlugin.getConfig().getString("CustomBackupLocation"))) + time; 
                  String folderToCompress = null;
                  boolean check_has_copy = true;
                  File[] arrayOfFile;
                  for (int i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) {
                    File temp = arrayOfFile[b];
                    long lastModified = temp.lastModified();
                    long nowlong = System.currentTimeMillis();
                    long distance = (nowlong - lastModified) / 86400000L;
                    if (distance <= Main.JavaPlugin.getConfig().getLong("NoBackup")) {
                      String want_to = temp.getPath().replace(Variable.Tempf, "").replace(".yml", "").replace(Variable.file_loc_prefix, "");
                      if (Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + want_to) != null)
                        Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + want_to).save(); 
                      String oldDir = String.valueOf(OriginalBackup_location) + Variable.file_loc_prefix + want_to;
                      File f = null;
                      if (Variable.world_prefix.equalsIgnoreCase("")) {
                        if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT")) {
                          f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + want_to);
                        } else {
                          f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + "world" + Variable.file_loc_prefix + want_to);
                        } 
                      } else {
                        f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + want_to);
                      } 
                      try {
                        Util.copyDir(f.getPath(), oldDir);
                        folderToCompress = String.valueOf(String.valueOf(Variable.custom_autobackup_location)) + Variable.file_loc_prefix + time;
                      } catch (Exception e) {
                        check_has_copy = false;
                      } 
                    } 
                    b++;
                  } 
                  if (check_has_copy) {
                    String zipFileName = String.valueOf(String.valueOf(OriginalBackup_location)) + ".zip";
                    try {
                      ZIP.zipFolder(OriginalBackup_location, zipFileName);
                    } catch (IOException e) {
                      e.printStackTrace();
                    } 
                    Util.deleteFile(new File(OriginalBackup_location));
                    Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("SingleServerModuleAutoBackupSuccess"));
                  } 
                } 
              } 
            }
          }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("AutoBackup") * 20L);
      } else {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableAutoBackup"));
      }  
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport") && 
      Main.JavaPlugin.getConfig().getLong("OptimizeTime") != 0L)
      (new BukkitRunnable() {
          public void run() {
            boolean has_been_solve = false;
            for (World world : Bukkit.getWorlds()) {
              if (!Util.CheckIsHome(world.getName().replace(Variable.world_prefix, "")))
                continue; 
              boolean in_whitelist = false;
              for (int i = 0; i < Main.JavaPlugin.getConfig().getStringList("UnOptimizeWorlds").size(); i++) {
                if (((String)Main.JavaPlugin.getConfig().getStringList("UnOptimizeWorlds").get(i)).equalsIgnoreCase(world.getName().replace(Variable.world_prefix, ""))) {
                  in_whitelist = true;
                  break;
                } 
              } 
              if (in_whitelist)
                continue; 
              if (Main.JavaPlugin.getConfig().getInt("OptimizeType") == 1) {
                if (world.getPlayers().size() == 0) {
                  has_been_solve = true;
                  Bukkit.unloadWorld(world, true);
                } 
                continue;
              } 
              if (Main.JavaPlugin.getConfig().getInt("OptimizeType") == 2) {
                Chunk[] arrayOfChunk;
                for (int j = (arrayOfChunk = world.getLoadedChunks()).length, b = 0; b < j; ) {
                  Chunk temp_chunk = arrayOfChunk[b];
                  boolean check_player = false;
                  boolean check_cable = false; int b1; int k;
                  BlockState[] arrayOfBlockState;
                  for (k = (arrayOfBlockState = temp_chunk.getTileEntities()).length,b1 = 0; b1 < k; ) {
                    BlockState bs = arrayOfBlockState[b1];
                    try {
                      if (Util.getNBTString(bs).toUpperCase().contains("IC2:CABLE")) {
                        check_cable = true;
                        break;
                      } 
                    } catch (NoClassDefFoundError e) {
                      check_cable = false;
                    } 
                    b1++;
                  } 
                  Entity[] arrayOfEntity;
                  for (int ke = (arrayOfEntity = temp_chunk.getEntities()).length; b1 < ke; ) {
                    Entity ee = arrayOfEntity[b1];
                    if (ee instanceof Player) {
                      check_player = true;
                      break;
                    } 
                    b1++;
                  } 
                  if (!check_player && !check_cable) {
                    has_been_solve = true;
                    temp_chunk.unload(true);
                  } 
                  b++;
                } 
              } 
            } 
            if (has_been_solve)
              if (Main.JavaPlugin.getConfig().getInt("OptimizeType") == 1) {
                Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("OptimizeTypeOne"));
              } else if (Main.JavaPlugin.getConfig().getInt("OptimizeType") == 2) {
                Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("OptimizeTypeTwo"));
              }  
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("OptimizeTime") * 20L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport")) {
      if (Main.JavaPlugin.getConfig().getLong("CheckTime") != 0L) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableHomeTileCheck"));
        (new BukkitRunnable() {
            int i = 0;
            
            public void run() {
              List<String> WarnList = new ArrayList<>();
              List<String> UnLoadList = new ArrayList<>();
              String WarnStr = "";
              String UnLoadStr = "";
              for (StaticsTick st : Variable.world_StaticsTick) {
                World world = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + st.name);
                int tiles = st.tile;
                if (tiles >= Main.JavaPlugin.getConfig().getInt("UnLoadTiles")) {
                  this.i++;
                  UnLoadList.add(world.getName().replace(Variable.world_prefix, ""));
                  if (world.getName() == null)
                    continue; 
                  for (Player p : world.getPlayers()) {
                    p.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    Bukkit.getConsoleSender()
                      .sendMessage(Variable.Lang_YML.getString("PlayerBeKickedByBanHome"));
                  } 
                  Bukkit.unloadWorld(world.getName().replace(Variable.world_prefix, ""), true);
                  continue;
                } 
                if (tiles >= Main.JavaPlugin.getConfig().getInt("MaxTiles")) {
                  this.i++;
                  WarnList.add(world.getName().replace(Variable.world_prefix, ""));
                } 
              } 
              if (WarnList.size() == 0 && UnLoadList.size() == 0)
                return; 
              if (Main.JavaPlugin.getConfig().getBoolean("CheckTipToAllPlayers")) {
                Bukkit.broadcastMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
              } else {
                Bukkit.getConsoleSender()
                  .sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
              } 
              if (Main.JavaPlugin.getConfig().getBoolean("CheckTipToAllPlayers")) {
                if (WarnList.size() != 0)
                  for (int i = 0; i < Variable.Lang_YML.getStringList("WarnLanguage").size(); i++) {
                    String message = Variable.Lang_YML.getStringList("WarnLanguage").get(i);
                    if (message.contains("<WarnList>"))
                      message = message.replace("<WarnList>", WarnList.toString()); 
                    Bukkit.broadcastMessage(message);
                  }  
                if (UnLoadList.size() != 0)
                  for (int i = 0; i < Variable.Lang_YML.getStringList("UnLoadLanguage").size(); i++) {
                    String message = Variable.Lang_YML.getStringList("UnLoadLanguage").get(i);
                    if (message.contains("<UnLoadList>"))
                      message = message.replace("<UnLoadList>", UnLoadList.toString()); 
                    Bukkit.broadcastMessage(message);
                  }  
              } else {
                if (WarnList.size() != 0)
                  for (int i = 0; i < Variable.Lang_YML.getStringList("WarnLanguage").size(); i++) {
                    String message = Variable.Lang_YML.getStringList("WarnLanguage").get(i);
                    if (message.contains("<WarnList>"))
                      message = message.replace("<WarnList>", WarnList.toString()); 
                    Bukkit.getConsoleSender().sendMessage(message);
                  }  
                if (UnLoadList.size() != 0)
                  for (int i = 0; i < Variable.Lang_YML.getStringList("UnLoadLanguage").size(); i++) {
                    String message = Variable.Lang_YML.getStringList("UnLoadLanguage").get(i);
                    if (message.contains("<UnLoadList>"))
                      message = message.replace("<UnLoadList>", UnLoadList.toString()); 
                    Bukkit.getConsoleSender().sendMessage(message);
                  }  
              } 
              if (Main.JavaPlugin.getConfig().getBoolean("CheckTipToAllPlayers")) {
                Bukkit.broadcastMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
              } else {
                Bukkit.getConsoleSender()
                  .sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
              } 
            }
          }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("CheckTime") * 20L);
      } 
      if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
        (new BukkitRunnable() {
            public void run() {
              for (World temp : Bukkit.getWorlds()) {
                String check_world_is_home = temp.getName().replace(Variable.world_prefix, "");
                if (!Util.CheckIsHome(check_world_is_home))
                  continue; 
                Integer Amount = Integer.valueOf(0);
                Boolean Check = Boolean.valueOf(false);
                Integer Del = Integer.valueOf(0);
                for (Entity entity : temp.getEntities()) {
                  if (entity instanceof org.bukkit.entity.LivingEntity) {
                    boolean check_white = false;
                    for (int c = 0; c < Main.JavaPlugin.getConfig().getStringList("WhiteEntities")
                      .size(); c++) {
                      String white = Main.JavaPlugin.getConfig().getStringList("WhiteEntities").get(c);
                      if (white.equalsIgnoreCase(entity.getType().toString())) {
                        check_white = true;
                        break;
                      } 
                    } 
                    if (!check_white) {
                      Amount = Integer.valueOf(Amount.intValue() + 1);
                      if (Amount.intValue() > Main.JavaPlugin.getConfig().getInt("DeleteEntities") && 
                        !(entity instanceof Player)) {
                        entity.remove();
                        Check = Boolean.valueOf(true);
                        Del = Integer.valueOf(Del.intValue() + 1);
                      } 
                    } 
                  } 
                } 
                if (Check.booleanValue()) {
                  String temp5 = Main.JavaPlugin.getConfig().getString("ClearEntity");
                  if (temp5.contains("<Name>"))
                    temp5 = temp5.replace("<Name>", temp.getName()); 
                  if (temp5.contains("<Amount>"))
                    temp5 = temp5.replace("<Amount>", String.valueOf(Del)); 
                  Bukkit.broadcastMessage(temp5);
                } 
              } 
            }
          }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("CheckTime") * 20L); 
      if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport")) {
        (new BukkitRunnable() {
            public void run() {
              for (World temp : Bukkit.getWorlds()) {
                String check_world_is_home = temp.getName().replace(Variable.world_prefix, "");
                if (!Util.CheckIsHome(check_world_is_home))
                  continue; 
                Integer Amount = Integer.valueOf(0);
                Boolean Check = Boolean.valueOf(false);
                Integer Del = Integer.valueOf(0);
                for (Entity entity : temp.getEntities()) {
                  if (entity.getType() == EntityType.DROPPED_ITEM) {
                    Amount = Integer.valueOf(Amount.intValue() + 1);
                    if (Amount.intValue() > Main.JavaPlugin.getConfig().getInt("DeleteItems")) {
                      Check = Boolean.valueOf(true);
                      Del = Integer.valueOf(Del.intValue() + 1);
                    } 
                  } 
                } 
                if (Check.booleanValue()) {
                  String temp5 = Variable.Lang_YML.getString("ClearDropItems");
                  if (temp5.contains("<Name>"))
                    temp5 = temp5.replace("<Name>", temp.getName()); 
                  if (temp5.contains("<Amount>"))
                    temp5 = temp5.replace("<Amount>", String.valueOf(Del)); 
                  Bukkit.broadcastMessage(temp5);
                } 
              } 
            }
          }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, Main.JavaPlugin.getConfig().getLong("CheckTime") * 20L);
      } else {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableHomeTileCheck"));
      } 
    } 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
      (new BukkitRunnable() {
          public void run() {
            if (Variable.bungee) {
              List<String> list = MySQL.getAllWorlds();
              for (String worldname : list) {
                if (Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + worldname) == null)
                  continue; 
                World world = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + worldname);
                if (MySQL.getlocktime(worldname).equalsIgnoreCase("true"))
                  world.setTime(Long.valueOf(MySQL.gettime(worldname)).longValue()); 
              } 
            } else {
              File folder = new File(Variable.Tempf);
              if (folder.listFiles() == null)
                return; 
              File[] arrayOfFile;
              for (int i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) {
                File temp = arrayOfFile[b];
                String want_to = temp.getPath().replace(Variable.Tempf, "").replace(".yml", "")
                  .replace(Variable.file_loc_prefix, "");
                if (Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + want_to) != null) {
                  World world = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + want_to);
                  YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(temp);
                  if (yamlConfiguration.getBoolean("locktime"))
                    world.setTime(yamlConfiguration.getLong("time")); 
                } 
                b++;
              } 
            } 
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, 60L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
      (new BukkitRunnable() {
          public void run() {
            for (Player p : Bukkit.getOnlinePlayers()) {
              if (Variable.DispathCommand.contains(p.getName())) {
                String temp5 = Variable.Lang_YML.getString("OverSomeBorderTip");
                if (!temp5.equalsIgnoreCase("")) {
                  p.sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
                  p.sendMessage(temp5);
                  p.sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
                  Bukkit.dispatchCommand((CommandSender)p, Main.JavaPlugin.getConfig().getString("BorderCommand"));
                  Variable.DispathCommand.remove(p.getName());
                } 
              } 
            } 
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, 20L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport"))
      (new BukkitRunnable() {
          public void run() {
            for (Player p : Bukkit.getOnlinePlayers()) {
              if (Variable.AddDebuff.contains(p.getName())) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 10));
                String temp5 = Variable.Lang_YML.getString("OverBorderTip");
                p.sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
                p.sendMessage(temp5);
                p.sendMessage("§a§l§m--------------" + Variable.Prefix + "§a§l§m--------------");
                Variable.AddDebuff.remove(p.getName());
              } 
            } 
          }
        }).runTaskTimer((Plugin)Main.JavaPlugin, 0L, 20L); 
    if (!Main.JavaPlugin.getConfig().getBoolean("DisableFunctionButTeleport")) {
      if (Main.JavaPlugin.getConfig().getBoolean("CustomTileMax")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableCustomTileMaxFunction"));
      } else {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableCustomTileMaxFunction"));
      } 
      if (Main.JavaPlugin.getConfig().getBoolean("EnableBlackEntities")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableBlackEntitiesFunction"));
      } else {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableBlackEntitiesFunction"));
      } 
      if (!Main.JavaPlugin.getConfig().getString("CustomBorder").equalsIgnoreCase(""))
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableCustomBorder")); 
      if (!Main.JavaPlugin.getConfig().getBoolean("KeepInventory")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableWholeKeepInventory"));
      } else if (Main.JavaPlugin.getConfig().getBoolean("KeepInventory")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnableWholeKeepInventory"));
      } 
      if (!Main.JavaPlugin.getConfig().getBoolean("doMobSpawning")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisableMobSpawning"));
      } else if (Main.JavaPlugin.getConfig().getBoolean("doMobSpawning")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnabledoMobSpawning"));
      } 
      if (!Main.JavaPlugin.getConfig().getBoolean("mobGriefing")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisablemobGriefing"));
      } else if (Main.JavaPlugin.getConfig().getBoolean("mobGriefing")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnablemobGriefing"));
      } 
      if (!Main.JavaPlugin.getConfig().getBoolean("doFireTick")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DisabledoFireTick"));
      } else if (Main.JavaPlugin.getConfig().getBoolean("doFireTick")) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("EnabledoFireTick"));
      } 
    } 
  }
}
