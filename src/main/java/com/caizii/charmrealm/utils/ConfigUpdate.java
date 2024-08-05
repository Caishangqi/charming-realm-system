package com.caizii.charmrealm.utils;

import com.caizii.charmrealm.CharmRealm;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigUpdate {
  public static void update() {
    boolean config_check = false;
    boolean lang_check = false;
    boolean gui_check = false;
    if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Version")) {
      CharmRealm.JavaPlugin.getConfig().set("Version", Double.valueOf(2.0296D));
      CharmRealm.JavaPlugin.saveConfig();
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.97D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("ClearInventoryBeforeCreate")) {
        CharmRealm.JavaPlugin.getConfig().set("ClearInventoryBeforeCreate", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().getString("DispathCommand") == null) {
        List<String> list = new ArrayList<>();
        CharmRealm.JavaPlugin.getConfig().set("DispathCommand", list);
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().getString("ArmorStand") == null) {
        CharmRealm.JavaPlugin.getConfig().set("ArmorStand", Integer.valueOf(-1));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("AdminSetLevelSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("AdminSetLevelSuccess", "§7[CharmRealm] §c成功设置该领域的等级!");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("ClearInventoryBeforeCreate") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("ClearInventoryBeforeCreate", "§7[CharmRealm] §d创建领域时背包已清空完毕!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.98D &&
      !CharmRealm.JavaPlugin.getConfig().isConfigurationSection("EnableAutoRespawnInHome")) {
      CharmRealm.JavaPlugin.getConfig().set("EnableAutoRespawnInHome", Boolean.valueOf(false));
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.99D) {
      if (CharmRealm.pluginVariable.GUI_YML.getString("Next") == null) {
        CharmRealm.pluginVariable.GUI_YML.set("Next", "§a>>> Next");
        gui_check = true;
      } 
      if (CharmRealm.pluginVariable.GUI_YML.getString("NextMaterial") == null) {
        CharmRealm.pluginVariable.GUI_YML.set("NextMaterial", "FEATHER");
        gui_check = true;
      } 
      if (CharmRealm.pluginVariable.GUI_YML.getString("Prev") == null) {
        CharmRealm.pluginVariable.GUI_YML.set("Prev", "§a>>> Prev");
        gui_check = true;
      } 
      if (CharmRealm.pluginVariable.GUI_YML.getString("PrevMaterial") == null) {
        CharmRealm.pluginVariable.GUI_YML.set("PrevMaterial", "FEATHER");
        gui_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("UpdateLanguageMessage") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("UpdateLanguageMessage", "§7[CharmRealm] §a配置文件Language.yml已自动更");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("UpdateGuiMessage") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("UpdateGuiMessage", "§7[CharmRealm] §a配置文件Gui.yml已自动更");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("UpdateConfigMessage") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("UpdateConfigMessage", "§7[CharmRealm] §a配置文件Config.yml已自动更");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.992D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("DisableFunctionButTeleport")) {
        CharmRealm.JavaPlugin.getConfig().set("DisableFunctionButTeleport", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("DisableFunctionButTeleport") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("DisableFunctionButTeleport", "§7[CharmRealm] §d已为您关闭除了GUI和传送指令以外其他所有功");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("DisableFunctionTip") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("DisableFunctionTip", "§7[CharmRealm] §d本插件除了传送以外的其他功能已被服主关闭,请前领域服务器使用该命令");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.993D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("EnableMobSpawn") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("EnableMobSpawn", "§7[CharmRealm] §d成功启领域的刷�功");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("DisableMobSpawn") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("DisableMobSpawn", "§7[CharmRealm] §d成功关闭领域的刷怪功");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("AutoUpdateHomeLevel") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("AutoUpdateHomeLevel", "§7[CharmRealm] §d测到您有更高级别的领域等级权,为您自动提升领域等级!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.994D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("EnableTilesAndChunksAndDropItemsStatisticsTop")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableTilesAndChunksAndDropItemsStatisticsTop", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("ShowAmount")) {
        CharmRealm.JavaPlugin.getConfig().set("ShowAmount", Integer.valueOf(8));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("ShowTimes")) {
        CharmRealm.JavaPlugin.getConfig().set("ShowTimes", Integer.valueOf(300));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("OneTileTick")) {
        CharmRealm.JavaPlugin.getConfig().set("OneTileTick", Double.valueOf(0.005D));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("OneEntityTick")) {
        CharmRealm.JavaPlugin.getConfig().set("OneEntityTick", Double.valueOf(0.005D));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("OneChunkTick")) {
        CharmRealm.JavaPlugin.getConfig().set("OneChunkTick", Double.valueOf(0.0D));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("OneDropTick")) {
        CharmRealm.JavaPlugin.getConfig().set("OneDropTick", Double.valueOf(0.001D));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("FormatInfo")) {
        CharmRealm.JavaPlugin.getConfig().set("FormatInfo", "%.2f");
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("StatisticsTop")) {
        List<String> list = new ArrayList<>();
        list.add("§7当前服务器玩家世界占用情况：");
        list.add("§8§m————————————————————————————————————————————————————————");
        CharmRealm.JavaPlugin.getConfig().set("StatisticsTop", list);
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("ShowFormat")) {
        CharmRealm.JavaPlugin.getConfig().set("ShowFormat", "§6§l#<index> §a<world> §8的世 §7<tile> §8Tiles §7<chunk> §8区块  §7<entity> §8实体  §7<drop> §8掉落  §8每Tick耗时 §7<tps> §8Ms");
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("StatisticsEnd")) {
        List<String> list = new ArrayList<>();
        list.add("§8§m————————————————————————————————————————————————————————");
        list.add("§a上方世界仅供参�，如果如果存在你的谁请及时清理掉落物和生物");
        CharmRealm.JavaPlugin.getConfig().set("StatisticsEnd", list);
        config_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.996D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("DifficultyModify") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("DifficultyModify", "§7[CharmRealm] §d为您当前领域调整<Mode>模式!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.997D) {
      if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("AutoReCreateInLowerLagHome")) {
        CharmRealm.JavaPlugin.getConfig().set("AutoReCreateInLowerLagHome", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("StartLowestLagServer") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("StartLowestLagServer", "§7[CharmRealm] §d正在为您进行均衡负载模式进行创建领域,请等待~");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.998D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("WorldIsNotExist") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("WorldIsNotExist", "§7[CharmRealm] §d世界不存,请检查世界存档是否存放在地图目录/本插件地图目录下~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("WorldTeleport") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("WorldTeleport", "§7[CharmRealm] §d世界传�成功~");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 1.999D &&
      CharmRealm.JavaPlugin.getConfig().isConfigurationSection("DecideBy")) {
      CharmRealm.JavaPlugin.getConfig().set("DecideBy", "TPS");
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.0D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("ProtectFarm") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("ProtectFarm", "§7[CharmRealm] §d当前耕地被保护着,您没有权,请勿践踏!");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("ProtectEntity") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("ProtectEntity", "§7[CharmRealm] §d当前实体被保护着,您没有权,请勿触碰!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.001D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("SoilTypeError") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SoilTypeError", "§7[CharmRealm] §dConfig.yml配置的�地类型SoilType错误,类型可以选择:SOIL/FARMLAND,1.16.5-Cat端使用FARMLAND");
        lang_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().getString("SoilType") == null) {
        CharmRealm.JavaPlugin.getConfig().set("SoilType", "SOIL");
        config_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.002D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("PlaceInMaxHeight") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("PlaceInMaxHeight", "§7[CharmRealm] §d抱歉,当前您放置的方块超过了领域高度上,请勿高处放置方块");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("EnableHeightLimit") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("EnableHeightLimit", "§7[CharmRealm] §a您开启了领域放置高高度的监听");
        lang_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("MaxHeight")) {
        CharmRealm.JavaPlugin.getConfig().set("MaxHeight", Integer.valueOf(255));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().getBoolean("EnableHeightLimit")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableHeightLimit", Boolean.valueOf(true));
        config_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.005D) {
      if (!CharmRealm.JavaPlugin.getConfig().getBoolean("EnableClearExtraBlocks")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableClearExtraBlocks", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("ClearExtraBlocks") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("ClearExtraBlocks", "§7[CharmRealm] §c测到区块/世界放置方块过多,为您自动清空掉~");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.006D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("HikariCP.connectionTimeout")) {
        CharmRealm.JavaPlugin.getConfig().set("HikariCP.connectionTimeout", Integer.valueOf(30000));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("HikariCP.minimumIdle")) {
        CharmRealm.JavaPlugin.getConfig().set("HikariCP.minimumIdle", Integer.valueOf(10));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("HikariCP.maximumPoolSize")) {
        CharmRealm.JavaPlugin.getConfig().set("HikariCP.maximumPoolSize", Integer.valueOf(50));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("UpdateNoPoints") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("UpdateNoPoints", "§7[CharmRealm] §c点券不足<NeedPoints>");
        lang_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.007D &&
      !CharmRealm.JavaPlugin.getConfig().getBoolean("AutoMoveWorldFilesToOther")) {
      CharmRealm.JavaPlugin.getConfig().set("AutoMoveWorldFilesToOther", Boolean.valueOf(false));
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.008D) {
      if (!CharmRealm.JavaPlugin.getConfig().getBoolean("CustomEntityMax")) {
        CharmRealm.JavaPlugin.getConfig().set("CustomEntityMax", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().getStringList("EntityList") == null) {
        CharmRealm.JavaPlugin.getConfig().set("EntityList", new ArrayList());
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().getBoolean("CheckEntityInterval")) {
        CharmRealm.JavaPlugin.getConfig().set("CheckEntityInterval", Integer.valueOf(60));
        config_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.009D) {
      if (!CharmRealm.JavaPlugin.getConfig().getBoolean("EnableBlackItemsUseInNoPermission")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableBlackItemsUseInNoPermission", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().getStringList("BlackItems") == null) {
        CharmRealm.JavaPlugin.getConfig().set("BlackItems", new ArrayList());
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.CommandUser")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.CommandUser", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Visit")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Visit", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.SetSpawn")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.SetSpawn", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Nether")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Nether", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.End")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.End", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Rain")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Rain", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Sun")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Sun", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Night")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Night", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Day")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Day", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Create-1")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Create-1", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Create-2")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Create-2", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.Create-airland")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.Create-airland", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.LockTime")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.LockTime", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("Permission.LockWeather")) {
        CharmRealm.JavaPlugin.getConfig().set("Permission.LockWeather", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("TakeBlackItemsInNoPermissionHome") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("TakeBlackItemsInNoPermissionHome", "§7[CharmRealm] §c抱歉,禁止携带违禁品进入无权限的家,关键:<type>");
        lang_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.012D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("UnOptimizeWorlds")) {
        List<String> list = new ArrayList<>();
        list.add("ZC");
        CharmRealm.JavaPlugin.getConfig().set("UnOptimizeWorlds", list);
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("FlowerAdd")) {
        CharmRealm.JavaPlugin.getConfig().set("FlowerAdd", Double.valueOf(0.3D));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("PopularityAdd")) {
        CharmRealm.JavaPlugin.getConfig().set("PopularityAdd", Double.valueOf(0.1D));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("PopularityAdd") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("PopularityAdd", "§7[CharmRealm] §d您为<Name>的领域增加了1点人");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("PopularityAddToOwnerAndOP") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("PopularityAddToOwnerAndOP", "§7[CharmRealm] §d您的领域<Player>访问,增加1点人");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("FlowersAdd") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("FlowersAdd", "§7[CharmRealm] §d您为<Name>的领域�上了一束鲜,<Now>/<Max>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("FlowersAddToOwnerAndOP") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("FlowersAddToOwnerAndOP", "§7[CharmRealm] §d您的领域<Player>投�了束鲜");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("FlowersMax") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("FlowersMax", "§7[CharmRealm] §d今日鲜花已经用完,<Max>/<Max>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("FlowersMySelf") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("FlowersMySelf", "§7[CharmRealm] §d抱歉,鲜花无法赠�给自己拥有权限的家");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("GiftAdd") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("GiftAdd", "§7[CharmRealm] §d领域的礼物盒<Name>赠�了物品,快去看看,点我打开~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("GiftSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("GiftSuccess", "§7[CharmRealm] §d您赠送了手上的物品给<Name>,感谢馈赠~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("GiftFail") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("GiftFail", "§7[CharmRealm] §d该领域的礼物盒已,无法赠�~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionOpenGift") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionOpenGift", "§7[CharmRealm] §d缺乏权限:SelfHome.Gift.Open,无法打开礼物盒~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionOpenOthersGift") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionOpenOthersGift", "§7[CharmRealm] §d您没有权限打他人领域的礼物盒~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionSendTheItemToGift") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionSendTheItemToGift", "§7[CharmRealm] §d缺乏权限:SelfHome.Gift.Send,无法打开礼物盒~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("HasAlreadyOpenByOthers") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("HasAlreadyOpenByOthers", "§7[CharmRealm] §d礼物盒正在被玩家<Name>打开,目前无法查看~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SendButTheHandIsAir") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SendButTheHandIsAir", "§7[CharmRealm] §d抱歉,当前您手上的物品为空,无法发�过去哦~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SendButTheInvHasBeenOpen") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SendButTheInvHasBeenOpen", "§7[CharmRealm] §d抱歉,该领域的礼物盒已被打,请等待该玩家关闭后再发?");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SendButTheMyHome") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SendButTheMyHome", "§7[CharmRealm] §d抱歉,您不能给自己送礼物哦~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("InvPlayersGiftGuiButNoPermission") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("InvPlayersGiftGuiButNoPermission", "§7[CharmRealm] §d抱歉,您没有权限打别人的领域收件箱,缺乏权限:SelfHome.Open.Inv");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("GiftGuiTitle") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("GiftGuiTitle", "§8领域礼物");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("GiftLoreAddPrefix") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("GiftLoreAddPrefix", "§a来自:");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NotHookProtocolLib") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NotHookProtocolLib", "§7[CharmRealm] §a没有发现Vault，领域礼物盒相关功能无法正常使用");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("HookProtocolLib") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("HookProtocolLib", "§7[CharmRealm] §a挂载:ProtocolLib 领域礼物盒相关功能可正常使用");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("TodayRefreshPopularityAndFlowerData") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("TodayRefreshPopularityAndFlowerData", "§7[CharmRealm] §a今日的玩 - 鲜花和人 - 数据已自动刷新重");
        lang_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.013D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("NormalJoinWorld")) {
        CharmRealm.JavaPlugin.getConfig().set("NormalJoinWorld", "");
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionSetIcon") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionSetIcon", "§7[CharmRealm] §d缺乏权限:SelfHome.ICON,无法设置图标~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionSetOthersIcon") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionSetOthersIcon", "§7[CharmRealm] §c没有权限设置他人领域的传送ICON图标");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SetIconButHandIsAir") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SetIconButHandIsAir", "§7[CharmRealm] §c抱歉,您不能设置ICON图标为手上的空气~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SetIconSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SetIconSuccess", "§7[CharmRealm] §a设置成功!");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionSetInfo") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionSetInfo", "§7[CharmRealm] §d缺乏权限:SelfHome.Info,无法设置领域标语~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionSetColorInfo") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionSetColorInfo", "§7[CharmRealm] §d缺乏权限:SelfHome.Info.Color,无法设置带颜色的标语~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionSetOthersInfo") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionSetOthersInfo", "§7[CharmRealm] §c没有权限设置他人领域的领域标语~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SetInfoSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SetInfoSuccess", "§7[CharmRealm] §a设置成功!换行符为逗号,");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.isConfigurationSection("VisitGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§d§l§m---------------%SelfHome_World_Name_<Name>%§d§l§m---------------");
        list.add("§8领域鲜花: §6%SelfHome_World_Flower_<Name>%");
        list.add("§8领域人气: §6%SelfHome_World_Popularity_<Name>%");
        list.add("§8领域热度指数: §6%SelfHome_World_Calc_<Name>%");
        list.add("");
        list.add("§8领域管理: §6%SelfHome_World_ManageList_<Name>%");
        list.add("§8领域信任: §6%SelfHome_World_TrustList_<Name>%");
        list.add("§8领域黑名: §6%SelfHome_World_BlackList_<Name>%");
        list.add("");
        list.add("§8领域等级: §6%SelfHome_World_Level_<Name>%");
        list.add("§8领域类型: §6%SelfHome_World_Type_<Name>%");
        list.add("");
        list.add("§d§l§m---------------%SelfHome_World_Name_<Name>%§d§l§m---------------");
        CharmRealm.pluginVariable.GUI_YML.set("VisitGuiLores", list);
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("VisitSlogan")) {
        CharmRealm.pluginVariable.GUI_YML.set("VisitSlogan", Boolean.valueOf(true));
        gui_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.014D)
      if (CharmRealm.pluginVariable.Lang_YML.getString("WantToPutItemIntoGiftChest") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("WantToPutItemIntoGiftChest", "§7[CharmRealm] §d抱歉,礼物盒仓库并不是给你存放你的物品的哦~");
        lang_check = true;
      }  
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.017D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("OperatorSendGiftButOpen") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("OperatorSendGiftButOpen", "§7[CharmRealm] §d抱歉,管理员正在派发福利礼包到您的收件,已为您自动关闭界,请重新打~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SuccessedSendToAll") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SuccessedSendToAll", "§7[CharmRealm] §d您已经成功发送手上的物品到礼物盒的领域列:<List>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("FailedSendToAll") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("FailedSendToAll", "§7[CharmRealm] §d以下领域列表发�失,礼物盒已:<List>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("PopularityAddButHomeIsNotExist") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("PopularityAddButHomeIsNotExist", "§7[CharmRealm] §d人气设置失败,该领域不存在");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("FlowerAddButHomeIsNotExist") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("FlowerAddButHomeIsNotExist", "§7[CharmRealm] §d鲜花设置失败,该领域不存在");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("PopularityAddSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("PopularityAddSuccess", "§7[CharmRealm] §d人气设置成功,目前的人气�为<Now>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("FlowerAddSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("FlowerAddSuccess", "§7[CharmRealm] §d鲜花设置成功,目前的鲜花�为<Now>");
        lang_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.018D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("EnableAsnycTime")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableAsnycTime", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("AsyncTimeWorld")) {
        CharmRealm.JavaPlugin.getConfig().set("AsyncTimeWorld", "world");
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("RealisticSeasons")) {
        CharmRealm.JavaPlugin.getConfig().set("RealisticSeasons", Boolean.valueOf(false));
        config_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.019D &&
      CharmRealm.pluginVariable.Lang_YML.getString("DisableAutoSaveWorld") == null) {
      CharmRealm.pluginVariable.Lang_YML.set("DisableAutoSaveWorld", "§7[CharmRealm] §a领域的自动保存功 已关");
      lang_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.02D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("VisitGuiShowAll")) {
        CharmRealm.JavaPlugin.getConfig().set("VisitGuiShowAll", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("DenyGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§6[左键]§d  -> 添加黑名");
        list.add("§6[右键]§d  -> 移除黑名");
        CharmRealm.pluginVariable.Lang_YML.set("DenyGuiLores", list);
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("InviteGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§6[左键]§d  -> 请成为领域管理员");
        list.add("§6[右键]§d  -> 移除该领域管理员");
        CharmRealm.pluginVariable.Lang_YML.set("InviteGuiLores", list);
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("TrustGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§6[左键]§d  -> 增加信任名单");
        list.add("§6[右键]§d  -> 移除信任名单");
        CharmRealm.pluginVariable.Lang_YML.set("TrustGuiLores", list);
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableCheckGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableCheckGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableCreateGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableCreateGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableDenyGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableDenyGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableInviteGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableInviteGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableMainGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableMainGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableManageGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableManageGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableManageGui2NormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableManageGui2NormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableTrustGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableTrustGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableInviteGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableInviteGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!CharmRealm.pluginVariable.GUI_YML.getBoolean("EnableVisitGuiNormalPane")) {
        CharmRealm.pluginVariable.GUI_YML.set("EnableVisitGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.021D &&
      !CharmRealm.JavaPlugin.getConfig().isConfigurationSection("EnableAutoRespawnInHome")) {
      CharmRealm.JavaPlugin.getConfig().set("EnableAutoRespawnInHome", Boolean.valueOf(false));
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.022D &&
      !CharmRealm.JavaPlugin.getConfig().isConfigurationSection("CustomBackupLocation")) {
      CharmRealm.JavaPlugin.getConfig().set("CustomBackupLocation", "");
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.023D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("EnableChatPrefix")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableChatPrefix", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("EnableAdventureMode")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableAdventureMode", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("PlayerMoveOverBorderHit")) {
        CharmRealm.JavaPlugin.getConfig().set("PlayerMoveOverBorderHit", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("PlayerMoveOverBorderBuff")) {
        CharmRealm.JavaPlugin.getConfig().set("PlayerMoveOverBorderBuff", Boolean.valueOf(true));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("PlayerMoveOverBorderButAdventure") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("PlayerMoveOverBorderButAdventure", "§8[§6CharmRealms§8] §7测到您走出边界过,自动切换为冒险模.");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("OutdatedWorldHasBeenDeleted") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("OutdatedWorldHasBeenDeleted", "§8[§6CharmRealms§8] §7本次清空领域<Amount>,清空的列:<List>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("OutdateWorldConfirm") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("OutdateWorldConfirm", "§8[§6CharmRealms§8] §7您确定要删除<Day>天未被他人或自己访问过的有符合条件的领域?,5秒冷,请再次输>>>");
        lang_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.024D) {
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("EnableEntityInteract")) {
        CharmRealm.JavaPlugin.getConfig().set("EnableEntityInteract", Boolean.valueOf(true));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("GivePopularityButOP") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("GivePopularityButOP", "§8[§6CharmRealms§8] §7您是OP,访问他人领域为隐身状,不给予人气以及提示领域所有者~");
        lang_check = true;
      } 
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.025D &&
      !CharmRealm.JavaPlugin.getConfig().isConfigurationSection("MoveWorldAfterUnLoad")) {
      CharmRealm.JavaPlugin.getConfig().set("MoveWorldAfterUnLoad", Boolean.valueOf(false));
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.026D) {
      if (CharmRealm.pluginVariable.Lang_YML.getString("NoPermissionSetCustomBlockLimit") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("NoPermissionSetCustomBlockLimit", "§8[§6CharmRealms§8] §7抱歉,您无权设置方块的自定义放置功能指令,非OP身份~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SetCustomBlockButHomeIsNull") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SetCustomBlockButHomeIsNull", "§8[§6CharmRealms§8] §7抱歉,参数里的领域不存在~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SetCustomBlockButHomeButHasAlreadyContain") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SetCustomBlockButHomeButHasAlreadyContain", "§8[§6CharmRealms§8] §7抱歉,该领域的该限制已存在~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SetCustomBlockButHomeButNotContain") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SetCustomBlockButHomeButNotContain", "§8[§6CharmRealms§8] §7抱歉,该领域的该限制不存在~");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("SetCustomBlockSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("SetCustomBlockSuccess", "§8[§6CharmRealms§8] §7成功设置<Name>领域的<NBT>方块放置数量为:<Amount>,限制类型为:<Type>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("RemoveCustomBlockSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("RemoveCustomBlockSuccess", "§8[§6CharmRealms§8] §7成功移除<Name>领域的<NBT>方块放置的限制,限制类型为:<Type>");
        lang_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("AddCustomBlockSuccess") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("AddCustomBlockSuccess", "§8[§6CharmRealms§8] §7成功添加<Name>领域的<NBT>方块放置数量<Amount>个,目前新的限制数量为:<NowAmount>,限制类型为:<Type>");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.027D) {
      if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("MultiverseCoreCompability")) {
        CharmRealm.JavaPlugin.getConfig().set("MultiverseCoreCompability", Boolean.valueOf(true));
        config_check = true;
      } 
      if (CharmRealm.pluginVariable.Lang_YML.getString("MultiverseCoreCompability") == null) {
        CharmRealm.pluginVariable.Lang_YML.set("MultiverseCoreCompability", "§8[§6CharmRealms§8] §a挂载到:Multiverse-Core 兼容多世界插件已开启~");
        lang_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().getString("UnAutoSaveWorlds") == null) {
        List<String> list = new ArrayList<>();
        list.add("DIM34676");
        CharmRealm.JavaPlugin.getConfig().set("UnAutoSaveWorlds", list);
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("CheckUpdate")) {
        CharmRealm.JavaPlugin.getConfig().set("CheckUpdate", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("CheckUpdate")) {
        CharmRealm.JavaPlugin.getConfig().set("CheckUpdate", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("CheckHasNewPlugin")) {
        List<String> list = new ArrayList<>();
        list.add("§8[§6CharmRealms§8] §d当前运行的插件版本为:§a<Now>");
        list.add("§8[§6CharmRealms§8] §d找到一个更新的版本:§a<New>");
        list.add("§8[§6CharmRealms§8] §d下载地址: §e<Link>");
        CharmRealm.pluginVariable.Lang_YML.set("CheckHasNewPlugin", list);
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("NowIsTheLatestPlugin")) {
        CharmRealm.pluginVariable.Lang_YML.set("NowIsTheLatestPlugin", "§8[§6CharmRealms§8] §d当前运行的插件版本为最新版:§a<Now>");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("CheckUpdateFailed")) {
        CharmRealm.pluginVariable.Lang_YML.set("CheckUpdateFailed", "§8[§6CharmRealms§8] §c版本更新检查失败!");
        lang_check = true;
      } 
      if (!CharmRealm.JavaPlugin.getConfig().isConfigurationSection("DisablePortalCreate")) {
        CharmRealm.JavaPlugin.getConfig().set("DisablePortalCreate", Boolean.valueOf(true));
        config_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.0283D) {
      if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("FaweSwitch")) {
        CharmRealm.JavaPlugin.getConfig().set("FaweSwitch", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("BorderMaterial")) {
        CharmRealm.JavaPlugin.getConfig().set("BorderMaterial", "BEDROCK");
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("BorderShape")) {
        CharmRealm.JavaPlugin.getConfig().set("BorderShape", "Square");
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("UpdateClearOld")) {
        CharmRealm.JavaPlugin.getConfig().set("UpdateClearOld", Boolean.valueOf(false));
        config_check = true;
      } 
      if (CharmRealm.JavaPlugin.getConfig().isConfigurationSection("KeepSpawnInMemory")) {
        CharmRealm.JavaPlugin.getConfig().set("KeepSpawnInMemory", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("FaweAndWorldEditCompability")) {
        CharmRealm.pluginVariable.Lang_YML.set("FaweAndWorldEditCompability", "§8[§6CharmRealms§8] §a挂载到:Multiverse-FastAsyncWorldEdit 异步形状边界功能已开启~");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("ToggleccWorldEnable")) {
        CharmRealm.pluginVariable.Lang_YML.set("ToggleccWorldEnable", "§8[§6CharmRealms§8] §a成功切换边界显示情况为: 显示");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("ToggleccWorldDisable")) {
        CharmRealm.pluginVariable.Lang_YML.set("ToggleccWorldDisable", "§8[§6CharmRealms§8] §a成功切换边界显示情况为: 隐藏");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.0285D) {
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("BiomeChangeTip")) {
        CharmRealm.pluginVariable.Lang_YML.set("BiomeChangeTip", "§8[§6CharmRealms§8] §a已为您更改当前的区块群系,请重新上线查看生效!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (CharmRealm.JavaPlugin.getConfig().getDouble("Version") < 2.0296D) {
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("WeatherRain")) {
        CharmRealm.pluginVariable.Lang_YML.set("WeatherRain", "§8[§6CharmRealms§8] §a为您切换为雨天");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("WeatherSun")) {
        CharmRealm.pluginVariable.Lang_YML.set("WeatherSun", "§8[§6CharmRealms§8] §a为您切换为晴天");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("TimeDay")) {
        CharmRealm.pluginVariable.Lang_YML.set("TimeDay", "§8[§6CharmRealms§8] §a为您切换为白天");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("TimeNight")) {
        CharmRealm.pluginVariable.Lang_YML.set("TimeNight", "§8[§6CharmRealms§8] §a为您切换为黑天");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("LookSeed")) {
        CharmRealm.pluginVariable.Lang_YML.set("LookSeed", "§8[§6CharmRealms§8] §a当前世界的种子为:<Seed>");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("BiomeError")) {
        CharmRealm.pluginVariable.Lang_YML.set("BiomeError", "§8[§6CharmRealms§8] §a群系类型错误,请检查");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("EnableFly")) {
        CharmRealm.pluginVariable.Lang_YML.set("EnableFly", "§8[§6CharmRealms§8] §a成功为您开启飞行模式");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("DisableFly")) {
        CharmRealm.pluginVariable.Lang_YML.set("DisableFly", "§8[§6CharmRealms§8] §a成功为您关闭飞行模式");
        lang_check = true;
      } 
      if (!CharmRealm.pluginVariable.Lang_YML.isConfigurationSection("ToggleccWorldDisableFlying")) {
        CharmRealm.pluginVariable.Lang_YML.set("ToggleccWorldDisableFlying", "§8[§6CharmRealms§8] §a您离开了飞行的世界,为您自动关闭飞行功能.");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (config_check) {
      CharmRealm.JavaPlugin.getConfig().set("Version", Double.valueOf(2.0296D));
      CharmRealm.JavaPlugin.saveConfig();
      Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("UpdateConfigMessage"));
    } 
    if (lang_check) {
      try {
        CharmRealm.pluginVariable.Lang_YML.save(new File(CharmRealm.JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "Language" + CharmRealm.pluginVariable.file_loc_prefix + CharmRealm.JavaPlugin.getConfig().getString("Language") + ".yml"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
      Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("UpdateLanguageMessage"));
    } 
    if (gui_check) {
      try {
        CharmRealm.pluginVariable.GUI_YML.save(new File(CharmRealm.JavaPlugin.getDataFolder() + CharmRealm.pluginVariable.file_loc_prefix + "GUI" + ".yml"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
      Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("UpdateGuiMessage"));
    } 
  }
}
