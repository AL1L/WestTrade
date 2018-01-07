package io.westcraft.trade.command;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class WestCmd {

    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;
    private final List<String> argList;
    private boolean rtn = true;

    public WestCmd(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
        this.argList = Arrays.asList(args);
    }

    public boolean getReturn() {
        return rtn;
    }

    public void setReturn(boolean rtn) {
        this.rtn = rtn;
    }

    public CommandSender getSender() {
        return sender;
    }

    public ConsoleCommandSender getConsoleSender() {
        if (sender instanceof ConsoleCommandSender)
            return (ConsoleCommandSender) sender;
        return null;
    }

    public Player getPlayerSender() {
        if (sender instanceof Player)
            return (Player) sender;
        return null;
    }

    public BlockCommandSender getBlockSender() {
        if (sender instanceof BlockCommandSender)
            return (BlockCommandSender) sender;
        return null;
    }

    public Entity getEntitySender() {
        if (sender instanceof Entity)
            return (Entity) sender;
        return null;
    }

    public Command getCommand() {
        return command;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getArgList() {
        return argList;
    }

    public String[] getArgs() {
        if (args == null)
            return new String[0];
        return args;
    }

    public int getArgsLength() {
        return args.length;
    }

    public String getArg(int index) {
        return args[index];
    }

    public String getAllArgs() {
        return getAllArgs(0, " ");
    }

    public String getAllArgs(int offset) {
        return getAllArgs(offset, " ");
    }

    public String getAllArgs(int offset, String separator) {
        if (args.length < offset)
            return "";
        StringBuilder builder = new StringBuilder();
        for (int i = offset; i < args.length; i++) {
            builder.append(args[i].trim());
            if (i != args.length - 1)
                builder.append(separator);
        }
        return builder.toString().trim();
    }
}
