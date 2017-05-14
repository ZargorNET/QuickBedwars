package de.zargornet.qbw.databasequeries;

import com.google.gson.Gson;
import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.utils.database.MySQL;
import de.zargornet.qbw.utils.database.SQLite;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The mysql queries for {@link IDatabaseQueries}
 */
public class SQLQueries implements IDatabaseQueries {
    private Connection con;

    /**
     * Defines the connection and create tables
     * Also choose between SQLite and MySQL
     *
     * @param type The type see also {@link SQLQueryType}
     */
    public SQLQueries(SQLQueryType type) {
        try {
            switch (type) {
                case MySQL:
                    con = new MySQL().getCon();
                    getCon().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS qbw_worlds (name TEXT PRIMARY KEY NOT NULL, json TEXT)");
                    getCon().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS qbw_arenas (name TEXT PRIMARY KEY NOT NULL, json TEXT)");
                    break;
                case SQLite:
                    con = new SQLite().getCon();
                    getCon().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS qbw_worlds (name TEXT PRIMARY KEY NOT NULL, json TEXT)");
                    getCon().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS qbw_arenas (name TEXT PRIMARY KEY NOT NULL, json TEXT)");
                    break;
                default:
                    Qbw.getInstance().getLogger().severe("A unknown error happened!");
                    Qbw.getInstance().getPluginLoader().disablePlugin(Qbw.getInstance());
                    break;
            }
        } catch (SQLException exe) {
            exe.printStackTrace();
        }

    }

    @Override
    public void setStats(Player p, int kills, int death) {
    }

    @Override
    public List<Integer> getStats(Player p) {
        return null;
    }

    @Override
    public void setWorld(QbwWorld world) {
        try {
            QbwWorld clonedQbwWorld = world.clone();
            clonedQbwWorld.setLoaded(false);
            if (getWorld(clonedQbwWorld.getName()) == null) {
                PreparedStatement ps = getCon().prepareStatement("INSERT INTO qbw_worlds (name, json) VALUES (?,?)");
                ps.setString(1, clonedQbwWorld.getName().toLowerCase());
                ps.setString(2, new Gson().toJson(clonedQbwWorld));
                ps.executeUpdate();
                ps.close();
            } else {
                PreparedStatement ps = getCon().prepareStatement("UPDATE qbw_worlds SET json = ? WHERE name = ?");
                ps.setString(1, new Gson().toJson(clonedQbwWorld));
                ps.setString(2, clonedQbwWorld.getName().toLowerCase());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException | CloneNotSupportedException exe) {
            exe.printStackTrace();
        }
    }

    @Override
    public QbwWorld getWorld(String worldname) {
        QbwWorld world = null;
        try {
            PreparedStatement ps = getCon().prepareStatement("SELECT * FROM qbw_worlds WHERE name = ?");
            ps.setString(1, worldname.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                world = new Gson().fromJson(rs.getString("json"), QbwWorld.class);
            }
            ps.close();
            rs.close();
        } catch (SQLException exe) {
            exe.printStackTrace();
        }
        return world;
    }

    @Override
    public void remWorld(String worldname) {
        try {
            PreparedStatement ps = getCon().prepareStatement("DELETE FROM qbw_worlds WHERE name = ?");
            ps.setString(1, worldname.toLowerCase());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exe) {
            exe.printStackTrace();
        }
    }

    @Override
    public List<QbwWorld> getWorlds() {
        List<QbwWorld> worlds = new ArrayList<>();
        try {
            PreparedStatement ps = getCon().prepareStatement("SELECT * FROM qbw_worlds");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                QbwWorld world = new Gson().fromJson(rs.getString("json"), QbwWorld.class);
                worlds.add(world);
            }
            ps.close();
            rs.close();
        } catch (SQLException exe) {
            exe.printStackTrace();
        }
        return worlds;
    }

    @Override
    public void setArena(QbwArena arena) {
        try {
            QbwArena clonedArena = arena.clone();
            clonedArena.setWorld(null);
            clonedArena.getPlayers().clear();
            clonedArena.getCounters().clear();
            if (getArena(clonedArena.getName()) == null) {
                PreparedStatement ps = getCon().prepareStatement("INSERT INTO qbw_arenas (name, json) VALUES (?,?)");
                ps.setString(1, clonedArena.getName().toLowerCase());
                ps.setString(2, new Gson().toJson(clonedArena));
                ps.executeUpdate();
                ps.close();
            } else {
                PreparedStatement ps = getCon().prepareStatement("UPDATE qbw_arenas SET json = ? WHERE name = ?");
                ps.setString(1, new Gson().toJson(clonedArena));
                ps.setString(2, clonedArena.getName().toLowerCase());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException | CloneNotSupportedException exe) {
            exe.printStackTrace();
        }
    }

    @Override
    public void remArena(String arenaname) {
        try {
            PreparedStatement ps = getCon().prepareStatement("DELETE FROM qbw_arenas WHERE name = ?");
            ps.setString(1, arenaname.toLowerCase());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exe) {
            exe.printStackTrace();
        }
    }

    @Override
    public QbwArena getArena(String arenaname) {
        QbwArena arena = null;
        try {
            PreparedStatement ps = getCon().prepareStatement("SELECT * FROM qbw_arenas WHERE name = ?");
            ps.setString(1, arenaname.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                arena = new Gson().fromJson(rs.getString("json"), QbwArena.class);
            }
            ps.close();
            rs.close();
        } catch (SQLException exe) {
            exe.printStackTrace();
        }
        return arena;
    }

    @Override
    public List<QbwArena> getArenas() {
        List<QbwArena> arenas = Collections.synchronizedList(new ArrayList<QbwArena>());
        try {
            PreparedStatement ps = getCon().prepareStatement("SELECT * FROM qbw_arenas");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                QbwArena arena = new Gson().fromJson(rs.getString("json"), QbwArena.class);
                arenas.add(arena);
            }
            ps.close();
            rs.close();
        } catch (SQLException exe) {
            exe.printStackTrace();
        }
        return arenas;
    }

    private Connection getCon() {
        return con;
    }

    /**
     * Creates an enum
     * Defines the type of sql, because there are 2 types:
     * SQLite
     * MySQL
     */
    public enum SQLQueryType {
        MySQL,
        SQLite
    }
}
