package wpojek555.hydrationplugin.utilities;


import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import wpojek555.hydrationplugin.data.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class PlayerUtility {
    private static Map<String, PlayerData> playerData = new HashMap<>();

    public static PlayerData getPlayerData(Player p) {
        if(!playerData.containsKey(p.getUniqueId().toString())){
            PlayerData m = new PlayerData();
            playerData.put(p.getUniqueId().toString(), m);
            return m;

        }
        return playerData.get(p.getUniqueId().toString());
    }

    public static void setPlayerData(Player p, PlayerData memory) {
        playerData.put(p.getUniqueId().toString(), memory);
    }
    public static String getFolderPath(Player p) {
        return Bukkit.getPluginManager().getPlugin("hydrationPlugin").getDataFolder().getAbsolutePath() + "/player/" + p.getUniqueId().toString();
    }
}
