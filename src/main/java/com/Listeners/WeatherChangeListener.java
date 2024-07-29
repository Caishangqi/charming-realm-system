package com.Listeners;

import com.SelfHome.Variable;
import com.Util.MySQL;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
  @EventHandler
  public void onWeatherChange(WeatherChangeEvent e) {
    if (Variable.bungee) {
      for (World world : Bukkit.getWorlds()) {
        String name = world.getName().replace(Variable.world_prefix, "");
        if (MySQL.getlockweather(name).equalsIgnoreCase("true")) {
          e.setCancelled(true);
          e.getWorld().setWeatherDuration(0);
        } 
      } 
    } else {
      File folder = new File(Variable.Tempf);
      File[] arrayOfFile;
      for (int i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) {
        File temp = arrayOfFile[b];
        String want_to = String.valueOf(String.valueOf(Variable.world_prefix)) + temp.getPath().replace(Variable.Tempf, "").replace(".yml", "")
          .replace(Variable.file_loc_prefix, "");
        if (Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + want_to) != null) {
          World world = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + want_to);
          YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(temp);
          if (yamlConfiguration.getBoolean("lockweather")) {
            e.setCancelled(true);
            e.getWorld().setWeatherDuration(0);
          } 
        } 
        b++;
      } 
    } 
  }
}
