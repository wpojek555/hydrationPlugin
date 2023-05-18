package wpojek555.hydrationplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import wpojek555.hydrationplugin.Commands.GetLevelCMD;
import wpojek555.hydrationplugin.Commands.SetThirstyCMD;
import wpojek555.hydrationplugin.Events.*;
import wpojek555.hydrationplugin.data.PlayerData;
import wpojek555.hydrationplugin.items.ItemManager;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class HydrationPlugin extends JavaPlugin {

    public String bossBar_Tittle;
    public String bossBar_Style;
    public String bossBar_Color;
    public String bossBar_Color_medium;
    public String bossBar_Color_low;
    public int Hydratiion_level_maximum;
    public int Hydratiion_level_medium;
    public int Hydratiion_level_min;
    public int clean_water_level;
    public String clean_water_name;
    public int dirty_water_level;
    public String dirty_water_name;
    public boolean bed_damage_bool;
    public int bed_damage_value;
    public boolean items_enabled;
    public String death_message;
    private static HydrationPlugin instance;
    private Map<Player, BossBar> bossBars;

    public static HydrationPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Logika uruchamiania pluginu

        instance = this;
        bossBars = new HashMap<>();
        cfgSetup();
        printDrop();
        ItemManager.init();
        FurnaceRecipe clearWaterRecipe = new FurnaceRecipe(new NamespacedKey(this, "clearwater"), ItemManager.clearWater, Material.POTION, 1f, 100);
        Bukkit.addRecipe(clearWaterRecipe);

        ShapelessRecipe recipe = new ShapelessRecipe(ItemManager.BidonItem);
        recipe.addIngredient(ItemManager.clearWater.getData());
        recipe.addIngredient(ItemManager.clearWater.getData());
        recipe.addIngredient(ItemManager.clearWater.getData());
        recipe.addIngredient(ItemManager.clearWater.getData());
        Bukkit.addRecipe(recipe);

        getServer().getPluginManager().registerEvents(new OnBedLeave(), this);
        getServer().getPluginManager().registerEvents(new OnDeath(), this);
        getServer().getPluginManager().registerEvents(new OnDrinkWater(), this);
        getServer().getPluginManager().registerEvents(new OnMove(), this);
        getServer().getPluginManager().registerEvents(new OnFillBottle(), this);
        getServer().getPluginManager().registerEvents(new General(), this);
        getCommand("setHydration").setExecutor(new SetThirstyCMD());
        getCommand("hydrationLevel").setExecutor(new GetLevelCMD());
        PlayerBossBarLoaderIfReloaded();
    }

    @Override
    public void onDisable() {
        // Logika wyłączania pluginu

        for (BossBar bossBar : bossBars.values()) {
            bossBar.removeAll();
        }
        bossBars.clear();

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData data = PlayerUtility.getPlayerData(player);
            File f = new File(PlayerUtility.getFolderPath(player) + "general.yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            cfg.set("stats.thirsty", data.getThirsty());

            try {
                cfg.save(f);
            } catch (IOException err) {
                err.printStackTrace();
            }

            PlayerUtility.setPlayerData(player, null);
        }
    }

    public static BossBar getBossBar(Player player) {
        HydrationPlugin plugin = HydrationPlugin.getInstance();
        return plugin.bossBars.get(player);
    }

    public static void addBossBar(Player player, BossBar bossBar) {
        HydrationPlugin plugin = HydrationPlugin.getInstance();
        plugin.bossBars.put(player, bossBar);
    }

    public static void removeBossBar(Player player) {
        HydrationPlugin plugin = HydrationPlugin.getInstance();
        plugin.bossBars.remove(player);
    }

    public void cfgSetup() {
        // Konfiguracja pluginu

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        bossBar_Tittle = getConfig().getString("bossbar-Tittle");
        bossBar_Color = getConfig().getString("bossbar-color");
        bossBar_Color_medium = getConfig().getString("bossbar-color-medium");
        bossBar_Color_low = getConfig().getString("bossbar-color-low");
        bossBar_Style = getConfig().getString("bossbar-style");
        Hydratiion_level_maximum = getConfig().getInt("Hydration-level-max");
        Hydratiion_level_medium = getConfig().getInt("Hydration-level-medium");
        Hydratiion_level_min = getConfig().getInt("Hydration-level-min");
        items_enabled = getConfig().getBoolean("custom-items-active");
        clean_water_name = getConfig().getString("clean_water_name");
        clean_water_level = getConfig().getInt("clean_water_value");
        dirty_water_name = getConfig().getString("dirty_water_name");
        dirty_water_level = getConfig().getInt("dirty_water_value");
        bed_damage_bool = getConfig().getBoolean("bed_damage");
        bed_damage_value = getConfig().getInt("bed_damage_value");
        death_message = getConfig().getString("death_message");
    }

    public void PlayerBossBarLoaderIfReloaded() {
        // Ładowanie pasków bossa dla graczy po ponownym załadowaniu pluginu

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = new PlayerData();
            File f = new File(PlayerUtility.getFolderPath(player) + "general.yml");

            if (f.exists()) {
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
                playerData.setThirsty(cfg.getInt("stats.thirsty"));
            } else {
                playerData.setThirsty(Hydratiion_level_maximum);
            }

            PlayerUtility.setPlayerData(player, playerData);
            Player p;
            p = player;
            BossBar bossBar = Bukkit.createBossBar(bossBar_Tittle, BarColor.valueOf(bossBar_Color), BarStyle.valueOf(bossBar_Style));
            bossBar.addPlayer(p);
            float thirstyPercentage = (float) PlayerUtility.getPlayerData(p).getThirsty() / Hydratiion_level_maximum;
            bossBar.setProgress(thirstyPercentage);
            HydrationPlugin.addBossBar(p, bossBar);
        }
    }

    public void printDrop() {
        // Metoda do wydruku w konsoli ASCII artu "drop"

        System.out.println("|              ");
        System.out.println("|       ##      ");
        System.out.println("|       ##      ");
        System.out.println("|     ######    ");
        System.out.println("|     ######    ");
        System.out.println("|   ##########  ");
        System.out.println("|   ##  ######  ");
        System.out.println("| ##  ##########");
        System.out.println("| ##  ##########");
        System.out.println("| ####  ########");
        System.out.println("|   ##########  ");
        System.out.println("|     ######    ");
        System.out.println("|              ");
    }
}
