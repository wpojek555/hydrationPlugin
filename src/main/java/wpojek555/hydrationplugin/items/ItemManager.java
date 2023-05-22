package wpojek555.hydrationplugin.items;


import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import wpojek555.hydrationplugin.HydrationPlugin;

import java.util.Arrays;

public class ItemManager {
    public static ItemStack clearWater;
    public static ItemStack BidonItem;
    public static ItemStack DirtyWater;

    public static void init(){
        createClearWater();
        createDirtyWater();
    }
    public static void createClearWater() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        ItemMeta meta = item.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + HydrationPlugin.getInstance().clean_water_name);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        clearWater = item;
    }

    public static void createDirtyWater() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        ItemMeta meta = item.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        meta.setDisplayName(ChatColor.getByChar("6") + "" + ChatColor.BOLD + HydrationPlugin.getInstance().dirty_water_name);
//        meta.setLore(Arrays.asList(ChatColor.GRAY + "Brudna i słona woda"));
        item.setItemMeta(meta);
        DirtyWater = item;
    }
}
