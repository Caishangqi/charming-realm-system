package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Home;
import com.caizii.charmrealm.utils.HomeAPI;
import com.caizii.charmrealm.utils.MySQL;
import com.caizii.charmrealm.utils.Util;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.File;

public class WorldBlockPlaceListener implements Listener {
  public int getChunkAmount(String nbt, Chunk chunk) {
    int NowAmount = 0;
    BlockState[] arrayOfBlockState;
    for (int i = (arrayOfBlockState = chunk.getTileEntities()).length, b = 0; b < i; ) {
      BlockState state = arrayOfBlockState[b];
      if (Util.getNBTString(state).toUpperCase().contains(nbt.toUpperCase()))
        NowAmount++; 
      b++;
    } 
    return NowAmount;
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void BlockCanBuildEvent2(BlockPlaceEvent event) {
    if (event.getPlayer().getName().toUpperCase().contains("AS-FAKEPLAYER") || 
      event.getPlayer().getName().toUpperCase().contains("[MINECRAFT]") || 
      event.getPlayer().getName().toUpperCase().contains("[MEKANISM]") || 
      event.getPlayer().getName().toUpperCase().contains("[IF]"))
      return; 
    Player p = event.getPlayer();
    if (event.getBlock() == null)
      return; 
    int level = 0;
    if (CharmRealm.pluginVariable.bungee) {
      if (MySQL.CheckIsAHome(p.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))) {
        level = Integer.valueOf(MySQL.getLevel(p.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))).intValue();
      } else {
        return;
      } 
    } else {
      File f2 = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(p.getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""))) + ".yml");
      if (!f2.exists())
        return; 
      YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f2);
      level = yamlConfiguration.getInt("Level");
    } 
    int addradius = CharmRealm.JavaPlugin.getConfig().getInt("UpdateRadius") / 2;
    if (!CharmRealm.JavaPlugin.getConfig().getBoolean("CustomTileMax"))
      return; 
    Block block = event.getBlock();
    String nbt = Util.getNBTString(block.getState());
    boolean check_contain = false;
    String contain_nbt = "";
    int MaxThisTile = 0;
    for (int d = 0; d < CharmRealm.JavaPlugin.getConfig().getStringList("TileList").size(); d++) {
      String[] temp = ((String) CharmRealm.JavaPlugin.getConfig().getStringList("TileList").get(d)).split("\\|");
      if (temp[0].equalsIgnoreCase("world"))
        if (nbt.toUpperCase().contains(temp[1].toUpperCase())) {
          check_contain = true;
          contain_nbt = temp[1];
          MaxThisTile = Integer.valueOf(temp[2]).intValue();
          break;
        }  
    } 
    if (!check_contain)
      return; 
    int NowAmount = 0;
    Location topleft = p.getWorld().getSpawnLocation().clone();
    Location topright = p.getWorld().getSpawnLocation().clone();
    Location bottomleft = p.getWorld().getSpawnLocation().clone();
    Location bottomright = p.getWorld().getSpawnLocation().clone();
    topleft.setX(p.getLocation().getWorld().getSpawnLocation().getX() + ((level + 1) * addradius));
    topleft.setZ(p.getLocation().getWorld().getSpawnLocation().getZ() + ((level + 1) * addradius));
    topright.setX(p.getLocation().getWorld().getSpawnLocation().getX() + ((level + 1) * addradius));
    topright.setZ(p.getLocation().getWorld().getSpawnLocation().getZ() - ((level + 1) * addradius));
    bottomleft.setX(p.getLocation().getWorld().getSpawnLocation().getX() - ((level + 1) * addradius));
    bottomleft.setZ(p.getLocation().getWorld().getSpawnLocation().getZ() + ((level + 1) * addradius));
    bottomright.setX(p.getLocation().getWorld().getSpawnLocation().getX() - ((level + 1) * addradius));
    bottomright.setZ(p.getLocation().getWorld().getSpawnLocation().getZ() - ((level + 1) * addradius));
    for (Chunk temp : Util.getchunkmap(topleft, topright, bottomleft, bottomright))
      NowAmount += getChunkAmount(contain_nbt, temp); 
    NowAmount--;
    boolean extra_perm = false;
    int extra_amount = MaxThisTile;
    for (int i = 100; i > 0; i--) {
      if (p.hasPermission("SelfHome.WorldPlace." + contain_nbt + "." + i)) {
        extra_perm = true;
        if (extra_amount < i) {
          extra_amount = i;
          break;
        } 
      } 
    } 
    Home home = HomeAPI.getHome(event.getBlock().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, ""));
    for (String str : home.getLimitBlock()) {
      String[] args = str.split("\\|");
      if (args[0].equalsIgnoreCase("world") && nbt.toUpperCase().contains(args[1].toUpperCase())) {
        int amount = Integer.valueOf(args[2]).intValue();
        if (extra_amount < amount) {
          extra_perm = true;
          extra_amount = amount;
        } 
      } 
    } 
    if (extra_perm)
      MaxThisTile = extra_amount; 
    if (CharmRealm.JavaPlugin.getConfig().getBoolean("EnableClearExtraBlocks") &&
      NowAmount + 1 > MaxThisTile) {
      event.setCancelled(true);
      int wait_to_delete = NowAmount + 1 - MaxThisTile - 1;
      for (Chunk temp : Util.getchunkmap(topleft, topright, bottomleft, bottomright)) {
        BlockState[] arrayOfBlockState;
        for (int j = (arrayOfBlockState = temp.getTileEntities()).length, b = 0; b < j; ) {
          BlockState state = arrayOfBlockState[b];
          if (Util.getNBTString(state).toUpperCase().contains(contain_nbt.toUpperCase()))
            if (wait_to_delete != 0) {
              state.getBlock().getLocation().getWorld().getBlockAt(state.getBlock().getLocation()).setType(Material.AIR);
              p.sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("ClearExtraBlocks"));
              wait_to_delete--;
            } else {
              break;
            }  
          b++;
        } 
      } 
    } 
    if (NowAmount + 1 <= MaxThisTile) {
      String temp = CharmRealm.pluginVariable.Lang_YML.getString("PlaceWorldMaxTile");
      if (temp.contains("<Now>"))
        temp = temp.replace("<Now>", String.valueOf(NowAmount + 1)); 
      if (temp.contains("<Max>"))
        temp = temp.replace("<Max>", String.valueOf(MaxThisTile)); 
      if (temp.contains("<NBT>"))
        temp = temp.replace("<NBT>", String.valueOf(contain_nbt)); 
      p.sendMessage(temp);
    } else {
      String temp = CharmRealm.pluginVariable.Lang_YML.getString("PlaceReachWorldMaxTile");
      if (temp.contains("<Now>"))
        temp = temp.replace("<Now>", String.valueOf(NowAmount)); 
      if (temp.contains("<Max>"))
        temp = temp.replace("<Max>", String.valueOf(MaxThisTile)); 
      if (temp.contains("<NBT>"))
        temp = temp.replace("<NBT>", String.valueOf(contain_nbt)); 
      event.setCancelled(true);
      p.sendMessage(temp);
    } 
  }
}
