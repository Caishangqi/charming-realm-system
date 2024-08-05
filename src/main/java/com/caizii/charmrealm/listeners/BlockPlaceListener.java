package com.caizii.charmrealm.listeners;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Home;
import com.caizii.charmrealm.utils.HomeAPI;
import com.caizii.charmrealm.utils.Util;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST)
  public void BlockCanBuildEvent(BlockPlaceEvent event) {
    if (event.getPlayer().getName().toUpperCase().contains("AS-FAKEPLAYER") || 
      event.getPlayer().getName().toUpperCase().contains("[MINECRAFT]") || 
      event.getPlayer().getName().toUpperCase().contains("[MEKANISM]") || 
      event.getPlayer().getName().toUpperCase().contains("[IF]"))
      return; 
    Player p = event.getPlayer();
    if (event.getBlock() == null)
      return; 
    if (CharmRealm.pluginVariable.Debug.contains(p.getName())) {
      p.sendMessage("§e§l§m--------------§7[§eDeBug§7]§e§l§m--------------");
      TextComponent Send_Block_Message = new TextComponent(
          "§eGet-Returned:§d" + Util.getNBTString(event.getBlock().getState()) + " §b>> §dCopy");
      Send_Block_Message.setClickEvent(
          new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Util.getNBTString(event.getBlock().getState())));
      p.spigot().sendMessage((BaseComponent)Send_Block_Message);
      p.sendMessage("§e§l§m--------------§7[§eDeBug§7]§e§l§m--------------");
    } 
    if (!Util.CheckIsHome(event.getPlayer().getWorld().getName().replace(CharmRealm.pluginVariable.world_prefix, "")))
      return; 
    if (!CharmRealm.JavaPlugin.getConfig().getBoolean("CustomTileMax"))
      return; 
    Block block = event.getBlock();
    String nbt = Util.getNBTString(block.getState());
    boolean check_contain = false;
    String contain_nbt = "";
    int MaxThisTile = 0;
    for (int d = 0; d < CharmRealm.JavaPlugin.getConfig().getStringList("TileList").size(); d++) {
      String[] temp = ((String) CharmRealm.JavaPlugin.getConfig().getStringList("TileList").get(d)).split("\\|");
      if (temp[0].equalsIgnoreCase("chunk"))
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
    boolean extra_perm = false;
    int extra_amount = MaxThisTile;
    for (int i = 100; i > 0; i--) {
      if (p.hasPermission("SelfHome.ChunkPlace." + contain_nbt + "." + i)) {
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
      if (args[0].equalsIgnoreCase("chunk") && nbt.toUpperCase().contains(args[1].toUpperCase())) {
        int amount = Integer.valueOf(args[2]).intValue();
        if (extra_amount < amount) {
          extra_perm = true;
          extra_amount = amount;
        } 
      } 
    } 
    BlockState[] arrayOfBlockState;
    for (int j = (arrayOfBlockState = event.getBlock().getChunk().getTileEntities()).length, b = 0; b < j; ) {
      BlockState state = arrayOfBlockState[b];
      if (Util.getNBTString(state).toUpperCase().contains(contain_nbt.toUpperCase())) {
        NowAmount++;
        if (CharmRealm.JavaPlugin.getConfig().getBoolean("EnableClearExtraBlocks") &&
          NowAmount > MaxThisTile) {
          event.setCancelled(true);
          state.getBlock().getLocation().getWorld().getBlockAt(state.getBlock().getLocation()).setType(Material.AIR);
          p.sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("ClearExtraBlocks"));
        } 
      } 
      b++;
    } 
    NowAmount--;
    if (extra_perm)
      MaxThisTile = extra_amount; 
    if (NowAmount + 1 <= MaxThisTile) {
      String temp = CharmRealm.pluginVariable.Lang_YML.getString("PlaceMaxTile");
      if (temp.contains("<Now>"))
        temp = temp.replace("<Now>", String.valueOf(NowAmount + 1)); 
      if (temp.contains("<Max>"))
        temp = temp.replace("<Max>", String.valueOf(MaxThisTile)); 
      if (temp.contains("<NBT>"))
        temp = temp.replace("<NBT>", String.valueOf(contain_nbt)); 
      p.sendMessage(temp);
    } else {
      String temp = CharmRealm.pluginVariable.Lang_YML.getString("PlaceReachMaxTile");
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
