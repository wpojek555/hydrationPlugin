package wpojek555.hydrationplugin.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.data.PlayerData;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

public class OnDeath implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        String deathMSG = e.getDeathMessage();

        if (e.getEntity() instanceof Player){
            PlayerData playerData = new PlayerData();
            playerData.setThirsty(HydrationPlugin.getInstance().Hydratiion_level_maximum);

            Player player = (Player)e.getEntity();
            PlayerUtility.setPlayerData(player, playerData);
            if (OnMove.died){
                String text = HydrationPlugin.getInstance().death_message.replace("&", "§");
                e.setDeathMessage(text.replace("<Player>", player.getName()));;
                OnMove.died = false;


            }

        }

    }
}
