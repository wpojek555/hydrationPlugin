package wpojek555.hydrationplugin.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class HydrationCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("hydration")) {
            if (strings.length == 1) {
                completions.add("reload");
            }
        }

        return completions;
    }
}
