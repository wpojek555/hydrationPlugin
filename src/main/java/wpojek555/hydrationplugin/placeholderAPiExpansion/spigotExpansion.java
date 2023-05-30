package wpojek555.hydrationplugin.placeholderAPiExpansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wpojek555.hydrationplugin.HydrationPlugin;

public class spigotExpansion extends PlaceholderExpansion {

    private HydrationPlugin plugin;

    public spigotExpansion (HydrationPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public @org.jetbrains.annotations.NotNull String getIdentifier() {
        return "hydration";
    }

    @Override
    public @org.jetbrains.annotations.NotNull String getAuthor() {
        return "wpojek555";
    }

    @Override
    public @org.jetbrains.annotations.NotNull String getVersion() {
        return "1.4";
    }


    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("level")) return String.valueOf(HydrationPlugin.getInstance().HydrationLevel.get(player));
        if(params.equalsIgnoreCase("isDrought")) return String.valueOf(plugin.isDroughtActive);
        return null;
    }
}
