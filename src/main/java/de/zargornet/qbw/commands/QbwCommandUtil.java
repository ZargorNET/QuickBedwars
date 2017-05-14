package de.zargornet.qbw.commands;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaState;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.game.worlds.TeamColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

/**
 * Have some useful features for the commands
 */
public class QbwCommandUtil {
    /**
     * Checks if a team was added to a QBWWorld
     *
     * @param sender CommandSender
     * @param world  QbwWorld
     * @param color  TeamColor
     * @return Returns false when team wasn't added
     */
    public static boolean isTeamAdded(CommandSender sender, QbwWorld world, String color) {
        if (world.getTeams().stream().noneMatch(qbwTeam -> qbwTeam.getColor() == TeamColor.valueOf(color.toUpperCase()))) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cTeam wasn't added");
            return false;
        }
        return true;
    }

    /**
     * Checks if a world is currently used by an arena
     *
     * @param sender CommandSender
     * @param world  The world name
     * @return Returns false when world is used
     */
    public static boolean checkIfWorldIsNotUsed(CommandSender sender, String world) {
        if (Qbw.getInstance().getArenas().stream().anyMatch(arena -> arena.getWorld() != null && arena.getWorld().getName().equalsIgnoreCase(world))) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cWorld is still in use by the following arena(s): ");
            Qbw.getInstance().getArenas().stream().filter(arena -> arena.getWorld().getName().equalsIgnoreCase(world)).forEach(arena -> sender.sendMessage("§6" + arena.getName()));
            return false;
        }
        return true;
    }

    /**
     * Gets an arena by name
     *
     * @param sender CommandSender
     * @param name   Arena name
     * @return Returns "null" when arena doesn't exist!
     */
    public static QbwArena getArenaIfNotNull(CommandSender sender, String name) {
        synchronized (Qbw.getInstance().getArenas()) {
            Optional<QbwArena> arenaOptional = Qbw.getInstance().getArenas().stream().filter(arena -> arena.getName().equalsIgnoreCase(name)).findFirst();
            if (!arenaOptional.isPresent()) {
                sender.sendMessage(Qbw.getInstance().getPrefix() + "§cArena wasn't added!");
                return null;
            }
            return arenaOptional.get();
        }
    }

    /**
     * Checks if an arena's state is stopped
     *
     * @param sender CommandSender
     * @param arena  Arena name
     * @return Returns false when arena isn't stopped
     */
    public static boolean isArenaStopped(CommandSender sender, QbwArena arena) {
        if (arena.getState() != QbwArenaState.STOPPED) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cArena is currently running! Stop it with: §e/QBW disablearena " + arena.getName());
            return false;
        }
        return true;
    }

    /**
     * Checks if a TeamColor is valid
     *
     * @param sender CommandSender
     * @param color  TeamColor as string
     * @return Returns null if color isn't valid
     */
    public static TeamColor getTeamColorIfValid(CommandSender sender, String color) {
        Optional<TeamColor> colorOptional = Arrays.stream(TeamColor.values()).filter(qbwColor -> qbwColor.name().equals(color.toUpperCase())).findFirst();
        if (!colorOptional.isPresent()) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cTeam color is unknown!");
            sender.sendMessage("Valid types:");
            Arrays.stream(TeamColor.values()).forEach(qbwColor -> sender.sendMessage(qbwColor.name()));
            return null;
        }
        return colorOptional.get();
    }

    /**
     * Checks if a world was added to the QBW system
     *
     * @param sender CommandSender
     * @param world  The QBWWorld which should be checked
     * @return Returns false when the world wasn't added
     */
    public static boolean worldAdded(CommandSender sender, QbwWorld world) {
        if (world == null) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cWorld wasn't added");
            return false;
        }
        return true;
    }

    /**
     * Checks if a {@link CommandSender} is a player
     *
     * @param sender CommandSender
     * @return Returns false when sender isn't a player
     */
    public static boolean senderIsPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cCommand can only be used by players!");
            return false;
        }
        return true;
    }
}
