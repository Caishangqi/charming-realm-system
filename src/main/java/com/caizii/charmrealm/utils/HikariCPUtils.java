package com.caizii.charmrealm.utils;

import com.caizii.charmrealm.CharmRealm;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class HikariCPUtils {
  public static HikariDataSource sqlConnectionPool;
  
  public static void setSqlConnectionPool() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setConnectionTimeout(CharmRealm.JavaPlugin.getConfig().getLong("HikariCP.connectionTimeout"));
    hikariConfig.setMinimumIdle(CharmRealm.JavaPlugin.getConfig().getInt("HikariCP.minimumIdle"));
    hikariConfig.setMaximumPoolSize(CharmRealm.JavaPlugin.getConfig().getInt("HikariCP.maximumPoolSize"));
    hikariConfig.setIdleTimeout(600000L);
    hikariConfig.setMaxLifetime(800000L);
    hikariConfig.setConnectionTestQuery("SELECT 1");
    String type = CharmRealm.JavaPlugin.getConfig().getString("Type");
    String host = CharmRealm.JavaPlugin.getConfig().getString("Host");
    String port = String.valueOf(CharmRealm.JavaPlugin.getConfig().getInt("Port"));
    String database = CharmRealm.JavaPlugin.getConfig().getString("Database");
    String username = CharmRealm.JavaPlugin.getConfig().getString("Username");
    String password = CharmRealm.JavaPlugin.getConfig().getString("Password");
    String url = "jdbc:" + type + "://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&autoReconnect=true";
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setUsername(username);
    hikariConfig.setPassword(password);
    hikariConfig.setAutoCommit(true);
    sqlConnectionPool = new HikariDataSource(hikariConfig);
    try {
      if (sqlConnectionPool.getConnection() != null) {
        Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("DataBaseConnectionSuccess"));
        MySQL.init();
      } 
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage(CharmRealm.pluginVariable.Lang_YML.getString("DataBaseConnectionError"));
    } 
  }
}
