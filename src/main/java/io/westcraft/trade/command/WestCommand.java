package io.westcraft.trade.command;

import io.westcraft.trade.WestCraft;
import io.westcraft.trade.WestTrade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class WestCommand extends Command {

    private static final String ERROR_MISSING_ARGS = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " Missing args.";
    private static final String ERROR_TOO_MANY_ARGS = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " Too many args.";
    private static final String ERROR_INVALID_SENDER = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " You can not run this command.";
    private static final String ERROR_NO_PERMISSION = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " No permission.";

    final JavaPlugin parentPlugin;

    private int minArgs = 0;
    private int maxArgs = -1;
    private List<CommandSenders> allowedSenders = Arrays.asList(CommandSenders.CONSOLE, CommandSenders.ENTITY,
            CommandSenders.BLOCK, CommandSenders.OTHER, CommandSenders.PLAYER);
    private boolean runAsync = false;
    private boolean registered = false;

    public WestCommand(JavaPlugin plugin, String name) {
        this(plugin, name, "");
    }

    public WestCommand(JavaPlugin plugin, String name, String description, String... aliases) {
        super(name, description, "", Arrays.asList(aliases));
        this.parentPlugin = plugin;
        WestCraft.registerCommand(this);
    }

    private static Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    public void register() throws NoSuchFieldException, IllegalAccessException {
        if (getName() == null || getDescription() == null || registered)
            return;
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

        bukkitCommandMap.setAccessible(true);
        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        commandMap.register(WestTrade.getInstance().getDescription().getName(), this);
        registered = true;
    }

    @SuppressWarnings("unchecked")
    public void unregister() throws NoSuchFieldException, IllegalAccessException {
        Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
        SimpleCommandMap commandMap = (SimpleCommandMap) result;
        Object map = getPrivateField(commandMap, "knownCommands");
        HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
        knownCommands.remove(getName());
        for (String alias : getAliases()) {
            if (knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(this.getName())) {
                knownCommands.remove(alias);
            }
        }
        onUnregister();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        WestCmd cmd = new WestCmd(sender, this, label, args);

        if (!this.parentPlugin.isEnabled()) {
            return false;
        } else if (!this.testPermissionSilent(sender)) {
            onMissingPermission(cmd);
            return true;
        }

        CommandSenders senderType = CommandSenders.getSender(sender);
        BukkitScheduler scheduler = Bukkit.getScheduler();

        if (!CommandSenders.compare(allowedSenders, sender)) {
            if (runAsync) {
                scheduler.runTaskAsynchronously(parentPlugin, () -> onInvalidSender(cmd, senderType));
            } else {
                onInvalidSender(cmd, senderType);
            }
        } else if (args.length > maxArgs && maxArgs >= 0) {
            if (runAsync) {
                scheduler.runTaskAsynchronously(parentPlugin, () -> onTooManyArgs(cmd, args.length));
            } else {
                onTooManyArgs(cmd, args.length);
            }
        } else if (args.length < minArgs) {
            if (runAsync) {
                scheduler.runTaskAsynchronously(parentPlugin, () -> onMissingArgs(cmd, args.length));
            } else {
                onMissingArgs(cmd, args.length);
            }
        } else {
            if (runAsync) {
                scheduler.runTaskAsynchronously(parentPlugin, () -> onCommand(cmd));
            } else {
                onCommand(cmd);
            }
        }
        return cmd.getReturn();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> altRtn = super.tabComplete(sender, alias, args);
        WestCmd cmd = new WestCmd(sender, this, null, args);
        if (sender instanceof Player && !allowedSenders.contains(CommandSenders.PLAYER)) {
            return new ArrayList<>();
        } else if (sender instanceof ConsoleCommandSender && !allowedSenders.contains(CommandSenders.CONSOLE)) {
            return new ArrayList<>();
        } else if (sender instanceof BlockCommandSender && !allowedSenders.contains(CommandSenders.BLOCK)) {
            return new ArrayList<>();
        } else {
            List<String> rtn = onTabComplete(cmd);
            if (rtn != null)
                return rtn;
        }
        return altRtn;
    }

    public JavaPlugin getParentPlugin() {
        return parentPlugin;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public void setMaxArgs(int maxArgs) {
        this.maxArgs = maxArgs;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public boolean isRunAsync() {
        return runAsync;
    }

    public void setRunAsync(boolean runAsync) {
        this.runAsync = runAsync;
    }

    public void setAllowedSenders(List<CommandSenders> allowedSenders) {
        this.allowedSenders = allowedSenders;
    }

    public void setAllowedSenders(CommandSenders... allowedSenders) {
        this.allowedSenders = Arrays.asList(allowedSenders);
    }

    public abstract void onCommand(WestCmd cmd);

    public abstract List<String> onTabComplete(WestCmd cmd);

    public void onMissingArgs(WestCmd cmd, int count) {
        cmd.getSender().sendMessage(ERROR_MISSING_ARGS);
    }

    public void onTooManyArgs(WestCmd cmd, int count) {
        cmd.getSender().sendMessage(ERROR_TOO_MANY_ARGS);
    }

    public void onInvalidSender(WestCmd cmd, CommandSenders got) {
        cmd.getSender().sendMessage(ERROR_INVALID_SENDER);
    }

    public void onMissingPermission(WestCmd cmd) {
        cmd.getSender().sendMessage(ERROR_NO_PERMISSION);
    }

    public void onUnregister() {
    }
}
