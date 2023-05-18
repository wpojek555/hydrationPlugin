package wpojek555.hydrationplugin.Commands;

import jdk.internal.joptsimple.internal.Strings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wpojek555.hydrationplugin.HydrationPlugin;
import wpojek555.hydrationplugin.data.PlayerData;
import wpojek555.hydrationplugin.utilities.PlayerUtility;

public class SetThirstyCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        double amount;
        Player target;
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (player.hasPermission("hydrationPlugin.setHydration")) {
                if (strings.length == 1) {
                    player.sendMessage(ChatColor.RED + "You have to specify amount");
                } else if (strings.length == 2) {
                    try {
                        target = Bukkit.getPlayer(strings[0]);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Invalid Player");
                        return false;
                    }
                    try {
                        amount = Double.parseDouble(strings[1]);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Invalid Percent");
                        return false;
                    }

                    if (!target.isOnline()) {
                        player.sendMessage(ChatColor.RED + "Invalid Player");
                        return false;
                    }
                    if (amount > 0 && amount < 100) {
                        PlayerData memory = new PlayerData();
                        double amount2 = (amount / 100) * HydrationPlugin.getInstance().Hydratiion_level_maximum;

                        int amount3 = (Integer.parseInt(Double.toString(amount2)));
                        memory.setThirsty(amount3);
                        PlayerUtility.setPlayerData(target, memory);
                        player.sendMessage(ChatColor.GREEN + "Hydration level set for " + target.getName() + "to" + ChatColor.WHITE + "" + ChatColor.BOLD + amount + "%");
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid Percent");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You don't have permission to do this.");
            }
        }



        return false;
    }
}
