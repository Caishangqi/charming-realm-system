package com.caizii.charmrealm;

import com.caizii.charmrealm.utils.StaticsTick;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Variable {
  public  Economy econ;
  
  public  List<Player> cooldown = new ArrayList<>();
  
  public  List<String> waitDeleteconfirm = new ArrayList<>();
  
  public  List<String> Deletecooldown = new ArrayList<>();
  
  public  List<String> KeepWorlds = new ArrayList<>();
  
  public  Boolean First = Boolean.valueOf(true);
  
  public  String Final = "";
  
  public  String worldFinal = "";
  
  public  String backupFinal = "";
  
  public  String Log_All = "";
  
  public  String Tempf;
  
  public  String Temp;
  
  public  String Tempf2;
  
  public  String DelDir;
  
  public  String server_file_world;
  
  public  String single_server_gen;
  
  public  String world_prefix = "";
  
  public  boolean Cat_Check = false;
  
  public  String Prefix = "";
  
  public  boolean check_first_start = true;
  
  public  File f_log;
  
  public  File f_PlaceHolders;
  
  public  HashMap<String, String> invite_list = new HashMap<>();
  
  public  String Papi_world;
  
  public  String CheckIsHome = null;
  
  public  FileConfiguration getName_yml;

  public  FileConfiguration GUI_SETTING_YML;
  public  FileConfiguration GUI_CREATE_YML;
  public FileConfiguration GUI_BROWSER_YML;

  public  String custom_playerdata_location = "";
  
  public  String custom_autobackup_location = "";
  
  public  boolean bungee = true;
  
  public  String[] ab;
  
  public  ArrayList<String> Debug = new ArrayList<>();
  
  public  List<Redis> cache = new ArrayList<>();
  
  public  FileConfiguration GUI_YML;
  
  public  List<String> list_home = new ArrayList<>();
  
  public  List<StaticsTick> world_StaticsTick = new ArrayList<>();
  
  public  boolean linux_os = false;
  
  public  String file_loc_prefix = "\\";
  
  public  String world_subfix_end = "\\";
  
  public  PlayerPoints playerPoints;
  
  public  HashMap<String, List<Hologram>> hololist = new HashMap<>();
  
  public  FileConfiguration Lang_YML;
  
  public  boolean Hologram_switch = true;
  
  public  List<String> AddDebuff = new ArrayList<>();
  
  public  List<String> DispathCommand = new ArrayList<>();
  
  public  boolean PlyaerPointsModule = false;
  
  public  HashMap<String, String> wait_to_spawn_home = new HashMap<>();
  
  public  String NMS_Version = null;
  
  public  HashMap<String, String> wait_to_command = new HashMap<>();
  
  public  String Soil = "FARMLAND";
  
  public  HashMap<String, List<String>> popularity_list = new HashMap<>();
  
  public  HashMap<String, Integer> flowers_list = new HashMap<>();
  
  public  HashMap<String, String> has_open_gifts_list = new HashMap<>();
  
  public  HashMap<String, Double> toplist_popularity_flowers = new HashMap<>();
  
  public  List<String> has_already_move_world = new ArrayList<>();
  
  public  List<String> wait_to_confirm_command = new ArrayList<>();
  
  public  boolean hook_multiverseCore = false;
  
  public  boolean hook_FastAsyncWorldEdit = false;
  
  public  List<String> create_list_home = new ArrayList<>();
  
  public  List<String> has_already_hide_border = new ArrayList<>();
  
  public  boolean not_adopt_nms = false;
  
  public  boolean has_no_click_message = false;
  
  public  HashMap<String, String> flying_list = new HashMap<>();
  
  public  String prefix_p = "";
}
