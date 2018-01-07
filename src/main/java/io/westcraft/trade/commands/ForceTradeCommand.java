package io.westcraft.trade.commands;

import io.westcraft.trade.command.CommandSenders;
import io.westcraft.trade.command.WestCmd;
import io.westcraft.trade.command.WestCommand;
import io.westcraft.trade.trade.Trade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ForceTradeCommand extends WestCommand {

    public ForceTradeCommand(JavaPlugin plugin) {
        super(plugin, "forcetrade", "Trade with players", "ft", "forcet", "ftrade");
        setPermission("west.force.trade");
        setMinArgs(1);
        setMaxArgs(1);
        setAllowedSenders(CommandSenders.PLAYER);
    }

    @Override
    public void onCommand(WestCmd cmd) {
        Player p = cmd.getPlayerSender();
        Player p2 = Bukkit.getPlayer(cmd.getArg(0));
        if (p2 == null) {
            p.sendMessage(ChatColor.RED + "Player not found");
            return;
        }
        new Trade(p, p2);

    }

    @Override
    public List<String> onTabComplete(WestCmd cmd) {
        return null;
    }
}
