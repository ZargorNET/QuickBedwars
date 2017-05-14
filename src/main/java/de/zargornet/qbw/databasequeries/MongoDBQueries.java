package de.zargornet.qbw.databasequeries;

import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.utils.database.MongoDB;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Zargor on 30.12.2016.
 */
public class MongoDBQueries extends MongoDB implements IDatabaseQueries {
    @Override
    public void setStats(Player p, int kills, int death) {

    }

    @Override
    public List<Integer> getStats(Player p) {
        return null;
    }

    @Override
    public void setWorld(QbwWorld qbwWorld) {

    }

    @Override
    public QbwWorld getWorld(String worldname) {
        return null;
    }

    @Override
    public void remWorld(String worldname) {

    }

    @Override
    public List<QbwWorld> getWorlds() {
        return null;
    }

    @Override
    public void setArena(QbwArena arena) {

    }

    @Override
    public void remArena(String arenaname) {

    }

    @Override
    public QbwArena getArena(String arenaname) {
        return null;
    }

    @Override
    public List<QbwArena> getArenas() {
        return null;
    }
}
