package de.zargornet.qbw.commands.helpcommand;

import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * The help command
 */
@QbwCommandPath(
        path = "help",
        permission = ""
)
public class HelpCommand implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        String s = "\n§7§m----§r §eQuickBedwars Help §7§m---- §r \n";
        if (args.length == 0) {
            s += "§7- §e/QBW help worldconf §7Shows you the help about configuring a world \n";
            s += "§7- §e/QBW help stats §7Show stats commands \n";
            s += "§7- §e/QWB help arenaconf §7Shows you the help about configuring an arena \n";
            s += "§7- §e/QWB help arena §7Shows you the default commands about an arena(ex. join/leave)";
        } else {
            switch (args[0].toLowerCase()) {
                case "worldconf":
                    s += "§7- §e/QBW enableworld <World name> §7Enable a world \n";
                    s += "§7- §e/QBW disableworld <World name> §7Disable a world \n";
                    s += "§7- §e/QBW addworld <World name> §7Adds a world to qbw, so it can be used as an arena \n";
                    s += "§7- §e/QBW remworld <World name> §7Removes a world from qbw \n";
                    s += "§7- §e/QBW addspawner <World name> <Type> §7Adds a spawner to a qbw map on your position \n";
                    s += "§7- §e/QBW remspawner <World name> <Number> §7Removes a spawner from a qbw map \n";
                    s += "§7- §e/QBW addteam <World name> <Team color> §7Adds a team to a qbw map \n";
                    s += "§7- §e/QBW remteam <World name> <Team color> §7Removes a team from a qbw map \n";
                    s += "§7- §e/QBW setteambed <World name> <Team color> §7Sets a team's bed \n";
                    s += "§7- §e/QBW setteamshop <World name> <Team color> §7Sets a team's villager shop \n";
                    s += "§7- §e/QBW setteamspawn <World name> <Team color> §7Sets a team's spawn point \n";
                    s += "§7- §e/QBW listspawner <World name> §7List all spawners \n";
                    s += "§7- §e/QBW listteams <World name> §7List all teams";
                    break;
                case "stats":
                    s += "§7- §e/QBW stats §7Show your stats \n";
                    s += "§7- §e/QBW resetstats <Player> §7Reset a player's stats";
                    break;
                case "arenaconf":
                    s += "§7- §e/QBW addarena <Unique name> §7Adds an arena \n";
                    s += "§7- §e/QBW remarena <Name> §7Removes an arena \n";
                    s += "§7- §e/QBW setlobbyspawn <Name> §7Sets an arena's lobby spawn point \n";
                    s += "§7- §e/QBW setplayersperteam <Name> <Number> §7Sets the maximum player limit for teams in an arena \n";
                    s += "§7- §e/QBW setteamcount <Name> <Number> §7Sets the team count";
                    break;
                case "arena":
                    s += "§7- §e/QBW join <Name> §7Join an arena \n";
                    s += "§7- §e/QBW leave §7Leave an arena \n";
                    s += "§7- §e/QBW forcemap §7Forces a map \n";
                    s += "§7- §e/QBW forcestart §7Force your arena to start \n";
                    s += "§7- §e/QBW disablearena <Name> §7Forces an arena to stop and disable it \n";
                    s += "§7- §e/QBW enablearena <Name> §7Start a stopped arena again \n";
                    s += "§7- §e/QBW listarenas §7List all arenas";
                    break;
                default:
                    s += "§cNot found. Please write: §e/QBW help";
                    break;
            }
        }
        sender.sendMessage(s);
    }
}
