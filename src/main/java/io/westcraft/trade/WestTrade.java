package io.westcraft.trade;

import io.westcraft.trade.commands.ForceTradeCommand;
import io.westcraft.trade.commands.TradeCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WestTrade extends JavaPlugin {

    public static WestTrade getInstance() {
        return (WestTrade) Bukkit.getPluginManager().getPlugin("WestTrade");
    }

    @Override
    public void onEnable() {
        WestCraft.registerCommand(new TradeCommand(this));
        WestCraft.registerCommand(new ForceTradeCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
