package io.westcraft.trade.commands;

import io.westcraft.trade.command.CommandSenders;
import io.westcraft.trade.command.WestCmd;
import io.westcraft.trade.command.WestCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TradeCommand extends WestCommand {
    public TradeCommand(JavaPlugin plugin) {
        super(plugin, "trade", "Trade with players", "t");
        setPermission("west.trade");
        setMinArgs(1);
        setMaxArgs(1);
        setAllowedSenders(CommandSenders.PLAYER);
    }

    @Override
    public void onCommand(WestCmd cmd) {
        Player p = cmd.getPlayerSender();
        p.sendMessage(ChatColor.GOLD + "Well... this is awkward... I can't do anything quite yet :P");
    }

    @Override
    public List<String> onTabComplete(WestCmd cmd) {
        return null;
    }
}
