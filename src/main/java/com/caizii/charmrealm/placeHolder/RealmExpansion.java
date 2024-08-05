package com.caizii.charmrealm.placeHolder;

import com.caizii.charmrealm.CharmRealm;
import com.caizii.charmrealm.utils.Home;
import com.caizii.charmrealm.utils.HomeAPI;
import com.caizii.charmrealm.utils.Util;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class RealmExpansion extends PlaceholderExpansion {

    public boolean canRegister() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public @NotNull String getIdentifier() {
        return "realm";
    }

    /**
     * @return
     */
    @Override
    public @NotNull String getAuthor() {
        return "Caizii";
    }

    /**
     * @return
     */
    @Override
    public @NotNull String getVersion() {
        return "1.0.29";
    }

    @Override
    public boolean persist() {
        return true; //
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String result_check = null;

        if (params.contains("time_created_")) {
            String temp = params.replace("time_created_", "");
            if (!Util.CheckIsHome(temp)) {
                result_check = CharmRealm.pluginVariable.Lang_YML.getString("PlaceHolders.NoHome");
                return result_check;
            }
            Home home = HomeAPI.getHome(temp);
            result_check = String.valueOf(home.getCreatedDate());
            return result_check;

        }

        if (params.contains("world_player_")) {
            String temp = params.replace("world_player_", "");
            if (!Util.CheckIsHome(temp)) {
                return String.valueOf(0);
            }
            World finded_world = Bukkit.getWorld(temp);
            if (finded_world == null) return String.valueOf(0);
            else return String.valueOf(finded_world.getPlayers().size());
        }

        return null; //
    }
}
