package de.zargornet.qbw.utils.database;

import de.zargornet.qbw.Qbw;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL class
 */
public class MySQL {
    @Getter
    private Connection con;

    /***
     * Creates the MySQL connection
     */
    public MySQL() {
        try {
            String USERNAME = Qbw.getInstance().getCfg().getConfiguration().get("database.mysql.username").toString();
            String PASSWORD = Qbw.getInstance().getCfg().getConfiguration().get("database.mysql.password").toString();
            String DATABASE = Qbw.getInstance().getCfg().getConfiguration().get("database.mysql.database").toString();
            String HOST = Qbw.getInstance().getCfg().getConfiguration().get("database.mysql.host").toString();
            int PORT = Integer.valueOf(Qbw.getInstance().getCfg().getConfiguration().get("database.mysql.port").toString());
            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true", USERNAME, PASSWORD);
        } catch (SQLException e) {
            Qbw.getInstance().getLogger().severe("A unkown error happened while connecting to the mysql server! Is the server online?");
            Qbw.getInstance().getPluginLoader().disablePlugin(Qbw.getInstance());
        }
    }


}
