package wpojek555.hydrationplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

public class Hydration implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (!(strings.length >= 1)){
                try {
                    float Amount = PlayerUtility.getPlayerData(player).getThirsty();
                    float result = (Amount / (float) (HydrationPlugin.getInstance().Hydratiion_level_maximum)) * 100;
                    int result2 = Math.round(result);
                    player.sendMessage("Hydration level: " + result2 + "%");
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Something went wrong!");
                }
            } else if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("reload")) {
                    try {
                        HydrationPlugin.getInstance().reloadConfigurationFile();
                        player.sendMessage(ChatColor.GREEN + "Plugin reloaded successfully");
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Something went wrong!");
                    }

                }
            }

        }
        return false;
    }
}
