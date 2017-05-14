package de.zargornet.qbw.utils.database;

import de.zargornet.qbw.Qbw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SQLite class
 */
public class SQLite {
    private Connection con;

    /**
     * Creates the SQLite connection
     */
    public SQLite() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + Qbw.getInstance().getDataFolder() + "/database.db");
        } catch (SQLException e) {
            Qbw.getInstance().getLogger().severe("A unkown error happened while connecting to the sqlite server! Do this plugin have write access to \"/plugins/QuickBedwars\"?");
            Qbw.getInstance().getPluginLoader().disablePlugin(Qbw.getInstance());
        }
    }

    public Connection getCon() {
        return con;
    }
}
