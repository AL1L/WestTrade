package io.westcraft.trade;

import io.westcraft.trade.command.WestCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WestCraft {

    private static final List<WestCommand> registeredCommands = new ArrayList<>();

    public static boolean registerCommand(WestCommand command) {
        if (!registeredCommands.contains(command))
            registeredCommands.add(command);
        if (WestTrade.getInstance().isEnabled() && !command.isRegistered()) {
            try {
                command.register();
                log().info("Registered command " + command.getName());
                return true;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                log().warning("Failed to register command " + command.getName());
            }
        }
        return false;
    }

    public static boolean unregisterCommand(WestCommand command) {
        if (registeredCommands.contains(command))
            registeredCommands.remove(command);
        if (WestTrade.getInstance().isEnabled() && command.isRegistered()) {
            try {
                command.unregister();
                log().info("Unregistered command " + command.getName());
                return true;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                log().warning("Failed to unregister command " + command.getName());
            }
        }
        return false;
    }

    public static Logger log() {
        return WestTrade.getInstance().getLogger();
    }

    public static List<WestCommand> getRegisteredCommands() {
        return registeredCommands;
    }

    public static JavaPlugin getPlugin(String name) {
        return (JavaPlugin) Bukkit.getPluginManager().getPlugin(name);
    }
}
