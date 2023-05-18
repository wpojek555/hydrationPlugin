package wpojek555.hydrationplugin.Events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.data.PlayerData;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

public class OnBedLeave implements Listener{
    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        if (HydrationPlugin.getInstance().bed_damage_bool){
            Player p = event.getPlayer();
            PlayerData memory = new PlayerData();
            int count = PlayerUtility.getPlayerData(p).getThirsty() - HydrationPlugin.getInstance().bed_damage_value;
            if (count < 1) {
                count = 0;
            }
            memory.setThirsty(count);
            PlayerUtility.setPlayerData(p, memory);
        }

    }
}
