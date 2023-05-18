package wpojek555.hydrationplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

public class GetLevelCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            try {
                float Amount = PlayerUtility.getPlayerData(player).getThirsty();
                float result = (Amount / (float) (HydrationPlugin.getInstance().Hydratiion_level_maximum)) * 100;
                int result2 = Math.round(result);
                player.sendMessage("Level nawodnienia wynosi: " + result2 + "%");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Cos poszło nie tak");
            }
        }
        return false;
    }
}
