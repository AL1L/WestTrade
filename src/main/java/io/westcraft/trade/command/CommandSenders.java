package io.westcraft.trade.command;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public enum CommandSenders {
    PLAYER, CONSOLE, BLOCK, ENTITY, OTHER;

    public static CommandSenders getSender(CommandSender o) {
        if (o instanceof Player)
            return PLAYER;
        if (o instanceof ConsoleCommandSender)
            return CONSOLE;
        if (o instanceof BlockCommandSender)
            return BLOCK;
        if (o instanceof Entity)
            return ENTITY;
        return OTHER;
    }

    public static boolean compare(CommandSenders type, CommandSender sender) {
        CommandSenders realType = getSender(sender);
        return type.equals(realType) || (realType.equals(PLAYER) && type.equals(ENTITY));
    }

    public static boolean compare(List<CommandSenders> senders, CommandSender sender) {
        if (senders != null)
            for (CommandSenders type : senders)
                if (compare(type, sender))
                    return true;
        return false;
    }
}
