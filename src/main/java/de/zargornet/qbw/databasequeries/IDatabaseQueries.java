package de.zargornet.qbw.databasequeries;

import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for database queries
 */
public interface IDatabaseQueries {
    /**
     * Sets a player's stats
     *
     * @param p     Player
     * @param kills Kills
     * @param death Deaths
     */
    void setStats(Player p, int kills, int death);

    /**
     * Get a player's stats
     *
     * @param p Player
     * @return A list where the first entry defines the kills and the second the deaths
     */
    List<Integer> getStats(Player p);

    /**
     * Sets/adds a world to the database
     *
     * @param qbwWorld QbwWorld
     */
    void setWorld(QbwWorld qbwWorld);

    /**
     * Gets a qbw world
     *
     * @param worldname World name
     * @return {@link QbwWorld}
     */
    QbwWorld getWorld(String worldname);

    /**
     * Removes an qbw world
     *
     * @param worldname Worldname
     */
    void remWorld(String worldname);

    /**
     * Gets all qbw worlds
     *
     * @return List with {@linkplain QbwWorld}
     */
    List<QbwWorld> getWorlds();

    /**
     * Sets/adds an arena to the database
     *
     * @param arena Arena
     */
    void setArena(QbwArena arena);

    /**
     * Removes an arena
     *
     * @param arenaname The name for arena
     */
    void remArena(String arenaname);

    /**
     * Gets an arena
     *
     * @param arenaname Arena name
     * @return {@link QbwArena}
     */
    QbwArena getArena(String arenaname);

    /**
     * Get all arenas
     *
     * @return List with {@linkplain QbwArena}
     */
    List<QbwArena> getArenas();
}
