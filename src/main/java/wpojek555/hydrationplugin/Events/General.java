package wpojek555.hydrationplugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.data.PlayerData;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

import java.io.File;
import java.io.IOException;

public class General implements Listener {

    @EventHandler
    public void OnJoin (PlayerJoinEvent e) {
        // Obsługa zdarzenia dołączenia gracza do serwera

        PlayerData playerData = new PlayerData();
        File f = new File(PlayerUtility.getFolderPath(e.getPlayer()) + "general.yml");

        if(f.exists()) {
            // Jeśli istnieje plik dla gracza, wczytaj dane z pliku
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            playerData.setThirsty(cfg.getInt("stats.thirsty"));
        } else {
            // Jeśli plik nie istnieje, ustaw domyślną wartość dla poziomu pragnienia gracza
            playerData.setThirsty(HydrationPlugin.getInstance().Hydratiion_level_maximum);
        }

        PlayerUtility.setPlayerData(e.getPlayer(), playerData);
        Player p = e.getPlayer();
        BossBar bossBar = Bukkit.createBossBar(HydrationPlugin.getInstance().bossBar_Tittle, BarColor.valueOf(HydrationPlugin.getInstance().bossBar_Color), BarStyle.valueOf(HydrationPlugin.getInstance().bossBar_Style));
        bossBar.addPlayer(p);
        float thirstyPercentage = (float) PlayerUtility.getPlayerData(p).getThirsty() / HydrationPlugin.getInstance().Hydratiion_level_maximum;
        bossBar.setProgress(thirstyPercentage);
        HydrationPlugin.addBossBar(p, bossBar);
    }

    @EventHandler
    public void OnQuit (PlayerQuitEvent e) {
        // Obsługa zdarzenia opuszczenia serwera przez gracza

        PlayerData data = PlayerUtility.getPlayerData(e.getPlayer());
        File f = new File(PlayerUtility.getFolderPath(e.getPlayer()) + "general.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.set("stats.thirsty", data.getThirsty());

        try { cfg.save(f); } catch (IOException err) { err.printStackTrace();}
        PlayerUtility.setPlayerData(e.getPlayer(), null);
        BossBar bossBar = HydrationPlugin.getBossBar(e.getPlayer());
        if (bossBar != null) {
            bossBar.removeAll();
            HydrationPlugin.removeBossBar(e.getPlayer());
        }
    }
}
