package de.zargornet.qbw.game.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.game.QbwPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * A util class for easier managing {@link de.zargornet.qbw.game.arena.QbwArena}
 */
public class QbwArenaUtil {
    /**
     * Broadcast a message to all players which are in a specific arena
     *
     * @param arena   Arena
     * @param message Message
     */
    public static void broadcastToArena(QbwArena arena, String message) {
        if (!arena.getPlayers().isEmpty())
            arena.getPlayers().forEach(qbwPlayer -> qbwPlayer.getPlayer().sendMessage(message));
    }

    /**
     * Gets an QbwPlayer in a unknown arena
     *
     * @param player Player
     * @return QbwPlayer
     */
    public static QbwPlayer getQbwPlayerInArenas(Player player) {
        final QbwPlayer[] qbwPlayer = {null};
        Qbw.getInstance().getArenas().forEach(arena -> {
            Optional<QbwPlayer> op = arena.getPlayers().stream().filter(p -> p.getPlayer() == player).findFirst();
            op.ifPresent(qbwPlayer1 -> qbwPlayer[0] = qbwPlayer1);
        });
        return qbwPlayer[0];
    }

    /**
     * Gets an QbwPlayer in a unknown arena
     *
     * @param playername Playername
     * @return QbwPlayer
     */
    public static QbwPlayer getQbwPlayerInArenas(String playername) {
        final QbwPlayer[] qbwPlayer = {null};
        Qbw.getInstance().getArenas().forEach(arena -> {
            Optional<QbwPlayer> op = arena.getPlayers().stream().filter(p -> p.getPlayer().getName().equalsIgnoreCase(playername)).findFirst();
            op.ifPresent(qbwPlayer1 -> qbwPlayer[0] = qbwPlayer1);
        });
        return qbwPlayer[0];
    }
}
