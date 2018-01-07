package io.westcraft.trade.commands;

import io.westcraft.trade.command.WestCmd;
import io.westcraft.trade.command.WestCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TradeCommand extends WestCommand {
    public TradeCommand(JavaPlugin plugin) {
        super(plugin, "trade", "Trade with players", "t");
    }

    @Override
    public void onCommand(WestCmd cmd) {

    }

    @Override
    public List<String> onTabComplete(WestCmd cmd) {
        return null;
    }
}
