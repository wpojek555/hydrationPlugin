package wpojek555.hydrationplugin.Events;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnFillBottle implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.GLASS_BOTTLE && event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block waterBlock = player.getTargetBlockExact(6, FluidCollisionMode.SOURCE_ONLY);
            if (waterBlock.getType() == Material.WATER) {
//                for (ItemStack item2 : player.getInventory().getContents()) {
//                    if (item2 != null && item2.getType() == Material.POTION) {
//                        PotionMeta potionMeta = (PotionMeta) item2.getItemMeta();
//                        if (potionMeta.getBasePotionData().getType() == PotionType.WATER) {
//                            // Usuń znaleziony `POTION` z ekwipunku gracza
//                            player.getInventory().removeItem(item2);
//                            break; // przerywamy pętlę po znalezieniu i usunięciu pierwszego wystąpienia
//                        }
//                    }
//                }
//
//// Dodaj DirtyWater do ekwipunku gracza
//                player.getInventory().addItem(ItemManager.DirtyWater);
            }
        }
    }
}
