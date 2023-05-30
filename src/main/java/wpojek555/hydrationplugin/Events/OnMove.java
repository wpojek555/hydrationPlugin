package wpojek555.hydrationplugin.Events;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.data.PlayerData;
import wpojek555.hydrationplugin.items.ItemManager;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

import static org.bukkit.entity.Pose.SWIMMING;

public class OnMove implements Listener {

    public static boolean died = false;
    static boolean wasThirsty = false;
    boolean OnePick = false;
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        PlayerData memory = new PlayerData();
/*        if (HydrationPlugin.getInstance().isDroughtPeriod(p.getWorld())) {
            p.sendMessage(ChatColor.GREEN + "Susza");
        }*/


        if(HydrationPlugin.getInstance().items_enabled) {
            for (ItemStack item2 : p.getInventory().getContents()) {
                if (item2 != null && item2.getType() == Material.POTION) {
                    PotionMeta potionMeta = (PotionMeta) item2.getItemMeta();
                    if (potionMeta.getBasePotionData().getType() == PotionType.WATER && !(potionMeta.getDisplayName().startsWith(ChatColor.AQUA + "" + ChatColor.BOLD) || potionMeta.getDisplayName().equals(ChatColor.getByChar("6") + "" + ChatColor.BOLD + HydrationPlugin.getInstance().dirty_water_name) || (potionMeta.getDisplayName().startsWith(ChatColor.GREEN + "" + ChatColor.BOLD)))) {
                        // Usuń znaleziony `POTION` z ekwipunku gracza
                        p.getInventory().removeItem(item2);
                        p.getInventory().addItem(ItemManager.DirtyWater);
                        break; // przerywamy pętlę po znalezieniu i usunięciu pierwszego wystąpienia
                    }
                }
            }
        }


// Dodaj DirtyWater do ekwipunku gracza


//        if (p.getGameMode() == GameMode.SURVIVAL){
        int count = PlayerUtility.getPlayerData(p).getThirsty();
if (p.getGameMode() == GameMode.SURVIVAL){

        if(p.isSneaking()) {

        } else if (p.isSprinting()) {

            count = PlayerUtility.getPlayerData(p).getThirsty() - 3;
        } else {
            count = PlayerUtility.getPlayerData(p).getThirsty() - 1;
        }



        if (count < 1){
            memory.setThirsty(0);
        } else {
            memory.setThirsty(count);
        }

        PlayerUtility.setPlayerData(p, memory);
        if(OnePick){
            BossBar bossBar2 = HydrationPlugin.getBossBar(p);

            bossBar2.addPlayer(p);
            HydrationPlugin.addBossBar(p, bossBar2);
            OnePick = false;

        }
        BossBar bossBar = HydrationPlugin.getBossBar(p);

    if((p.getWorld().getEnvironment() == World.Environment.NETHER) || (HydrationPlugin.getInstance().isDroughtActive)) {

        if(p.isSneaking()) {

        } else if (p.isSprinting()) {

            count = PlayerUtility.getPlayerData(p).getThirsty() - 3;
        } else {
            count = PlayerUtility.getPlayerData(p).getThirsty() - 1;
        }
    }
        if (bossBar != null) {
            float thirstyPercentage = (float) PlayerUtility.getPlayerData(p).getThirsty() / HydrationPlugin.getInstance().Hydratiion_level_maximum;
            bossBar.setProgress(thirstyPercentage);
        }
        if (count < (HydrationPlugin.getInstance().Hydratiion_level_medium + 1)) {
            bossBar.setColor(BarColor.valueOf(HydrationPlugin.getInstance().bossBar_Color_medium));
            wasThirsty = true;
            p.addPotionEffect((new PotionEffect(PotionEffectType.SLOW, 600, 1, false, false, false )));


        if (count < (HydrationPlugin.getInstance().Hydratiion_level_min + 1)) {
            wasThirsty = true;


                p.addPotionEffect((new PotionEffect(PotionEffectType.SLOW, 600, 3, false, false, false )));



            bossBar.setColor(BarColor.valueOf(HydrationPlugin.getInstance().bossBar_Color_low));
            p.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 600, 255, false, false, false )));
            p.addPotionEffect((new PotionEffect(PotionEffectType.WEAKNESS, 600, 3, false, false, true )));
            p.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 600, 1, false, false, false )));
            p.addPotionEffect((new PotionEffect(PotionEffectType.POISON, 600, 1, false, false, true )));
          if (count < 1) {


              died = true;
            p.setHealth(0);

            wasThirsty = false;
            memory.setThirsty(HydrationPlugin.getInstance().Hydratiion_level_maximum);
            PlayerUtility.setPlayerData(p, memory);
            bossBar.setColor(BarColor.valueOf(HydrationPlugin.getInstance().bossBar_Color));
        }}} else {
            if ((p.getWorld().getEnvironment() == World.Environment.NETHER) || (HydrationPlugin.getInstance().isDroughtActive)){
                bossBar.setColor(BarColor.WHITE);
            } else {
                bossBar.setColor(BarColor.valueOf(HydrationPlugin.getInstance().bossBar_Color));
            }
            if(wasThirsty) {
                p.removePotionEffect(PotionEffectType.WEAKNESS);
                p.removePotionEffect(PotionEffectType.SLOW);
                p.removePotionEffect(PotionEffectType.BLINDNESS);
                p.removePotionEffect(PotionEffectType.POISON);
                p.removePotionEffect(PotionEffectType.CONFUSION);
                wasThirsty = false;
            }
            bossBar.setTitle(HydrationPlugin.getInstance().bossBar_Tittle);
            bossBar.setStyle(BarStyle.valueOf(HydrationPlugin.getInstance().bossBar_Style));

        }} else {
    BossBar bossBar = HydrationPlugin.getBossBar(p);
    bossBar.removePlayer(p);
    OnePick = true;


}
        float Amount = PlayerUtility.getPlayerData(p).getThirsty();
        float result = (Amount / (float) (HydrationPlugin.getInstance().Hydratiion_level_maximum)) * 100;
        int result2 = Math.round(result);
        HydrationPlugin.getInstance().HydrationLevel.put(p, result2);
    }


}

