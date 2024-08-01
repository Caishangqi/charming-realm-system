package com.SelfHome;

import com.Util.StaticsTick;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Variable {
  public static Economy econ;
  
  public static List<Player> cooldown = new ArrayList<>();
  
  public static List<String> waitDeleteconfirm = new ArrayList<>();
  
  public static List<String> Deletecooldown = new ArrayList<>();
  
  public static List<String> KeepWorlds = new ArrayList<>();
  
  public static Boolean First = Boolean.valueOf(true);
  
  public static String Final = "";
  
  public static String worldFinal = "";
  
  public static String backupFinal = "";
  
  public static String Log_All = "";
  
  public static String Tempf;
  
  public static String Temp;
  
  public static String Tempf2;
  
  public static String DelDir;
  
  public static String server_file_world;
  
  public static String single_server_gen;
  
  public static String world_prefix = "";
  
  public static boolean Cat_Check = false;
  
  public static String Prefix = "";
  
  public static boolean check_first_start = true;
  
  public static File f_log;
  
  public static File f_PlaceHolders;
  
  public static HashMap<String, String> invite_list = new HashMap<>();
  
  public static String Papi_world;
  
  public static String CheckIsHome = null;
  
  public static FileConfiguration getName_yml;

  public static FileConfiguration GUI_SETTING_YML;
  
  public static String custom_playerdata_location = "";
  
  public static String custom_autobackup_location = "";
  
  public static boolean bungee = true;
  
  public static String[] ab;
  
  public static ArrayList<String> Debug = new ArrayList<>();
  
  public static List<Redis> cache = new ArrayList<>();
  
  public static FileConfiguration GUI_YML;
  
  public static List<String> list_home = new ArrayList<>();
  
  public static List<StaticsTick> world_StaticsTick = new ArrayList<>();
  
  public static boolean linux_os = false;
  
  public static String file_loc_prefix = "\\";
  
  public static String world_subfix_end = "\\";
  
  public static PlayerPoints playerPoints;
  
  public static HashMap<String, List<Hologram>> hololist = new HashMap<>();
  
  public static FileConfiguration Lang_YML;
  
  public static boolean Hologram_switch = true;
  
  public static List<String> AddDebuff = new ArrayList<>();
  
  public static List<String> DispathCommand = new ArrayList<>();
  
  public static boolean PlyaerPointsModule = false;
  
  public static HashMap<String, String> wait_to_spawn_home = new HashMap<>();
  
  public static String NMS_Version = null;
  
  public static HashMap<String, String> wait_to_command = new HashMap<>();
  
  public static String Soil = "SOIL";
  
  public static HashMap<String, List<String>> popularity_list = new HashMap<>();
  
  public static HashMap<String, Integer> flowers_list = new HashMap<>();
  
  public static HashMap<String, String> has_open_gifts_list = new HashMap<>();
  
  public static HashMap<String, Double> toplist_popularity_flowers = new HashMap<>();
  
  public static List<String> has_already_move_world = new ArrayList<>();
  
  public static List<String> wait_to_confirm_command = new ArrayList<>();
  
  public static boolean hook_multiverseCore = false;
  
  public static boolean hook_FastAsyncWorldEdit = false;
  
  public static List<String> create_list_home = new ArrayList<>();
  
  public static List<String> has_already_hide_border = new ArrayList<>();
  
  public static boolean not_adopt_nms = false;
  
  public static boolean has_no_click_message = false;
  
  public static HashMap<String, String> flying_list = new HashMap<>();
  
  public static String prefix_p = "";
}
