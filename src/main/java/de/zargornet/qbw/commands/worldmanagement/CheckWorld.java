package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwTeam;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.game.worlds.TeamColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Check if a world is ready for being played
 */
@QbwCommandPath(
        path = "checkworld",
        permission = "qbw.checkworld"
)
public class CheckWorld implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW checkworld <Name>");
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world))
            return;
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, world.getName()))
            return;

        TeamError teamError = null;
        if (!world.getTeams().isEmpty()) {
            for (QbwTeam team : world.getTeams()) {
                if (team.getShopLoc() == null) {
                    teamError = new TeamError(team, TeamErrorCause.NO_SHOP);
                    break;
                }
                if (team.getTeamBedLoc() == null) {
                    teamError = new TeamError(team, TeamErrorCause.NO_BED);
                    break;
                }
                if (team.getTeamBedLoc() == null) {
                    teamError = new TeamError(team, TeamErrorCause.NO_SPAWNPOINT);
                    break;
                }
            }
        } else {
            teamError = new TeamError(new QbwTeam(TeamColor.UNKNOWN, null, null, null), TeamErrorCause.NO_TEAMS);
        }

        String s = "§aStatus of world §e" + world.getName() + " §7(" + (world.isEnabled() ? "Enabled" : "Disabled") + ") \n";
        if (world.getSpawnerList().isEmpty())
            s += "   §7- §eSpawner configured: §c✘ \n";
        else
            s += "   §7- §eSpawner configured: §a✔ \n";

        if (world.getTeams().size() < 2)
            s += "   §7- §eTeams added: §c✘ §7(At least 2 teams needed) \n";
        else
            s += "   §7- §eTeams added: §a✔ \n";

        if (teamError != null)
            s += "   §7- §eTeams well configured: §c✘ §7(§e" + teamError.getTeam().getColor() + " §7=> §e" + teamError.getCause().name() + "§7) \n";
        else
            s += "   §7- §eTeams well configured: §a✔ \n";


        sender.sendMessage(s);
    }

    private class TeamError {
        private QbwTeam team;
        private TeamErrorCause cause;

        TeamError(QbwTeam team, TeamErrorCause cause) {
            this.team = team;
            this.cause = cause;
        }

        public QbwTeam getTeam() {
            return team;
        }

        public TeamErrorCause getCause() {
            return cause;
        }
    }

    private enum TeamErrorCause {
        NO_TEAMS,
        NO_SHOP,
        NO_SPAWNPOINT,
        NO_BED
    }
}
