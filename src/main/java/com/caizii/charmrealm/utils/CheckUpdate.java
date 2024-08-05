package com.caizii.charmrealm.utils;

import com.caizii.charmrealm.CharmRealm;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckUpdate {
  public static final String NOW_VERSION = "V2.0.2.9";
  
  public static String new_Version;
  
  public static void checkUpdate() {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin) CharmRealm.JavaPlugin, () -> {
          try {
            HttpURLConnection huc = (HttpURLConnection)(new URL("https://gitee.com/api/v5/repos/a1242839141/CharmRealm/tags")).openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            ByteArrayOutputStream ba = new ByteArrayOutputStream(16384);
            byte[] data = new byte[4096];
            int nRead;
            while ((nRead = huc.getInputStream().read(data, 0, data.length)) != -1)
              ba.write(data, 0, nRead); 
            JsonArray array = (JsonArray)(new Gson()).fromJson(ba.toString("UTF-8"), JsonArray.class);
            JsonObject latestVer = array.get(0).getAsJsonObject();
            String name = latestVer.get("name").getAsString();
            if (!name.toUpperCase().contains("V2.0.2.9".toUpperCase())) {
              for (int i = 0; i < CharmRealm.pluginVariable.Lang_YML.getStringList("CheckHasNewPlugin").size(); i++) {
                new_Version = name;
                String temp = CharmRealm.pluginVariable.Lang_YML.getStringList("CheckHasNewPlugin").get(i);
                if (temp.contains("<Now>"))
                  temp = temp.replace("<Now>", "V2.0.2.9"); 
                if (temp.contains("<New>"))
                  temp = temp.replace("<New>", new_Version); 
                if (temp.contains("<Link>"))
                  temp = temp.replace("<Link>", "https://gitee.com/a1242839141/CharmRealm/releases"); 
                Bukkit.getConsoleSender().sendMessage(temp);
              } 
            } else {
              new_Version = null;
              String temp = CharmRealm.pluginVariable.Lang_YML.getString("NowIsTheLatestPlugin");
              if (temp.contains("<Now>"))
                temp = temp.replace("<Now>", "V2.0.2.9"); 
              Bukkit.getConsoleSender().sendMessage(temp);
            } 
          } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("CheckUpdateFailed"));
            new_Version = null;
          } 
        });
  }
}
