package com.caizii.charmrealm.utils;

import com.caizii.charmrealm.CharmRealm;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class Home {
    public String name;

    private UUID playerOwnerUUID;

    private String CreatedDate;

    List<String> Members;

    List<String> OPs;

    List<String> Denys;

    boolean allowStranger;

    public int level;

    public boolean pvp;

    public boolean pickup;

    public boolean dropitem;

    public String Server;

    public boolean locktime;

    public boolean lockweather;

    public long time;

    public double X;

    public double Y;

    public double Z;

    public int flowers;

    public int Popularity;

    public List<String> Gifts;

    public List<String> Advertisement;

    public List<String> LimitBlock;

    public String icon;

    public String toString() {
        return "Home [name=" + this.name + "]";
    }

    public Home(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCreatedDate() {
        File f = new File(CharmRealm.pluginVariable.Tempf, this.name + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);

        if (yamlConfiguration.isString("CreatedDate")) {
            return yamlConfiguration.getString("CreatedDate");
        } else {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月 d号, yyyy年", Locale.CHINESE);
            String formattedDate = currentDate.format(formatter);

            yamlConfiguration.set("CreatedDate", formattedDate);
            try {
                yamlConfiguration.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.CreatedDate = formattedDate;
            return formattedDate;
        }
    }

    public UUID getPlayerOwnerUUID() {

        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        this.playerOwnerUUID = UUID.fromString(Objects.requireNonNull(yamlConfiguration.getString("playerOwnerUUID")));
        return playerOwnerUUID;
    }

    public List<String> getGifts() {
        if (CharmRealm.pluginVariable.bungee)
            try {
                return MySQL.getGift(this.name);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getStringList("gifts");
    }

    public void setGifts(List<String> Gifts) throws IOException {
        this.Gifts = Gifts;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setGifts(this.name, MySQL.getListStringSpiltByDot(Gifts));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("gifts", Gifts);
            yamlConfiguration.save(f);
        }
    }

    public List<String> getLimitBlock() {
        if (CharmRealm.pluginVariable.bungee)
            return MySQL.getLimitBlock(this.name);
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getStringList("limitblock");
    }

    public List<String> getAdvertisement() {
        if (CharmRealm.pluginVariable.bungee)
            return MySQL.getAdvertisement(this.name);
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getStringList("advertisement");
    }

    public void setAdvertisement(List<String> adv) throws IOException {
        this.Advertisement = adv;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setAdvertisement(this.name, MySQL.getListStringSpiltByDot(this.Advertisement));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("advertisement", this.Advertisement);
            yamlConfiguration.save(f);
        }
    }

    public void setLimitBlock(List<String> adv) throws IOException {
        this.LimitBlock = adv;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setLimitBlock(this.name, MySQL.getListStringSpiltByDot(this.LimitBlock));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("limitblock", this.LimitBlock);
            yamlConfiguration.save(f);
        }
    }

    public String getIcon() {
        if (CharmRealm.pluginVariable.bungee)
            return MySQL.getIcon(this.name);
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getString("icon");
    }

    public void setIcon(String str) throws IOException {
        this.icon = str;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setIcon(this.name, String.valueOf(this.icon));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("icon", this.icon);
            yamlConfiguration.save(f);
        }
    }

    public int getFlowers() {
        if (CharmRealm.pluginVariable.bungee)
            return Integer.valueOf(MySQL.getFlowers(this.name)).intValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getInt("flowers");
    }

    public void setFlowers(int amount) throws IOException {
        this.flowers = amount;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setFlowers(this.name, String.valueOf(this.flowers));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("flowers", Integer.valueOf(this.flowers));
            yamlConfiguration.save(f);
        }
    }

    public int getPopularity() {
        if (CharmRealm.pluginVariable.bungee)
            return Integer.valueOf(MySQL.getPopularity(this.name)).intValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getInt("popularity");
    }

    public void setPopularity(int amount) throws IOException {
        this.Popularity = amount;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setPopularity(this.name, String.valueOf(this.Popularity));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("popularity", Integer.valueOf(this.Popularity));
            yamlConfiguration.save(f);
        }
    }

    public List<String> getMembers() {
        if (CharmRealm.pluginVariable.bungee)
            return MySQL.getMembers(this.name);
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getStringList("Members");
    }

    public void setMembers(List<String> members) throws IOException {
        this.Members = members;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setMembers(this.name, MySQL.getListStringSpiltByDot(this.Members));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("Members", this.Members);
            yamlConfiguration.save(f);
        }
    }

    public List<String> getOPs() {
        if (CharmRealm.pluginVariable.bungee)
            return MySQL.getOP(this.name);
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getStringList("OP");
    }

    public void setOPs(List<String> oPs) throws IOException {
        this.OPs = oPs;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setOP(this.name, MySQL.getListStringSpiltByDot(this.OPs));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("OP", this.OPs);
            yamlConfiguration.save(f);
        }
    }

    public List<String> getDenys() {
        if (CharmRealm.pluginVariable.bungee)
            return MySQL.getDenys(this.name);
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getStringList("Denys");
    }

    public void setDenys(List<String> denys) throws IOException {
        this.Denys = denys;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setDenys(this.name, MySQL.getListStringSpiltByDot(this.Denys));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("Denys", this.Denys);
            yamlConfiguration.save(f);
        }
    }

    public boolean isAllowStranger() {
        if (CharmRealm.pluginVariable.bungee)
            return Boolean.valueOf(MySQL.getPublic(this.name)).booleanValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getBoolean("Public");
    }

    public void setAllowStranger(boolean allowStranger) throws IOException {
        this.allowStranger = allowStranger;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setPublic(this.name, String.valueOf(this.allowStranger));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("Public", Boolean.valueOf(this.allowStranger));
            yamlConfiguration.save(f);
        }
    }

    public int getLevel() {
        if (CharmRealm.pluginVariable.bungee)
            return Integer.valueOf(MySQL.getLevel(this.name)).intValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getInt("Level");
    }

    public void setLevel(int level) throws IOException {
        this.level = level;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setLevel(this.name, String.valueOf(this.level));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("Level", Integer.valueOf(this.level));
            yamlConfiguration.save(f);
        }
    }

    public boolean isPvp() {
        if (CharmRealm.pluginVariable.bungee)
            return Boolean.valueOf(MySQL.getPVP(this.name)).booleanValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getBoolean("PVP");
    }

    public void setPvp(boolean pvp) throws IOException {
        this.pvp = pvp;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setLevel(this.name, String.valueOf(this.pvp));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("pvp", Boolean.valueOf(this.pvp));
            yamlConfiguration.save(f);
        }
    }

    public boolean isPickup() {
        if (CharmRealm.pluginVariable.bungee)
            return Boolean.valueOf(MySQL.getpickup(this.name)).booleanValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getBoolean("pickup");
    }

    public void setPickup(boolean pickup) throws IOException {
        this.pickup = pickup;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setpickup(this.name, String.valueOf(this.pickup));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("pickup", Boolean.valueOf(this.pickup));
            yamlConfiguration.save(f);
        }
    }

    public boolean isDropitem() {
        if (CharmRealm.pluginVariable.bungee)
            return Boolean.valueOf(MySQL.getdropitem(this.name)).booleanValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getBoolean("drop");
    }

    public void setDropitem(boolean dropitem) throws IOException {
        this.dropitem = dropitem;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setLevel(this.name, String.valueOf(this.dropitem));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("drop", Boolean.valueOf(this.dropitem));
            yamlConfiguration.save(f);
        }
    }

    public String getServer() {
        if (CharmRealm.pluginVariable.bungee)
            return MySQL.getServer(this.name);
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getString("Server");
    }

    public boolean isLocktime() {
        if (CharmRealm.pluginVariable.bungee)
            return Boolean.valueOf(MySQL.getlocktime(this.name)).booleanValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getBoolean("locktime");
    }

    public void setLocktime(boolean locktime) throws IOException {
        this.locktime = locktime;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setlocktime(this.name, String.valueOf(this.locktime));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("locktime", Boolean.valueOf(this.locktime));
            yamlConfiguration.save(f);
        }
    }

    public boolean isLockweather() {
        if (CharmRealm.pluginVariable.bungee)
            return Boolean.valueOf(MySQL.getlockweather(this.name)).booleanValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getBoolean("lockweather");
    }

    public void setLockweather(boolean lockweather) throws IOException {
        this.lockweather = lockweather;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setlockweather(this.name, String.valueOf(this.lockweather));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("lockweather", Boolean.valueOf(this.lockweather));
            yamlConfiguration.save(f);
        }
    }

    public long getTime() {
        if (CharmRealm.pluginVariable.bungee)
            return Long.valueOf(MySQL.gettime(this.name)).longValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getLong("time");
    }

    public void setTime(long time) throws IOException {
        this.time = time;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.settime(this.name, String.valueOf(this.time));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("time", Long.valueOf(this.time));
            yamlConfiguration.save(f);
        }
    }

    public double getX() {
        if (CharmRealm.pluginVariable.bungee)
            return Double.valueOf(MySQL.getX(this.name)).doubleValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getDouble("X");
    }

    public void setX(double x) throws IOException {
        this.X = x;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setX(this.name, String.valueOf(this.X));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("X", Double.valueOf(this.X));
            yamlConfiguration.save(f);
        }
    }

    public double getY() {
        if (CharmRealm.pluginVariable.bungee)
            return Double.valueOf(MySQL.getY(this.name)).doubleValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getDouble("Y");
    }

    public void setY(double y) throws IOException {
        this.Y = y;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setY(this.name, String.valueOf(this.Y));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("Y", Double.valueOf(this.Y));
            yamlConfiguration.save(f);
        }
    }

    public double getZ() {
        if (CharmRealm.pluginVariable.bungee)
            return Double.valueOf(MySQL.getZ(this.name)).doubleValue();
        File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
        return yamlConfiguration.getDouble("Z");
    }

    public void setZ(double z) throws IOException {
        this.Z = z;
        if (CharmRealm.pluginVariable.bungee) {
            MySQL.setZ(this.name, String.valueOf(this.Z));
        } else {
            File f = new File(CharmRealm.pluginVariable.Tempf, String.valueOf(String.valueOf(this.name)) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
            yamlConfiguration.set("Z", Double.valueOf(this.Z));
            yamlConfiguration.save(f);
        }
    }
}
