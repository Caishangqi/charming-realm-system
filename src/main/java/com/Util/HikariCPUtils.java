package com.Util;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import org.bukkit.Bukkit;

public class HikariCPUtils {
  public static HikariDataSource sqlConnectionPool;
  
  public static void setSqlConnectionPool() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setConnectionTimeout(Main.JavaPlugin.getConfig().getLong("HikariCP.connectionTimeout"));
    hikariConfig.setMinimumIdle(Main.JavaPlugin.getConfig().getInt("HikariCP.minimumIdle"));
    hikariConfig.setMaximumPoolSize(Main.JavaPlugin.getConfig().getInt("HikariCP.maximumPoolSize"));
    hikariConfig.setIdleTimeout(600000L);
    hikariConfig.setMaxLifetime(800000L);
    hikariConfig.setConnectionTestQuery("SELECT 1");
    String type = Main.JavaPlugin.getConfig().getString("Type");
    String host = Main.JavaPlugin.getConfig().getString("Host");
    String port = String.valueOf(Main.JavaPlugin.getConfig().getInt("Port"));
    String database = Main.JavaPlugin.getConfig().getString("Database");
    String username = Main.JavaPlugin.getConfig().getString("Username");
    String password = Main.JavaPlugin.getConfig().getString("Password");
    String url = "jdbc:" + type + "://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&autoReconnect=true";
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setUsername(username);
    hikariConfig.setPassword(password);
    hikariConfig.setAutoCommit(true);
    sqlConnectionPool = new HikariDataSource(hikariConfig);
    try {
      if (sqlConnectionPool.getConnection() != null) {
        Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DataBaseConnectionSuccess"));
        MySQL.init();
      } 
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("DataBaseConnectionError"));
    } 
  }
}
