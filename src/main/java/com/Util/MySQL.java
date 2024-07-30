package com.Util;

import com.SelfHome.Main;
import com.SelfHome.Variable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQL {
    public static String type = Main.JavaPlugin.getConfig().getString("Type");

    public static String host = Main.JavaPlugin.getConfig().getString("Host");

    public static String port = String.valueOf(Main.JavaPlugin.getConfig().getInt("Port"));

    public static String database = Main.JavaPlugin.getConfig().getString("Database");

    public static String username = Main.JavaPlugin.getConfig().getString("Username");

    public static String password = Main.JavaPlugin.getConfig().getString("Password");

    private static final String CREATE_Users_TABLE = "CREATE TABLE IF NOT EXISTS SelfHomeMain_Users (Name VARCHAR(255),Members VARCHAR(255),OP VARCHAR(255),Denys VARCHAR(255),Public VARCHAR(255),Level VARCHAR(255),pvp VARCHAR(255),pickup VARCHAR(255),dropitem VARCHAR(255),Server VARCHAR(255),locktime VARCHAR(255),lockweather VARCHAR(255),time VARCHAR(255),X VARCHAR(255),Y VARCHAR(255),Z VARCHAR(255),flowers VARCHAR(100),popularity VARCHAR(100),gifts TEXT default null,advertisement VARCHAR(255),icon VARCHAR(255),visittime VARCHAR(255),limitblock varchar(255))";

    private static final String CREATE_Servers_TABLE = "CREATE TABLE IF NOT EXISTS SelfHomeMain_Servers (Server VarChar(100),Amount double)";

    private static final String Find_the_Lowest_Server = "SELECT * From SelfHomeMain_Servers Order by Amount ASC";

    private static final String Find_the_Highest_Server = "SELECT * From SelfHomeMain_Servers Order by Amount DESC";

    private static final String Update_Server_Statistic = "UPDATE SelfHomeMain_Servers Set Amount = ? Where Server = ?";

    private static final String Insert_Server = "INSERT INTO SelfHomeMain_Servers VALUES(?,?)";

    private static final String Has_Already_Contain_The_Server = "Select * From SelfHomeMain_Servers Where Server = ?";

    private static final String Get_Amount = "SELECT Amount FROM SelfHomeMain_Servers WHERE Server = ?";

    private static final String Already_has_the_player_home = "SELECT * FROM SelfHomeMain_Users Where Name = ?";

    private static final String Search_Home = "SELECT * FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Flowers = "SELECT flowers FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Popularity = "SELECT popularity FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Gifts = "SELECT gifts FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_LimitBlock = "SELECT limitblock FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_advertisement = "SELECT advertisement FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_icon = "SELECT icon FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_visittime = "SELECT visittime FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_X = "SELECT X FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Y = "SELECT Y FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Z = "SELECT Z FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Members = "SELECT Members FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_OP = "SELECT OP FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Denys = "SELECT Denys FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Level = "SELECT Level FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_Public = "SELECT Public FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_pvp = "SELECT pvp FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_pickup = "SELECT pickup FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_dropitem = "SELECT dropitem FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_locktime = "SELECT locktime FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_lockweather = "SELECT lockweather FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Get_time = "SELECT time FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String Update_Flowers = "UPDATE SelfHomeMain_Users Set flowers = ? Where Name = ?";

    private static final String Update_Popularity = "UPDATE SelfHomeMain_Users Set popularity = ? Where Name = ?";

    private static final String Update_Gift = "UPDATE SelfHomeMain_Users Set gifts = ? Where Name = ?";

    private static final String Update_icon = "UPDATE SelfHomeMain_Users Set icon = ? Where Name = ?";

    private static final String Update_LimitBlock = "UPDATE SelfHomeMain_Users Set limitblock = ? Where Name = ?";

    private static final String Update_advertisement = "UPDATE SelfHomeMain_Users Set advertisement = ? Where Name = ?";

    private static final String Update_visittime = "UPDATE SelfHomeMain_Users Set visittime = ? Where Name = ?";

    private static final String Update_X = "UPDATE SelfHomeMain_Users Set X = ? Where Name = ?";

    private static final String Update_Y = "UPDATE SelfHomeMain_Users Set Y = ? Where Name = ?";

    private static final String Update_Z = "UPDATE SelfHomeMain_Users Set Z = ? Where Name = ?";

    private static final String Update_Members = "UPDATE SelfHomeMain_Users Set Members = ? Where Name = ?";

    private static final String Update_OP = "UPDATE SelfHomeMain_Users Set OP = ? Where Name = ?";

    private static final String Update_Denys = "UPDATE SelfHomeMain_Users Set Denys = ? Where Name = ?";

    private static final String Update_Level = "UPDATE SelfHomeMain_Users Set Level = ? Where Name = ?";

    private static final String Update_Public = "UPDATE SelfHomeMain_Users Set Public = ? Where Name = ?";

    private static final String Update_pvp = "UPDATE SelfHomeMain_Users Set pvp = ? Where Name = ?";

    private static final String Update_pickup = "UPDATE SelfHomeMain_Users Set pickup = ? Where Name = ?";

    private static final String Update_dropitem = "UPDATE SelfHomeMain_Users Set dropitem = ? Where Name = ?";

    private static final String Update_locktime = "UPDATE SelfHomeMain_Users Set locktime = ? Where Name = ?";

    private static final String Update_lockweather = "UPDATE SelfHomeMain_Users Set lockweather = ? Where Name = ?";

    private static final String Update_time = "UPDATE SelfHomeMain_Users Set time = ? Where Name = ?";

    private static final String Update_Server = "UPDATE SelfHomeMain_Users Set Server = ? Where Name = ?";

    private static final String Insert_Value = "INSERT INTO SelfHomeMain_Users VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String Remove_Player = "DELETE From SelfHomeMain_Users Where Name = ?";

    private static final String Get_ALL = "SELECT * FROM SelfHomeMain_Users";

    private static final String Import_Check = "SELECT * FROM SelfHomeMain_Users WHERE Name = ?";

    private static final String DESC_LEVEL = "SELECT * FROM SelfHomeMain_Users Order by Level DESC";

    private static final String DESC_FLOWER = "SELECT * FROM SelfHomeMain_Users Order by flowers DESC";

    private static final String DESC_POPULARITY = "SELECT * FROM SelfHomeMain_Users Order by popularity DESC";

    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static void autoUpdateServer() {
        (new BukkitRunnable() {
            public void run() {
                Connection con = MySQL.getConnection();
                PreparedStatement ps = null;
                ResultSet res = null;
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(Variable.f_log);
                if (!yamlConfiguration.contains("NowID"))
                    yamlConfiguration.set("NowID", Integer.valueOf(0));
                if (!yamlConfiguration.contains("MaxID"))
                    yamlConfiguration.set("MaxID", Integer.valueOf(255));
                try {
                    yamlConfiguration.save(Variable.f_log);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                int nowID = yamlConfiguration.getInt("NowID");
                int MaxID = yamlConfiguration.getInt("MaxID");
                if (nowID >= MaxID)
                    return;
                try {
                    ps = con.prepareStatement("Select * From SelfHomeMain_Servers Where Server = ?", 1004, 1007);
                    ps.setString(1, Main.JavaPlugin.getConfig().getString("Server"));
                    res = ps.executeQuery();
                    res.last();
                    int amount = res.getRow();
                    if (amount == 0) {
                        ps = con.prepareStatement("INSERT INTO SelfHomeMain_Servers VALUES(?,?)");
                        ps.setString(1, Main.JavaPlugin.getConfig().getString("Server"));
                        if (Main.JavaPlugin.getConfig().getString("DecideBy").equalsIgnoreCase("Player")) {
                            ps.setDouble(2, Bukkit.getOnlinePlayers().size());
                        } else {
                            double se1 = Double.valueOf(PlaceholderAPI.setPlaceholders(null, "%server_tps_1%").replace("*", "")).doubleValue();
                            double se2 = Double.valueOf(PlaceholderAPI.setPlaceholders(null, "%server_tps_5%").replace("*", "")).doubleValue();
                            double se3 = Double.valueOf(PlaceholderAPI.setPlaceholders(null, "%server_tps_15%").replace("*", "")).doubleValue();
                            ps.setDouble(2, (se1 + se2 + se3) / 3.0D);
                        }
                        ps.executeUpdate();
                    } else {
                        ps = con.prepareStatement("UPDATE SelfHomeMain_Servers Set Amount = ? Where Server = ?");
                        if (Main.JavaPlugin.getConfig().getString("DecideBy").equalsIgnoreCase("Player")) {
                            ps.setDouble(1, Bukkit.getOnlinePlayers().size());
                        } else {
                            double se1 = Double.valueOf(PlaceholderAPI.setPlaceholders(null, "%server_tps_1%").replace("*", "")).doubleValue();
                            double se2 = Double.valueOf(PlaceholderAPI.setPlaceholders(null, "%server_tps_5%").replace("*", "")).doubleValue();
                            double se3 = Double.valueOf(PlaceholderAPI.setPlaceholders(null, "%server_tps_15%").replace("*", "")).doubleValue();
                            ps.setDouble(1, (se1 + se2 + se3) / 3.0D);
                        }
                        ps.setString(2, Main.JavaPlugin.getConfig().getString("Server"));
                        ps.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (res != null)
                            res.close();
                        if (ps != null)
                            ps.close();
                        if (con != null)
                            con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).runTaskTimerAsynchronously((Plugin) Main.JavaPlugin, 0L, 60L);
    }

    public static String getLowerstLagServer() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = null;
        try {
            ps = con.prepareStatement("SELECT * From SelfHomeMain_Servers Order by Amount ASC");
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("Server");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getHighestTPSServer() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = null;
        try {
            ps = con.prepareStatement("SELECT * From SelfHomeMain_Servers Order by Amount DESC");
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("Server");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double getServerAmount(String server) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        double amount = 0.0D;
        try {
            ps = con.prepareStatement("SELECT Amount FROM SelfHomeMain_Servers WHERE Server = ?");
            ps.setString(1, server);
            res = ps.executeQuery();
            if (res.next())
                amount = res.getDouble("Amount");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return amount;
    }

    public static boolean alreadyhastheplayerhome(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        int ss = 0;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users Where Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                ss++;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ss != 0)
            return true;
        return false;
    }

    public static boolean CheckIsAHome(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        boolean check = false;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name.replace(Variable.world_prefix, ""));
            res = ps.executeQuery();
            if (res.next())
                check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    public static boolean alreadyhastheplayerjoin(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        boolean check = false;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE OP LIKE '%" + name + "%'");
            res = ps.executeQuery();
            if (res.next())
                check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    public static String getJoinHome(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String check = null;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE OP LIKE '%" + name + "%'");
            res = ps.executeQuery();
            if (res.next())
                check = res.getString("Name");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    public static String getJoinServer(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String Server = null;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE OP LIKE '%" + name + "%'");
            res = ps.executeQuery();
            if (res.next())
                Server = res.getString("Server");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Server;
    }

    public static String getServer(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String Server = null;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE Name='" + name + "'");
            res = ps.executeQuery();
            if (res.next())
                Server = res.getString("Server");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Server;
    }

    public static void addFlowersColumn() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("alter table SelfHomeMain_Users add column flowers VARCHAR(100) default '0'");
            ps.executeUpdate();
        } catch (SQLException sQLException) {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addAdvertisementColumn() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("alter table SelfHomeMain_Users add column advertisement VARCHAR(255)");
            ps.executeUpdate();
        } catch (SQLException sQLException) {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addLimitBlockColumn() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("alter table SelfHomeMain_Users add column limitblock VARCHAR(255)");
            ps.executeUpdate();
        } catch (SQLException sQLException) {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addIconColumn() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("alter table SelfHomeMain_Users add column icon VARCHAR(255)");
            ps.executeUpdate();
        } catch (SQLException sQLException) {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addVisitColumn() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("alter table SelfHomeMain_Users add column visittime VARCHAR(255)");
            ps.executeUpdate();
        } catch (SQLException sQLException) {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addPopularityColumn() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("alter table SelfHomeMain_Users add column popularity VARCHAR(100) default '0'");
            ps.executeUpdate();
        } catch (SQLException sQLException) {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addGiftColumn() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("alter table SelfHomeMain_Users add column gifts TEXT default NULL");
            ps.executeUpdate();
        } catch (SQLException sQLException) {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setFlowers(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set flowers = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPopularity(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set popularity = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setGifts(String name, String value) throws IOException {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        Reader reader = new StringReader(value);
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set gifts = ? Where Name = ?");
            ps.setCharacterStream(1, reader);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setIcon(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set icon = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setVisitTime(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set visittime = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setAdvertisement(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set advertisement = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setLimitBlock(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set limitblock = ? Where Name = ?");
            if ((((value != null) ? 1 : 0) & ((value.length() != 0) ? 1 : 0)) != 0 &&
                    value.substring(0, 1).equals(","))
                value = value.substring(1, value.length());
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setX(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set X = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setY(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set Y = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setZ(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set Z = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void settime(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set time = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setlockweather(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set lockweather = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setlocktime(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set locktime = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setdropitem(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set dropitem = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setpickup(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set pickup = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setpvp(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set pvp = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setLevel(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set Level = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setServer(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set Server = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPublic(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set Public = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setDenys(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set Denys = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setOP(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set OP = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setMembers(String name, String value) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("UPDATE SelfHomeMain_Users Set Members = ? Where Name = ?");
            ps.setString(1, value);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFlowers(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT flowers FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("flowers");
            if (result.contains("."))
                result = result.split("\\.")[0];
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getPopularity(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT popularity FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("Popularity");
            if (result.contains("."))
                result = result.split("\\.")[0];
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<String> getGift(String name) throws IOException {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT gifts FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next()) {
                String str = "";
                Reader in = res.getCharacterStream("gifts");
                if (in != null) {
                    BufferedReader r = new BufferedReader(in);
                    StringBuffer sb = new StringBuffer();
                    try {
                        String line;
                        while ((line = r.readLine()) != null)
                            sb.append(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    str = sb.toString();
                    r.close();
                    in.close();
                }
                if (str.contains(",")) {
                    list = Arrays.asList(str.split(","));
                } else {
                    list.add(str);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String getIcon(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT icon FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next()) {
                String value = res.getString("icon");
                if (value != null) {
                    result = value;
                } else {
                    result = "";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getVisitTime(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT visittime FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next()) {
                String value = res.getString("visittime");
                if (value != null) {
                    result = value;
                } else {
                    result = "";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<String> getAdvertisement(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT advertisement FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                if (res.getString("advertisement") == null) {
                    list.add("");
                } else if (res.getString("advertisement").contains(",")) {
                    list = Arrays.asList(res.getString("advertisement").split(","));
                } else {
                    list.add(res.getString("advertisement"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<String> getLimitBlock(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT limitblock FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next()) {
                String value = res.getString("limitblock");
                if (value == null)
                    return list;
                if ((((value != null) ? 1 : 0) & ((value.length() != 0) ? 1 : 0)) != 0 &&
                        value.substring(0, 1).equals(","))
                    value = value.substring(1, value.length());
                if (value == null) {
                    list.add("");
                } else if (value.contains(",")) {
                    list = Arrays.asList(res.getString("limitblock").split(","));
                } else {
                    list.add(res.getString("limitblock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String getX(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT X FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("X");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getY(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT Y FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("Y");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getZ(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT Z FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("Z");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<String> getMembers(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT Members FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                if (res.getString("Members").contains(",")) {
                    list = Arrays.asList(res.getString("Members").split(","));
                } else {
                    list.add(res.getString("Members"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<String> getOP(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT OP FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                if (res.getString("OP").contains(",")) {
                    list = Arrays.asList(res.getString("OP").split(","));
                } else {
                    list.add(res.getString("OP"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<String> getDenys(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT Denys FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                if (res.getString("Denys").contains(",")) {
                    list = Arrays.asList(res.getString("Denys").split(","));
                } else {
                    list.add(res.getString("Denys"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String getPublic(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT Public FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("Public");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getLevel(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT Level FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("Level");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getPVP(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT pvp FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("pvp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getpickup(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT pickup FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("pickup");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getdropitem(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT dropitem FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("dropitem");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getlocktime(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT locktime FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res == null)
                return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (res != null)
                res.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (res != null)
                res.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getlockweather(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT lockweather FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("lockweather");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String gettime(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT time FROM SelfHomeMain_Users WHERE Name = ?");
            ps.setString(1, name);
            res = ps.executeQuery();
            if (res.next())
                result = res.getString("time");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<String> getAllWorlds() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users");
            res = ps.executeQuery();
            while (res.next())
                list.add(res.getString("Name"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static int getAllWorldsAmount() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        int amount = 0;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users", 1004, 1007);
            res = ps.executeQuery();
            res.last();
            amount = res.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return amount;
    }

    public static String getListStringSpiltByDot(List<String> list) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        if (list.size() > 0)
            result = list.get(0);
        for (int c = 1; c < list.size(); c++) {
            String temp = list.get(c);
            result = String.valueOf(String.valueOf(result)) + "," + temp;
        }
        try {
            if (res != null)
                res.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean PlayerQuitHome(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        boolean success = false;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE OP LIKE '%" + name + "%'");
            res = ps.executeQuery();
            if (res.next()) {
                String OP = res.getString("OP");
                if (OP.contains("," + name)) {
                    OP = OP.replace("," + name, "");
                    setOP(res.getString("Name"), OP);
                    success = true;
                } else if (OP.contains(name)) {
                    OP = OP.replace(name, "");
                    setOP(res.getString("Name"), OP);
                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static void insertvalue(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, String s10, String s11, String s12, String s13, String s14, String s15, String s16, String s17, String s18, String s19, String s20, String s21, String s22, String s23) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("INSERT INTO SelfHomeMain_Users VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, s1);
            ps.setString(2, s2);
            ps.setString(3, s3);
            ps.setString(4, s4);
            ps.setString(5, s5);
            ps.setString(6, s6);
            ps.setString(7, s7);
            ps.setString(8, s8);
            ps.setString(9, s9);
            ps.setString(10, s10);
            ps.setString(11, s11);
            ps.setString(12, s12);
            ps.setString(13, s13);
            ps.setString(14, s14);
            ps.setString(15, s15);
            ps.setString(16, s16);
            ps.setString(17, s17);
            ps.setString(18, s18);
            ps.setCharacterStream(19, (Reader) null);
            ps.setString(20, s20);
            ps.setString(21, s21);
            ps.setString(22, s22);
            ps.setString(23, s23);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removePlayer(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("DELETE From SelfHomeMain_Users Where Name = ?");
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFlowerTop(String top) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users Order by flowers DESC");
            res = ps.executeQuery();
            int amount = 1;
            while (res.next()) {
                if (amount == Integer.valueOf(top).intValue()) {
                    result = res.getString("Name");
                    break;
                }
                amount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getPopularityTop(String top) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users Order by popularity DESC");
            res = ps.executeQuery();
            int amount = 1;
            while (res.next()) {
                if (amount == Integer.valueOf(top).intValue()) {
                    result = res.getString("Name");
                    break;
                }
                amount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getLevelTop(String top) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        String result = "";
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users Order by Level DESC");
            res = ps.executeQuery();
            int amount = 1;
            while (res.next()) {
                if (amount == Integer.valueOf(top).intValue()) {
                    result = res.getString("Name");
                    break;
                }
                amount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static int getMyLevelTop(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        int amount = 1;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users Order by Level DESC");
            res = ps.executeQuery();
            while (res.next() &&
                    !res.getString("Name").equalsIgnoreCase(name))
                amount++;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return amount;
    }

    public static List<String> CheckHasPermission(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        List<String> members = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE Members LIKE '%" + name + "%'");
            res = ps.executeQuery();
            while (res.next()) {
                List<String> Members = Arrays.asList(res.getString("Members").split(","));
                for (String e : Members) {
                    if (e.equalsIgnoreCase(name))
                        members.add(res.getString("Name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return members;
    }

    public static void data_import(CommandSender p) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        int amount = 0;
        File folder = new File(
                String.valueOf(String.valueOf(Main.JavaPlugin.getDataFolder().getPath().toString())) + Variable.file_loc_prefix + "playerdata");
        File[] list_files = folder.listFiles();
        for (int c = 0; c < list_files.length; c++) {
            File temp = list_files[c];
            String want_to = temp.getPath().replace(
                            String.valueOf(String.valueOf(Main.JavaPlugin.getDataFolder().getPath().toString())) + Variable.file_loc_prefix + "playerdata", "")
                    .replace(".yml", "").replace(Variable.file_loc_prefix, "");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(temp);
            try {
                ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users WHERE Name = ?");
                ps.setString(1, want_to);
                res = ps.executeQuery();
                if (res.next()) {
                    String str = Variable.Lang_YML.getString("ImportButHasAlreadyExist");
                    if (str.contains("<Name>"))
                        str = str.replace("<Name>", want_to);
                    p.sendMessage(str);
                    continue;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<String> Members = new ArrayList<>();
            List<String> OPs = new ArrayList<>();
            List<String> Denys = new ArrayList<>();
            List<String> Gifts = new ArrayList<>();
            List<String> Advertisement = new ArrayList<>();
            List<String> LimitBlock = new ArrayList<>();
            for (String a : yamlConfiguration.getStringList("Members"))
                Members.add(a);
            for (String a : yamlConfiguration.getStringList("OP"))
                OPs.add(a);
            for (String a : yamlConfiguration.getStringList("Denys"))
                Denys.add(a);
            for (String a : yamlConfiguration.getStringList("gifts"))
                Gifts.add(a);
            for (String a : yamlConfiguration.getStringList("limitblock"))
                LimitBlock.add(a);
            insertvalue(want_to, getListStringSpiltByDot(Members), getListStringSpiltByDot(OPs),
                    getListStringSpiltByDot(Denys), String.valueOf(yamlConfiguration.getBoolean("Public")),
                    String.valueOf(yamlConfiguration.getInt("Level")), String.valueOf(yamlConfiguration.getBoolean("pvp")),
                    String.valueOf(yamlConfiguration.getBoolean("pickup")), String.valueOf(yamlConfiguration.getBoolean("drop")),
                    String.valueOf(yamlConfiguration.getString("Server")), String.valueOf(yamlConfiguration.getBoolean("locktime")),
                    String.valueOf(yamlConfiguration.getBoolean("lockweather")), String.valueOf(yamlConfiguration.getLong("time")),
                    String.valueOf(yamlConfiguration.getDouble("X")), String.valueOf(yamlConfiguration.getDouble("Y")),
                    String.valueOf(yamlConfiguration.getDouble("Z")), String.valueOf(yamlConfiguration.getDouble("flowers")), String.valueOf(yamlConfiguration.getDouble("popularity")), getListStringSpiltByDot(Gifts), getListStringSpiltByDot(Advertisement), yamlConfiguration.getString("icon"), "", getListStringSpiltByDot(LimitBlock));
            amount++;
            String str1 = Variable.Lang_YML.getString("ImportSuccess");
            if (str1.contains("<Name>"))
                str1 = str1.replace("<Name>", want_to);
            p.sendMessage(str1);
            continue;
        }
        String msg = Variable.Lang_YML.getString("ImportFinal");
        if (msg.contains("<Amount>"))
            msg = msg.replace("<Amount>", String.valueOf(amount));
        p.sendMessage(msg);
        try {
            if (res != null)
                res.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void data_export(CommandSender p) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet res = null;
        int amount = 0;
        try {
            ps = con.prepareStatement("SELECT * FROM SelfHomeMain_Users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String Public = rs.getString("Public");
                String Level = rs.getString("Level");
                String PVP = rs.getString("pvp");
                String Pickup = rs.getString("pickup");
                String drop = rs.getString("dropitem");
                String Server = rs.getString("Server");
                String locktime = rs.getString("locktime");
                String lockweather = rs.getString("lockweather");
                String time = rs.getString("time");
                String X = rs.getString("X");
                String Y = rs.getString("Y");
                String Z = rs.getString("Z");
                String icon = rs.getString("icon");
                String flowers = rs.getString("flowers");
                String popularity = rs.getString("popularity");
                if (!Server.equalsIgnoreCase(Main.JavaPlugin.getConfig().getString("Server"))) {
                    String str = Variable.Lang_YML.getString("ExportButServerNotEqual");
                    if (str.contains("<Name>"))
                        str = str.replace("<Name>", name);
                    if (str.contains("<Server>"))
                        str = str.replace("<Server>", Server);
                    p.sendMessage(str);
                    continue;
                }
                File playerdata = new File(String.valueOf(String.valueOf(Main.JavaPlugin.getDataFolder().getPath().toString())) +
                        Variable.file_loc_prefix + "playerdata" + Variable.file_loc_prefix + name + ".yml");
                if (playerdata.exists()) {
                    String str = Variable.Lang_YML.getString("ExportButHasAlreadyExist");
                    if (str.contains("<Name>"))
                        str = str.replace("<Name>", name);
                    p.sendMessage(str);
                    continue;
                }
                try {
                    playerdata.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    playerdata.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(playerdata);
                yamlConfiguration.createSection("Members");
                yamlConfiguration.createSection("OP");
                yamlConfiguration.createSection("Denys");
                yamlConfiguration.createSection("Public");
                yamlConfiguration.createSection("Level");
                yamlConfiguration.createSection("pvp");
                yamlConfiguration.createSection("pickup");
                yamlConfiguration.createSection("drop");
                yamlConfiguration.createSection("Server");
                yamlConfiguration.createSection("locktime");
                yamlConfiguration.createSection("lockweather");
                yamlConfiguration.createSection("time");
                yamlConfiguration.createSection("X");
                yamlConfiguration.createSection("Y");
                yamlConfiguration.createSection("Z");
                yamlConfiguration.createSection("icon");
                yamlConfiguration.set("Members", getMembers(name));
                yamlConfiguration.set("OP", getOP(name));
                yamlConfiguration.set("Denys", getDenys(name));
                yamlConfiguration.set("Public", Boolean.valueOf(Public));
                yamlConfiguration.set("Level", Integer.valueOf(Level));
                yamlConfiguration.set("pvp", Boolean.valueOf(PVP));
                yamlConfiguration.set("pickup", Boolean.valueOf(Pickup));
                yamlConfiguration.set("drop", Boolean.valueOf(drop));
                yamlConfiguration.set("Server", Server);
                yamlConfiguration.set("locktime", Boolean.valueOf(locktime));
                yamlConfiguration.set("lockweather", Boolean.valueOf(lockweather));
                yamlConfiguration.set("time", Long.valueOf(time));
                yamlConfiguration.set("X", Double.valueOf(X));
                yamlConfiguration.set("Y", Double.valueOf(Y));
                yamlConfiguration.set("Z", Double.valueOf(Z));
                try {
                    yamlConfiguration.set("flowers", Integer.valueOf(flowers));
                } catch (NumberFormatException e) {
                    yamlConfiguration.set("flowers", Integer.valueOf(0));
                }
                try {
                    yamlConfiguration.set("popularity", Integer.valueOf(popularity));
                } catch (NumberFormatException e) {
                    yamlConfiguration.set("popularity", popularity);
                }
                try {
                    yamlConfiguration.set("gifts", getGift(name));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                yamlConfiguration.set("advertisement", getAdvertisement(name));
                yamlConfiguration.set("limitblock", getLimitBlock(name));
                yamlConfiguration.set("icon", icon);
                try {
                    yamlConfiguration.save(playerdata);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String str1 = Variable.Lang_YML.getString("ExportSuccess");
                if (str1.contains("<Name>"))
                    str1 = str1.replace("<Name>", name);
                p.sendMessage(str1);
                amount++;
            }
            String temp = Variable.Lang_YML.getString("ExportFinal");
            if (temp.contains("<Amount>"))
                temp = temp.replace("<Amount>", String.valueOf(amount));
            p.sendMessage(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null)
                    res.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS SelfHomeMain_Users (Name VARCHAR(255),Members VARCHAR(255),OP VARCHAR(255),Denys VARCHAR(255),Public VARCHAR(255),Level VARCHAR(255),pvp VARCHAR(255),pickup VARCHAR(255),dropitem VARCHAR(255),Server VARCHAR(255),locktime VARCHAR(255),lockweather VARCHAR(255),time VARCHAR(255),X VARCHAR(255),Y VARCHAR(255),Z VARCHAR(255),flowers VARCHAR(100),popularity VARCHAR(100),gifts TEXT default null,advertisement VARCHAR(255),icon VARCHAR(255),visittime VARCHAR(255),limitblock varchar(255))");
            int ss = ps.executeUpdate();
            if (ss != 0)
                Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("CreateDataBaseTableSuccess"));
            PreparedStatement ps2 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS SelfHomeMain_Servers (Server VarChar(100),Amount double)");
            ps2.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Variable.Lang_YML.getString("CreateDataBaseTableError"));
            e.printStackTrace();
            return;
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        try {
            return HikariCPUtils.sqlConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
