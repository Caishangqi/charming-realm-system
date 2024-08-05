package com.caizii.charmrealm;

import com.caizii.charmrealm.utils.MySQL;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class initHome {
  public static void init() {
    if (CharmRealm.pluginVariable.bungee) {
      MySQL.addFlowersColumn();
      MySQL.addPopularityColumn();
      MySQL.addGiftColumn();
      MySQL.addAdvertisementColumn();
      MySQL.addIconColumn();
      MySQL.addVisitColumn();
      MySQL.addLimitBlockColumn();
    } else {
      File folder = new File(CharmRealm.pluginVariable.Tempf);
      File[] arrayOfFile;
      for (int i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) {
        File temp = arrayOfFile[b];
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(temp);
        boolean edit = false;
        if (!yamlConfiguration.isSet("flowers")) {
          yamlConfiguration.createSection("flowers");
          yamlConfiguration.set("flowers", Integer.valueOf(0));
          edit = true;
        } 
        if (!yamlConfiguration.isSet("popularity")) {
          yamlConfiguration.createSection("popularity");
          yamlConfiguration.set("popularity", Integer.valueOf(0));
          edit = true;
        } 
        if (!yamlConfiguration.isSet("gifts")) {
          yamlConfiguration.createSection("gifts");
          yamlConfiguration.set("gifts", new ArrayList());
          edit = true;
        } 
        if (!yamlConfiguration.isSet("advertisement")) {
          yamlConfiguration.createSection("advertisement");
          yamlConfiguration.set("advertisement", new ArrayList());
          edit = true;
        } 
        if (!yamlConfiguration.isSet("icon")) {
          yamlConfiguration.createSection("icon");
          yamlConfiguration.set("icon", "");
          edit = true;
        } 
        if (edit)
          try {
            yamlConfiguration.save(temp);
          } catch (IOException e) {
            e.printStackTrace();
          }  
        b++;
      } 
    } 
  }
}
