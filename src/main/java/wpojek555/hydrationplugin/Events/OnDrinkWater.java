package wpojek555.hydrationplugin.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.data.PlayerData;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

public class OnDrinkWater implements Listener {

    @EventHandler
    public void OnDrinkWater(PlayerItemConsumeEvent e){
        // Obsługa zdarzenia spożycia przedmiotu przez gracza

        if (e.getItem().getType().equals(Material.POTION)) {
            Player p = e.getPlayer();
            int count;
            PlayerData memory = new PlayerData();
            ItemStack item = e.getItem();
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

            if (HydrationPlugin.getInstance().items_enabled) {
                // Jeśli plugin obsługuje przedmioty, sprawdź typ i nazwę napoju

                if(e.getItem().getItemMeta().getDisplayName().startsWith(ChatColor.AQUA + "" + ChatColor.BOLD) || !(potionMeta.getBasePotionData().getType() == PotionType.WATER)){
                    // Jeśli nazwa zaczyna się od niebieskiego pogrubionego tekstu lub typ napoju nie jest wodą, dodaj poziom pragnienia dla czystej wody
                    count = PlayerUtility.getPlayerData(p).getThirsty() + HydrationPlugin.getInstance().clean_water_level;
                } else if(e.getItem().getItemMeta().getDisplayName().startsWith(ChatColor.GREEN + "" + ChatColor.BOLD) || !(potionMeta.getBasePotionData().getType() == PotionType.WATER)){
                    // Jeśli nazwa zaczyna się od zielonego pogrubionego tekstu lub typ napoju nie jest wodą, dodaj stały poziom pragnienia
                    count = PlayerUtility.getPlayerData(p).getThirsty() + 4000;
                } else {
                    // W przeciwnym razie dodaj poziom pragnienia dla brudnej wody i nałóż efekt "confusion" na gracza
                    count = PlayerUtility.getPlayerData(p).getThirsty() + HydrationPlugin.getInstance().dirty_water_level;
                    p.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 400, 1, true, true, true )));
                }
            } else {
                // Jeśli wtyczka nie obsługuje przedmiotów, dodaj poziom pragnienia dla czystej wody
                count = PlayerUtility.getPlayerData(p).getThirsty() + HydrationPlugin.getInstance().clean_water_level;
            }

            if (count > (HydrationPlugin.getInstance().Hydratiion_level_maximum - 1)){
                // Jeśli przekroczono maksymalny poziom pragnienia, ustaw maksymalny poziom pragnienia
                memory.setThirsty(HydrationPlugin.getInstance().Hydratiion_level_maximum);
            } else {
                memory.setThirsty(count);
            }

            PlayerUtility.setPlayerData(p, memory);
            BossBar bossBar = HydrationPlugin.getBossBar(p);
            if (bossBar != null) {
                // Jeśli gracz ma pasek bossa, zaktualizuj jego postęp na podstawie nowego poziomu pragnienia
                float thirstyPercentage = (float) PlayerUtility.getPlayerData(p).getThirsty() / HydrationPlugin.getInstance().Hydratiion_level_maximum;
                bossBar.setProgress(thirstyPercentage);
            }
        }
    }
}
