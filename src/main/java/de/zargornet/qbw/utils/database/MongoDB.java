package de.zargornet.qbw.utils.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.zargornet.qbw.Qbw;
import lombok.Data;
import org.bson.Document;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database: MongoDB
 */
@Data
public class MongoDB {
    private MongoClient client;
    private MongoDatabase db;

    /***
     * Creates the mongodb connection
     */
    public MongoDB() {
        String DATABASE = Qbw.getInstance().getCfg().getConfiguration().getString("database.mongodb.database");
        String HOST = Qbw.getInstance().getCfg().getConfiguration().getString("database.mongodb.host");
        int PORT = Qbw.getInstance().getCfg().getConfiguration().getInt("database.mongodb.port");
        boolean USESLOGIN = Qbw.getInstance().getCfg().getConfiguration().getBoolean("database.mongodb.usesLogin");

        Logger mongoLogger = Logger.getLogger("org.mongodb");
        mongoLogger.setLevel(Level.WARNING);

        if (USESLOGIN) {
            String USERNAME = Qbw.getInstance().getCfg().getConfiguration().getString("database.mongodb.username");
            String PASSWORD = Qbw.getInstance().getCfg().getConfiguration().getString("database.mongodb.password");
            MongoCredential credential = MongoCredential.createCredential(USERNAME, DATABASE, PASSWORD.toCharArray());
            client = new MongoClient(new ServerAddress(HOST, PORT), Collections.singletonList(credential));

        } else {
            client = new MongoClient(HOST, PORT);
        }
        db = client.getDatabase(DATABASE);
        try {
            db.runCommand(new Document("ping", ""));
        } catch (MongoException exe) {
            Qbw.getInstance().getLogger().severe("A unknown error happened while enabling mongodb! Is the password correct? Is the server online?");
            Qbw.getInstance().getPluginLoader().disablePlugin(Qbw.getInstance());
        }
    }
}
