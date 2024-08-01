package com.Util;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeAPI {
    public static List<Home> getHomes() {
        List<Home> list = new ArrayList<>();
        if (Variable.bungee) {
            for (String temp : MySQL.getAllWorlds()) {
                Home home = null;
                home = new Home(temp);
                list.add(home);
            }
        } else {
            File folder = new File(Variable.Tempf);
            File[] arrayOfFile;
            for (int i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) {
                File temp = arrayOfFile[b];
                String want_to = temp.getPath().replace(Variable.Tempf, "").replace(".yml", "").replace(Variable.file_loc_prefix, "");
                Home home = new Home(want_to);
                list.add(home);
                b++;
            }
        }
        return list;
    }

    public static Home getHome(String name) {
        name = name.replace(Variable.world_prefix, "");
        if (!Util.CheckIsHome(name)) {
            boolean has_been_join = false;
            if (Variable.bungee) {
                if (MySQL.alreadyhastheplayerjoin(name)) {
                    has_been_join = true;
                    name = MySQL.getJoinHome(name);
                }
            } else {
                File folder = new File(Variable.Tempf);
                File[] arrayOfFile;
                if (folder.listFiles() == null)
                    return null;
                for (int j = (arrayOfFile = folder.listFiles()).length, b = 0; b < j; ) {
                    File temp = arrayOfFile[b];
                    String want_to = temp.getPath().replace(Variable.Tempf, "").replace(".yml", "")
                            .replace(Variable.file_loc_prefix, "");
                    YamlConfiguration yamlConfiguration1 = YamlConfiguration.loadConfiguration(temp);
                    for (int i = 0; i < yamlConfiguration1.getStringList("OP").size(); i++) {
                        String temp_str = yamlConfiguration1.getStringList("OP").get(i);
                        if (temp_str.equalsIgnoreCase(name)) {
                            has_been_join = true;
                            name = want_to;
                            break;
                        }
                    }
                    b++;
                }
            }
            if (!has_been_join)
                return null;
        }
        Home home = new Home(name);
        return home;
    }

    public static void delHome(final String name) {
        if (Variable.bungee) {
            MySQL.removePlayer(name);
        } else {
            File f2 = new File(Variable.Tempf, String.valueOf(String.valueOf(name)) + ".yml");
            f2.delete();
        }
        World world = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + name);
        if (world != null)
            for (Player p6 : Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + name).getPlayers()) {
                p6.teleport(Bukkit.getWorld("world").getSpawnLocation());
                p6.sendMessage(Variable.Lang_YML.getString("WorldHasBeenDeleted"));
            }
        Bukkit.unloadWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + name, true);
        (new BukkitRunnable() {
            public void run() {
                File f;
                if (Variable.world_prefix.equalsIgnoreCase("")) {
                    if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT")) {
                        f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + name);
                    } else {
                        f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + "world" + Variable.file_loc_prefix + name);
                    }
                } else {
                    f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix + name);
                }
                Util.deleteFile(f);
            }
        }).runTaskLater((Plugin) Main.JavaPlugin, 5L);
    }

    public boolean hasPermission(Player p, String name) {
        name = name.replace(Variable.world_prefix, "");
        if (Util.Check(p, name).booleanValue())
            return true;
        return false;
    }
}
