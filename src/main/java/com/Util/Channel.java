package com.Util;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Channel {
  public static void sendPlayerToServer(Player p, String ServerName) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Connect");
    out.writeUTF(ServerName);
    p.sendPluginMessage((Plugin)Main.JavaPlugin, "BungeeCord", out.toByteArray());
  }
  
  public static void waitDelayToSomeWhere(Player p, String Server, String command) throws IOException {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Forward");
    out.writeUTF(Server);
    out.writeUTF("SelfHomeMain");
    ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
    DataOutputStream msgout = new DataOutputStream(msgbytes);
    msgout.writeUTF("waitDelayToHome," + p.getName() + "," + command);
    msgout.writeShort(123);
    out.writeShort((msgbytes.toByteArray()).length);
    out.write(msgbytes.toByteArray());
    p.sendPluginMessage((Plugin)Main.JavaPlugin, "BungeeCord", out.toByteArray());
  }
  
  public static void waitToCommand(Player p, String Server, String command) throws IOException {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Forward");
    out.writeUTF(Server);
    out.writeUTF("SelfHomeMain");
    ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
    DataOutputStream msgout = new DataOutputStream(msgbytes);
    msgout.writeUTF("waitToCommand," + p.getName() + "," + command);
    msgout.writeShort(123);
    out.writeShort((msgbytes.toByteArray()).length);
    out.write(msgbytes.toByteArray());
    p.sendPluginMessage((Plugin)Main.JavaPlugin, "BungeeCord", out.toByteArray());
  }
  
  public static void waitToLoad(final Player p, final String Server, final String home_name) throws IOException {
    if (Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + home_name) != null) {
      Chunk[] arrayOfChunk;
      for (int i = (arrayOfChunk = Bukkit.getWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + home_name).getLoadedChunks()).length, b = 0; b < i; ) {
        Chunk chunk = arrayOfChunk[b];
        chunk.unload(true);
        b++;
      } 
      Bukkit.unloadWorld(String.valueOf(String.valueOf(Variable.world_prefix)) + home_name, true);
    } 
    (new BukkitRunnable() {
        public void run() {
          File f = null;
          if (Variable.world_prefix.equalsIgnoreCase("")) {
            if (Bukkit.getVersion().toString().toUpperCase().contains("ARCLIGHT") || Bukkit.getVersion().toString().contains("1.20.1")) {
              f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix);
            } else {
              f = new File(
                  String.valueOf(String.valueOf(Variable.single_server_gen)) + "world" + Variable.file_loc_prefix);
            } 
          } else {
            f = new File(String.valueOf(String.valueOf(Variable.single_server_gen)) + Variable.world_prefix);
          } 
          ByteArrayDataOutput out = ByteStreams.newDataOutput();
          out.writeUTF("Forward");
          out.writeUTF(Server);
          out.writeUTF("SelfHomeMain");
          ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
          DataOutputStream msgout = new DataOutputStream(msgbytes);
          try {
            msgout.writeUTF("waitToLoad," + p.getName() + "," + home_name + "," + f.getAbsolutePath());
            msgout.writeShort(123);
          } catch (IOException e) {
            e.printStackTrace();
          } 
          out.writeShort((msgbytes.toByteArray()).length);
          out.write(msgbytes.toByteArray());
          p.sendPluginMessage((Plugin)Main.JavaPlugin, "BungeeCord", out.toByteArray());
        }
      }).runTaskLater((Plugin)Main.JavaPlugin, 10L);
  }
}
