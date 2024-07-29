package com.Util;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;

public class ConfigUpdate {
  public static void update() {
    boolean config_check = false;
    boolean lang_check = false;
    boolean gui_check = false;
    if (Main.JavaPlugin.getConfig().isConfigurationSection("Version")) {
      Main.JavaPlugin.getConfig().set("Version", Double.valueOf(2.0296D));
      Main.JavaPlugin.saveConfig();
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.97D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("ClearInventoryBeforeCreate")) {
        Main.JavaPlugin.getConfig().set("ClearInventoryBeforeCreate", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().getString("DispathCommand") == null) {
        List<String> list = new ArrayList<>();
        Main.JavaPlugin.getConfig().set("DispathCommand", list);
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().getString("ArmorStand") == null) {
        Main.JavaPlugin.getConfig().set("ArmorStand", Integer.valueOf(-1));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("AdminSetLevelSuccess") == null) {
        Variable.Lang_YML.set("AdminSetLevelSuccess", "§7[SelfHomeMain] §c成功设置该家园的等级!");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("ClearInventoryBeforeCreate") == null) {
        Variable.Lang_YML.set("ClearInventoryBeforeCreate", "§7[SelfHomeMain] §d创建家园时背包已清空完毕!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.98D && 
      !Main.JavaPlugin.getConfig().isConfigurationSection("EnableAutoRespawnInHome")) {
      Main.JavaPlugin.getConfig().set("EnableAutoRespawnInHome", Boolean.valueOf(false));
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.99D) {
      if (Variable.GUI_YML.getString("Next") == null) {
        Variable.GUI_YML.set("Next", "§a>>> Next");
        gui_check = true;
      } 
      if (Variable.GUI_YML.getString("NextMaterial") == null) {
        Variable.GUI_YML.set("NextMaterial", "FEATHER");
        gui_check = true;
      } 
      if (Variable.GUI_YML.getString("Prev") == null) {
        Variable.GUI_YML.set("Prev", "§a>>> Prev");
        gui_check = true;
      } 
      if (Variable.GUI_YML.getString("PrevMaterial") == null) {
        Variable.GUI_YML.set("PrevMaterial", "FEATHER");
        gui_check = true;
      } 
      if (Variable.Lang_YML.getString("UpdateLanguageMessage") == null) {
        Variable.Lang_YML.set("UpdateLanguageMessage", "§7[SelfHomeMain] §a配置文件Language.yml已自动更");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("UpdateGuiMessage") == null) {
        Variable.Lang_YML.set("UpdateGuiMessage", "§7[SelfHomeMain] §a配置文件Gui.yml已自动更");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("UpdateConfigMessage") == null) {
        Variable.Lang_YML.set("UpdateConfigMessage", "§7[SelfHomeMain] §a配置文件Config.yml已自动更");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.992D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("DisableFunctionButTeleport")) {
        Main.JavaPlugin.getConfig().set("DisableFunctionButTeleport", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("DisableFunctionButTeleport") == null) {
        Variable.Lang_YML.set("DisableFunctionButTeleport", "§7[SelfHomeMain] §d已为您关闭除了GUI和传送指令以外其他所有功");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("DisableFunctionTip") == null) {
        Variable.Lang_YML.set("DisableFunctionTip", "§7[SelfHomeMain] §d本插件除了传送以外的其他功能已被服主关闭,请前家园服务器使用该命令");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.993D) {
      if (Variable.Lang_YML.getString("EnableMobSpawn") == null) {
        Variable.Lang_YML.set("EnableMobSpawn", "§7[SelfHomeMain] §d成功启家园的刷�功");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("DisableMobSpawn") == null) {
        Variable.Lang_YML.set("DisableMobSpawn", "§7[SelfHomeMain] §d成功关闭家园的刷怪功");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("AutoUpdateHomeLevel") == null) {
        Variable.Lang_YML.set("AutoUpdateHomeLevel", "§7[SelfHomeMain] §d测到您有更高级别的家园等级权,为您自动提升家园等级!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.994D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("EnableTilesAndChunksAndDropItemsStatisticsTop")) {
        Main.JavaPlugin.getConfig().set("EnableTilesAndChunksAndDropItemsStatisticsTop", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("ShowAmount")) {
        Main.JavaPlugin.getConfig().set("ShowAmount", Integer.valueOf(8));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("ShowTimes")) {
        Main.JavaPlugin.getConfig().set("ShowTimes", Integer.valueOf(300));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("OneTileTick")) {
        Main.JavaPlugin.getConfig().set("OneTileTick", Double.valueOf(0.005D));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("OneEntityTick")) {
        Main.JavaPlugin.getConfig().set("OneEntityTick", Double.valueOf(0.005D));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("OneChunkTick")) {
        Main.JavaPlugin.getConfig().set("OneChunkTick", Double.valueOf(0.0D));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("OneDropTick")) {
        Main.JavaPlugin.getConfig().set("OneDropTick", Double.valueOf(0.001D));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("FormatInfo")) {
        Main.JavaPlugin.getConfig().set("FormatInfo", "%.2f");
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("StatisticsTop")) {
        List<String> list = new ArrayList<>();
        list.add("§7当前服务器玩家世界占用情况：");
        list.add("§8§m————————————————————————————————————————————————————————");
        Main.JavaPlugin.getConfig().set("StatisticsTop", list);
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("ShowFormat")) {
        Main.JavaPlugin.getConfig().set("ShowFormat", "§6§l#<index> §a<world> §8的世 §7<tile> §8Tiles §7<chunk> §8区块  §7<entity> §8实体  §7<drop> §8掉落  §8每Tick耗时 §7<tps> §8Ms");
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("StatisticsEnd")) {
        List<String> list = new ArrayList<>();
        list.add("§8§m————————————————————————————————————————————————————————");
        list.add("§a上方世界仅供参�，如果如果存在你的谁请及时清理掉落物和生物");
        Main.JavaPlugin.getConfig().set("StatisticsEnd", list);
        config_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.996D) {
      if (Variable.Lang_YML.getString("DifficultyModify") == null) {
        Variable.Lang_YML.set("DifficultyModify", "§7[SelfHomeMain] §d为您当前家园调整<Mode>模式!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.997D) {
      if (Main.JavaPlugin.getConfig().isConfigurationSection("AutoReCreateInLowerLagHome")) {
        Main.JavaPlugin.getConfig().set("AutoReCreateInLowerLagHome", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("StartLowestLagServer") == null) {
        Variable.Lang_YML.set("StartLowestLagServer", "§7[SelfHomeMain] §d正在为您进行均衡负载模式进行创建家园,请等待~");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.998D) {
      if (Variable.Lang_YML.getString("WorldIsNotExist") == null) {
        Variable.Lang_YML.set("WorldIsNotExist", "§7[SelfHomeMain] §d世界不存,请检查世界存档是否存放在地图目录/本插件地图目录下~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("WorldTeleport") == null) {
        Variable.Lang_YML.set("WorldTeleport", "§7[SelfHomeMain] §d世界传�成功~");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 1.999D && 
      Main.JavaPlugin.getConfig().isConfigurationSection("DecideBy")) {
      Main.JavaPlugin.getConfig().set("DecideBy", "TPS");
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.0D) {
      if (Variable.Lang_YML.getString("ProtectFarm") == null) {
        Variable.Lang_YML.set("ProtectFarm", "§7[SelfHomeMain] §d当前耕地被保护着,您没有权,请勿践踏!");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("ProtectEntity") == null) {
        Variable.Lang_YML.set("ProtectEntity", "§7[SelfHomeMain] §d当前实体被保护着,您没有权,请勿触碰!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.001D) {
      if (Variable.Lang_YML.getString("SoilTypeError") == null) {
        Variable.Lang_YML.set("SoilTypeError", "§7[SelfHomeMain] §dConfig.yml配置的�地类型SoilType错误,类型可以选择:SOIL/FARMLAND,1.16.5-Cat端使用FARMLAND");
        lang_check = true;
      } 
      if (Main.JavaPlugin.getConfig().getString("SoilType") == null) {
        Main.JavaPlugin.getConfig().set("SoilType", "SOIL");
        config_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.002D) {
      if (Variable.Lang_YML.getString("PlaceInMaxHeight") == null) {
        Variable.Lang_YML.set("PlaceInMaxHeight", "§7[SelfHomeMain] §d抱歉,当前您放置的方块超过了家园高度上,请勿高处放置方块");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("EnableHeightLimit") == null) {
        Variable.Lang_YML.set("EnableHeightLimit", "§7[SelfHomeMain] §a您开启了家园放置高高度的监听");
        lang_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("MaxHeight")) {
        Main.JavaPlugin.getConfig().set("MaxHeight", Integer.valueOf(255));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().getBoolean("EnableHeightLimit")) {
        Main.JavaPlugin.getConfig().set("EnableHeightLimit", Boolean.valueOf(true));
        config_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.005D) {
      if (!Main.JavaPlugin.getConfig().getBoolean("EnableClearExtraBlocks")) {
        Main.JavaPlugin.getConfig().set("EnableClearExtraBlocks", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("ClearExtraBlocks") == null) {
        Variable.Lang_YML.set("ClearExtraBlocks", "§7[SelfHomeMain] §c测到区块/世界放置方块过多,为您自动清空掉~");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.006D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("HikariCP.connectionTimeout")) {
        Main.JavaPlugin.getConfig().set("HikariCP.connectionTimeout", Integer.valueOf(30000));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("HikariCP.minimumIdle")) {
        Main.JavaPlugin.getConfig().set("HikariCP.minimumIdle", Integer.valueOf(10));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("HikariCP.maximumPoolSize")) {
        Main.JavaPlugin.getConfig().set("HikariCP.maximumPoolSize", Integer.valueOf(50));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("UpdateNoPoints") == null) {
        Variable.Lang_YML.set("UpdateNoPoints", "§7[SelfHomeMain] §c点券不足<NeedPoints>");
        lang_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.007D && 
      !Main.JavaPlugin.getConfig().getBoolean("AutoMoveWorldFilesToOther")) {
      Main.JavaPlugin.getConfig().set("AutoMoveWorldFilesToOther", Boolean.valueOf(false));
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.008D) {
      if (!Main.JavaPlugin.getConfig().getBoolean("CustomEntityMax")) {
        Main.JavaPlugin.getConfig().set("CustomEntityMax", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().getStringList("EntityList") == null) {
        Main.JavaPlugin.getConfig().set("EntityList", new ArrayList());
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().getBoolean("CheckEntityInterval")) {
        Main.JavaPlugin.getConfig().set("CheckEntityInterval", Integer.valueOf(60));
        config_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.009D) {
      if (!Main.JavaPlugin.getConfig().getBoolean("EnableBlackItemsUseInNoPermission")) {
        Main.JavaPlugin.getConfig().set("EnableBlackItemsUseInNoPermission", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().getStringList("BlackItems") == null) {
        Main.JavaPlugin.getConfig().set("BlackItems", new ArrayList());
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.CommandUser")) {
        Main.JavaPlugin.getConfig().set("Permission.CommandUser", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Visit")) {
        Main.JavaPlugin.getConfig().set("Permission.Visit", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.SetSpawn")) {
        Main.JavaPlugin.getConfig().set("Permission.SetSpawn", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Nether")) {
        Main.JavaPlugin.getConfig().set("Permission.Nether", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.End")) {
        Main.JavaPlugin.getConfig().set("Permission.End", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Rain")) {
        Main.JavaPlugin.getConfig().set("Permission.Rain", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Sun")) {
        Main.JavaPlugin.getConfig().set("Permission.Sun", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Night")) {
        Main.JavaPlugin.getConfig().set("Permission.Night", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Day")) {
        Main.JavaPlugin.getConfig().set("Permission.Day", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Create-1")) {
        Main.JavaPlugin.getConfig().set("Permission.Create-1", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Create-2")) {
        Main.JavaPlugin.getConfig().set("Permission.Create-2", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.Create-airland")) {
        Main.JavaPlugin.getConfig().set("Permission.Create-airland", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.LockTime")) {
        Main.JavaPlugin.getConfig().set("Permission.LockTime", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("Permission.LockWeather")) {
        Main.JavaPlugin.getConfig().set("Permission.LockWeather", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("TakeBlackItemsInNoPermissionHome") == null) {
        Variable.Lang_YML.set("TakeBlackItemsInNoPermissionHome", "§7[SelfHomeMain] §c抱歉,禁止携带违禁品进入无权限的家,关键:<type>");
        lang_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.012D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("UnOptimizeWorlds")) {
        List<String> list = new ArrayList<>();
        list.add("ZC");
        Main.JavaPlugin.getConfig().set("UnOptimizeWorlds", list);
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("FlowerAdd")) {
        Main.JavaPlugin.getConfig().set("FlowerAdd", Double.valueOf(0.3D));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("PopularityAdd")) {
        Main.JavaPlugin.getConfig().set("PopularityAdd", Double.valueOf(0.1D));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("PopularityAdd") == null) {
        Variable.Lang_YML.set("PopularityAdd", "§7[SelfHomeMain] §d您为<Name>的家园增加了1点人");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("PopularityAddToOwnerAndOP") == null) {
        Variable.Lang_YML.set("PopularityAddToOwnerAndOP", "§7[SelfHomeMain] §d您的家园<Player>访问,增加1点人");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("FlowersAdd") == null) {
        Variable.Lang_YML.set("FlowersAdd", "§7[SelfHomeMain] §d您为<Name>的家园�上了一束鲜,<Now>/<Max>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("FlowersAddToOwnerAndOP") == null) {
        Variable.Lang_YML.set("FlowersAddToOwnerAndOP", "§7[SelfHomeMain] §d您的家园<Player>投�了束鲜");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("FlowersMax") == null) {
        Variable.Lang_YML.set("FlowersMax", "§7[SelfHomeMain] §d今日鲜花已经用完,<Max>/<Max>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("FlowersMySelf") == null) {
        Variable.Lang_YML.set("FlowersMySelf", "§7[SelfHomeMain] §d抱歉,鲜花无法赠�给自己拥有权限的家");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("GiftAdd") == null) {
        Variable.Lang_YML.set("GiftAdd", "§7[SelfHomeMain] §d家园的礼物盒<Name>赠�了物品,快去看看,点我打开~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("GiftSuccess") == null) {
        Variable.Lang_YML.set("GiftSuccess", "§7[SelfHomeMain] §d您赠送了手上的物品给<Name>,感谢馈赠~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("GiftFail") == null) {
        Variable.Lang_YML.set("GiftFail", "§7[SelfHomeMain] §d该家园的礼物盒已,无法赠�~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionOpenGift") == null) {
        Variable.Lang_YML.set("NoPermissionOpenGift", "§7[SelfHomeMain] §d缺乏权限:SelfHome.Gift.Open,无法打开礼物盒~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionOpenOthersGift") == null) {
        Variable.Lang_YML.set("NoPermissionOpenOthersGift", "§7[SelfHomeMain] §d您没有权限打他人家园的礼物盒~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionSendTheItemToGift") == null) {
        Variable.Lang_YML.set("NoPermissionSendTheItemToGift", "§7[SelfHomeMain] §d缺乏权限:SelfHome.Gift.Send,无法打开礼物盒~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("HasAlreadyOpenByOthers") == null) {
        Variable.Lang_YML.set("HasAlreadyOpenByOthers", "§7[SelfHomeMain] §d礼物盒正在被玩家<Name>打开,目前无法查看~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SendButTheHandIsAir") == null) {
        Variable.Lang_YML.set("SendButTheHandIsAir", "§7[SelfHomeMain] §d抱歉,当前您手上的物品为空,无法发�过去哦~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SendButTheInvHasBeenOpen") == null) {
        Variable.Lang_YML.set("SendButTheInvHasBeenOpen", "§7[SelfHomeMain] §d抱歉,该家园的礼物盒已被打,请等待该玩家关闭后再发?");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SendButTheMyHome") == null) {
        Variable.Lang_YML.set("SendButTheMyHome", "§7[SelfHomeMain] §d抱歉,您不能给自己送礼物哦~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("InvPlayersGiftGuiButNoPermission") == null) {
        Variable.Lang_YML.set("InvPlayersGiftGuiButNoPermission", "§7[SelfHomeMain] §d抱歉,您没有权限打别人的家园收件箱,缺乏权限:SelfHome.Open.Inv");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("GiftGuiTitle") == null) {
        Variable.Lang_YML.set("GiftGuiTitle", "§8家园礼物");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("GiftLoreAddPrefix") == null) {
        Variable.Lang_YML.set("GiftLoreAddPrefix", "§a来自:");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NotHookProtocolLib") == null) {
        Variable.Lang_YML.set("NotHookProtocolLib", "§7[SelfHomeMain] §a没有发现Vault，家园礼物盒相关功能无法正常使用");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("HookProtocolLib") == null) {
        Variable.Lang_YML.set("HookProtocolLib", "§7[SelfHomeMain] §a挂载:ProtocolLib 家园礼物盒相关功能可正常使用");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("TodayRefreshPopularityAndFlowerData") == null) {
        Variable.Lang_YML.set("TodayRefreshPopularityAndFlowerData", "§7[SelfHomeMain] §a今日的玩 - 鲜花和人 - 数据已自动刷新重");
        lang_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.013D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("NormalJoinWorld")) {
        Main.JavaPlugin.getConfig().set("NormalJoinWorld", "");
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionSetIcon") == null) {
        Variable.Lang_YML.set("NoPermissionSetIcon", "§7[SelfHomeMain] §d缺乏权限:SelfHome.ICON,无法设置图标~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionSetOthersIcon") == null) {
        Variable.Lang_YML.set("NoPermissionSetOthersIcon", "§7[SelfHomeMain] §c没有权限设置他人家园的传送ICON图标");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SetIconButHandIsAir") == null) {
        Variable.Lang_YML.set("SetIconButHandIsAir", "§7[SelfHomeMain] §c抱歉,您不能设置ICON图标为手上的空气~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SetIconSuccess") == null) {
        Variable.Lang_YML.set("SetIconSuccess", "§7[SelfHomeMain] §a设置成功!");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionSetInfo") == null) {
        Variable.Lang_YML.set("NoPermissionSetInfo", "§7[SelfHomeMain] §d缺乏权限:SelfHome.Info,无法设置家园标语~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionSetColorInfo") == null) {
        Variable.Lang_YML.set("NoPermissionSetColorInfo", "§7[SelfHomeMain] §d缺乏权限:SelfHome.Info.Color,无法设置带颜色的标语~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("NoPermissionSetOthersInfo") == null) {
        Variable.Lang_YML.set("NoPermissionSetOthersInfo", "§7[SelfHomeMain] §c没有权限设置他人家园的家园标语~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SetInfoSuccess") == null) {
        Variable.Lang_YML.set("SetInfoSuccess", "§7[SelfHomeMain] §a设置成功!换行符为逗号,");
        lang_check = true;
      } 
      if (!Variable.GUI_YML.isConfigurationSection("VisitGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§d§l§m---------------%SelfHome_World_Name_<Name>%§d§l§m---------------");
        list.add("§8家园鲜花: §6%SelfHome_World_Flower_<Name>%");
        list.add("§8家园人气: §6%SelfHome_World_Popularity_<Name>%");
        list.add("§8家园热度指数: §6%SelfHome_World_Calc_<Name>%");
        list.add("");
        list.add("§8家园管理: §6%SelfHome_World_ManageList_<Name>%");
        list.add("§8家园信任: §6%SelfHome_World_TrustList_<Name>%");
        list.add("§8家园黑名: §6%SelfHome_World_BlackList_<Name>%");
        list.add("");
        list.add("§8家园等级: §6%SelfHome_World_Level_<Name>%");
        list.add("§8家园类型: §6%SelfHome_World_Type_<Name>%");
        list.add("");
        list.add("§d§l§m---------------%SelfHome_World_Name_<Name>%§d§l§m---------------");
        Variable.GUI_YML.set("VisitGuiLores", list);
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("VisitSlogan")) {
        Variable.GUI_YML.set("VisitSlogan", Boolean.valueOf(true));
        gui_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.014D)
      if (Variable.Lang_YML.getString("WantToPutItemIntoGiftChest") == null) {
        Variable.Lang_YML.set("WantToPutItemIntoGiftChest", "§7[SelfHomeMain] §d抱歉,礼物盒仓库并不是给你存放你的物品的哦~");
        lang_check = true;
      }  
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.017D) {
      if (Variable.Lang_YML.getString("OperatorSendGiftButOpen") == null) {
        Variable.Lang_YML.set("OperatorSendGiftButOpen", "§7[SelfHomeMain] §d抱歉,管理员正在派发福利礼包到您的收件,已为您自动关闭界,请重新打~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SuccessedSendToAll") == null) {
        Variable.Lang_YML.set("SuccessedSendToAll", "§7[SelfHomeMain] §d您已经成功发送手上的物品到礼物盒的家园列:<List>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("FailedSendToAll") == null) {
        Variable.Lang_YML.set("FailedSendToAll", "§7[SelfHomeMain] §d以下家园列表发�失,礼物盒已:<List>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("PopularityAddButHomeIsNotExist") == null) {
        Variable.Lang_YML.set("PopularityAddButHomeIsNotExist", "§7[SelfHomeMain] §d人气设置失败,该家园不存在");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("FlowerAddButHomeIsNotExist") == null) {
        Variable.Lang_YML.set("FlowerAddButHomeIsNotExist", "§7[SelfHomeMain] §d鲜花设置失败,该家园不存在");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("PopularityAddSuccess") == null) {
        Variable.Lang_YML.set("PopularityAddSuccess", "§7[SelfHomeMain] §d人气设置成功,目前的人气�为<Now>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("FlowerAddSuccess") == null) {
        Variable.Lang_YML.set("FlowerAddSuccess", "§7[SelfHomeMain] §d鲜花设置成功,目前的鲜花�为<Now>");
        lang_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.018D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("EnableAsnycTime")) {
        Main.JavaPlugin.getConfig().set("EnableAsnycTime", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("AsyncTimeWorld")) {
        Main.JavaPlugin.getConfig().set("AsyncTimeWorld", "world");
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("RealisticSeasons")) {
        Main.JavaPlugin.getConfig().set("RealisticSeasons", Boolean.valueOf(false));
        config_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.019D && 
      Variable.Lang_YML.getString("DisableAutoSaveWorld") == null) {
      Variable.Lang_YML.set("DisableAutoSaveWorld", "§7[SelfHomeMain] §a家园的自动保存功 已关");
      lang_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.02D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("VisitGuiShowAll")) {
        Main.JavaPlugin.getConfig().set("VisitGuiShowAll", Boolean.valueOf(false));
        config_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("DenyGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§6[左键]§d  -> 添加黑名");
        list.add("§6[右键]§d  -> 移除黑名");
        Variable.Lang_YML.set("DenyGuiLores", list);
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("InviteGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§6[左键]§d  -> 请成为家园管理员");
        list.add("§6[右键]§d  -> 移除该家园管理员");
        Variable.Lang_YML.set("InviteGuiLores", list);
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("TrustGuiLores")) {
        List<String> list = new ArrayList<>();
        list.add("§6[左键]§d  -> 增加信任名单");
        list.add("§6[右键]§d  -> 移除信任名单");
        Variable.Lang_YML.set("TrustGuiLores", list);
        lang_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableCheckGuiNormalPane")) {
        Variable.GUI_YML.set("EnableCheckGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableCreateGuiNormalPane")) {
        Variable.GUI_YML.set("EnableCreateGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableDenyGuiNormalPane")) {
        Variable.GUI_YML.set("EnableDenyGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableInviteGuiNormalPane")) {
        Variable.GUI_YML.set("EnableInviteGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableMainGuiNormalPane")) {
        Variable.GUI_YML.set("EnableMainGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableManageGuiNormalPane")) {
        Variable.GUI_YML.set("EnableManageGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableManageGui2NormalPane")) {
        Variable.GUI_YML.set("EnableManageGui2NormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableTrustGuiNormalPane")) {
        Variable.GUI_YML.set("EnableTrustGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableInviteGuiNormalPane")) {
        Variable.GUI_YML.set("EnableInviteGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
      if (!Variable.GUI_YML.getBoolean("EnableVisitGuiNormalPane")) {
        Variable.GUI_YML.set("EnableVisitGuiNormalPane", Boolean.valueOf(true));
        gui_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.021D && 
      !Main.JavaPlugin.getConfig().isConfigurationSection("EnableAutoRespawnInHome")) {
      Main.JavaPlugin.getConfig().set("EnableAutoRespawnInHome", Boolean.valueOf(false));
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.022D && 
      !Main.JavaPlugin.getConfig().isConfigurationSection("CustomBackupLocation")) {
      Main.JavaPlugin.getConfig().set("CustomBackupLocation", "");
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.023D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("EnableChatPrefix")) {
        Main.JavaPlugin.getConfig().set("EnableChatPrefix", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("EnableAdventureMode")) {
        Main.JavaPlugin.getConfig().set("EnableAdventureMode", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("PlayerMoveOverBorderHit")) {
        Main.JavaPlugin.getConfig().set("PlayerMoveOverBorderHit", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("PlayerMoveOverBorderBuff")) {
        Main.JavaPlugin.getConfig().set("PlayerMoveOverBorderBuff", Boolean.valueOf(true));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("PlayerMoveOverBorderButAdventure") == null) {
        Variable.Lang_YML.set("PlayerMoveOverBorderButAdventure", "§8[§6SelfHomeMain§8] §7测到您走出边界过,自动切换为冒险模.");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("OutdatedWorldHasBeenDeleted") == null) {
        Variable.Lang_YML.set("OutdatedWorldHasBeenDeleted", "§8[§6SelfHomeMain§8] §7本次清空家园<Amount>,清空的列:<List>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("OutdateWorldConfirm") == null) {
        Variable.Lang_YML.set("OutdateWorldConfirm", "§8[§6SelfHomeMain§8] §7您确定要删除<Day>天未被他人或自己访问过的有符合条件的家园?,5秒冷,请再次输>>>");
        lang_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.024D) {
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("EnableEntityInteract")) {
        Main.JavaPlugin.getConfig().set("EnableEntityInteract", Boolean.valueOf(true));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("GivePopularityButOP") == null) {
        Variable.Lang_YML.set("GivePopularityButOP", "§8[§6SelfHomeMain§8] §7您是OP,访问他人家园为隐身状,不给予人气以及提示家园主人~");
        lang_check = true;
      } 
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.025D && 
      !Main.JavaPlugin.getConfig().isConfigurationSection("MoveWorldAfterUnLoad")) {
      Main.JavaPlugin.getConfig().set("MoveWorldAfterUnLoad", Boolean.valueOf(false));
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.026D) {
      if (Variable.Lang_YML.getString("NoPermissionSetCustomBlockLimit") == null) {
        Variable.Lang_YML.set("NoPermissionSetCustomBlockLimit", "§8[§6SelfHomeMain§8] §7抱歉,您无权设置方块的自定义放置功能指令,非OP身份~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SetCustomBlockButHomeIsNull") == null) {
        Variable.Lang_YML.set("SetCustomBlockButHomeIsNull", "§8[§6SelfHomeMain§8] §7抱歉,参数里的家园不存在~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SetCustomBlockButHomeButHasAlreadyContain") == null) {
        Variable.Lang_YML.set("SetCustomBlockButHomeButHasAlreadyContain", "§8[§6SelfHomeMain§8] §7抱歉,该家园的该限制已存在~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SetCustomBlockButHomeButNotContain") == null) {
        Variable.Lang_YML.set("SetCustomBlockButHomeButNotContain", "§8[§6SelfHomeMain§8] §7抱歉,该家园的该限制不存在~");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("SetCustomBlockSuccess") == null) {
        Variable.Lang_YML.set("SetCustomBlockSuccess", "§8[§6SelfHomeMain§8] §7成功设置<Name>家园的<NBT>方块放置数量为:<Amount>,限制类型为:<Type>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("RemoveCustomBlockSuccess") == null) {
        Variable.Lang_YML.set("RemoveCustomBlockSuccess", "§8[§6SelfHomeMain§8] §7成功移除<Name>家园的<NBT>方块放置的限制,限制类型为:<Type>");
        lang_check = true;
      } 
      if (Variable.Lang_YML.getString("AddCustomBlockSuccess") == null) {
        Variable.Lang_YML.set("AddCustomBlockSuccess", "§8[§6SelfHomeMain§8] §7成功添加<Name>家园的<NBT>方块放置数量<Amount>个,目前新的限制数量为:<NowAmount>,限制类型为:<Type>");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.027D) {
      if (Main.JavaPlugin.getConfig().isConfigurationSection("MultiverseCoreCompability")) {
        Main.JavaPlugin.getConfig().set("MultiverseCoreCompability", Boolean.valueOf(true));
        config_check = true;
      } 
      if (Variable.Lang_YML.getString("MultiverseCoreCompability") == null) {
        Variable.Lang_YML.set("MultiverseCoreCompability", "§8[§6SelfHomeMain§8] §a挂载到:Multiverse-Core 兼容多世界插件已开启~");
        lang_check = true;
      } 
      if (Main.JavaPlugin.getConfig().getString("UnAutoSaveWorlds") == null) {
        List<String> list = new ArrayList<>();
        list.add("DIM34676");
        Main.JavaPlugin.getConfig().set("UnAutoSaveWorlds", list);
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("CheckUpdate")) {
        Main.JavaPlugin.getConfig().set("CheckUpdate", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("CheckUpdate")) {
        Main.JavaPlugin.getConfig().set("CheckUpdate", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("CheckHasNewPlugin")) {
        List<String> list = new ArrayList<>();
        list.add("§8[§6SelfHomeMain§8] §d当前运行的插件版本为:§a<Now>");
        list.add("§8[§6SelfHomeMain§8] §d找到一个更新的版本:§a<New>");
        list.add("§8[§6SelfHomeMain§8] §d下载地址: §e<Link>");
        Variable.Lang_YML.set("CheckHasNewPlugin", list);
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("NowIsTheLatestPlugin")) {
        Variable.Lang_YML.set("NowIsTheLatestPlugin", "§8[§6SelfHomeMain§8] §d当前运行的插件版本为最新版:§a<Now>");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("CheckUpdateFailed")) {
        Variable.Lang_YML.set("CheckUpdateFailed", "§8[§6SelfHomeMain§8] §c版本更新检查失败!");
        lang_check = true;
      } 
      if (!Main.JavaPlugin.getConfig().isConfigurationSection("DisablePortalCreate")) {
        Main.JavaPlugin.getConfig().set("DisablePortalCreate", Boolean.valueOf(true));
        config_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.0283D) {
      if (Main.JavaPlugin.getConfig().isConfigurationSection("FaweSwitch")) {
        Main.JavaPlugin.getConfig().set("FaweSwitch", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().isConfigurationSection("BorderMaterial")) {
        Main.JavaPlugin.getConfig().set("BorderMaterial", "BEDROCK");
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().isConfigurationSection("BorderShape")) {
        Main.JavaPlugin.getConfig().set("BorderShape", "Square");
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().isConfigurationSection("UpdateClearOld")) {
        Main.JavaPlugin.getConfig().set("UpdateClearOld", Boolean.valueOf(false));
        config_check = true;
      } 
      if (Main.JavaPlugin.getConfig().isConfigurationSection("KeepSpawnInMemory")) {
        Main.JavaPlugin.getConfig().set("KeepSpawnInMemory", Boolean.valueOf(true));
        config_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("FaweAndWorldEditCompability")) {
        Variable.Lang_YML.set("FaweAndWorldEditCompability", "§8[§6SelfHomeMain§8] §a挂载到:Multiverse-FastAsyncWorldEdit 异步形状边界功能已开启~");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("ToggleccWorldEnable")) {
        Variable.Lang_YML.set("ToggleccWorldEnable", "§8[§6SelfHomeMain§8] §a成功切换边界显示情况为: 显示");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("ToggleccWorldDisable")) {
        Variable.Lang_YML.set("ToggleccWorldDisable", "§8[§6SelfHomeMain§8] §a成功切换边界显示情况为: 隐藏");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.0285D) {
      if (!Variable.Lang_YML.isConfigurationSection("BiomeChangeTip")) {
        Variable.Lang_YML.set("BiomeChangeTip", "§8[§6SelfHomeMain§8] §a已为您更改当前的区块群系,请重新上线查看生效!");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (Main.JavaPlugin.getConfig().getDouble("Version") < 2.0296D) {
      if (!Variable.Lang_YML.isConfigurationSection("WeatherRain")) {
        Variable.Lang_YML.set("WeatherRain", "§8[§6SelfHomeMain§8] §a为您切换为雨天");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("WeatherSun")) {
        Variable.Lang_YML.set("WeatherSun", "§8[§6SelfHomeMain§8] §a为您切换为晴天");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("TimeDay")) {
        Variable.Lang_YML.set("TimeDay", "§8[§6SelfHomeMain§8] §a为您切换为白天");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("TimeNight")) {
        Variable.Lang_YML.set("TimeNight", "§8[§6SelfHomeMain§8] §a为您切换为黑天");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("LookSeed")) {
        Variable.Lang_YML.set("LookSeed", "§8[§6SelfHomeMain§8] §a当前世界的种子为:<Seed>");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("BiomeError")) {
        Variable.Lang_YML.set("BiomeError", "§8[§6SelfHomeMain§8] §a群系类型错误,请检查");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("EnableFly")) {
        Variable.Lang_YML.set("EnableFly", "§8[§6SelfHomeMain§8] §a成功为您开启飞行模式");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("DisableFly")) {
        Variable.Lang_YML.set("DisableFly", "§8[§6SelfHomeMain§8] §a成功为您关闭飞行模式");
        lang_check = true;
      } 
      if (!Variable.Lang_YML.isConfigurationSection("ToggleccWorldDisableFlying")) {
        Variable.Lang_YML.set("ToggleccWorldDisableFlying", "§8[§6SelfHomeMain§8] §a您离开了飞行的世界,为您自动关闭飞行功能.");
        lang_check = true;
      } 
      config_check = true;
    } 
    if (config_check) {
      Main.JavaPlugin.getConfig().set("Version", Double.valueOf(2.0296D));
      Main.JavaPlugin.saveConfig();
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("UpdateConfigMessage"));
    } 
    if (lang_check) {
      try {
        Variable.Lang_YML.save(new File(Main.JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "Language" + Variable.file_loc_prefix + Main.JavaPlugin.getConfig().getString("Language") + ".yml"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("UpdateLanguageMessage"));
    } 
    if (gui_check) {
      try {
        Variable.GUI_YML.save(new File(Main.JavaPlugin.getDataFolder() + Variable.file_loc_prefix + "GUI" + ".yml"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("UpdateGuiMessage"));
    } 
  }
}
