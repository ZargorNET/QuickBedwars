package de.zargornet.qbw;

import com.google.common.reflect.ClassPath;
import de.zargornet.qbw.commands.QbwCommandHandler;
import de.zargornet.qbw.customevent.CustomEventHandler;
import de.zargornet.qbw.databasequeries.IDatabaseQueries;
import de.zargornet.qbw.databasequeries.MongoDBQueries;
import de.zargornet.qbw.databasequeries.SQLQueries;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.utils.MetricsLite;
import de.zargornet.qbw.utils.files.Config;
import de.zargornet.qbw.utils.files.MessagesFile;
import de.zargornet.qbw.utils.packets.INMSUtil;
import de.zargornet.qbw.utils.packets.IPacketReader;
import de.zargornet.qbw.utils.packets.versions.v1_11_R1.NMSUtil;
import de.zargornet.qbw.utils.packets.versions.v1_11_R1.PacketReader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Main class
 */
public class Qbw extends JavaPlugin {
    private static Qbw instance;

    private INMSUtil nmsUtil;

    private IPacketReader packetReader;

    private Config cfg;

    private MessagesFile messagesFile;

    private CustomEventHandler customEventHandler;

    private IDatabaseQueries databaseQueries;

    private final String prefix = "§7[§aQBW§7] §r";

    private List<QbwArena> arenas = new ArrayList<>();


    /***
     * Called when the plugin loads
     */
    @Override
    public void onEnable() {
        instance = this;
        setupNMS();
        cfg = new Config();
        messagesFile = new MessagesFile();

        customEventHandler = new CustomEventHandler();
        this.getCommand("quickbedwars").setExecutor(new QbwCommandHandler());
        registerListeners();
        setupDatabase();

        loadArenas();
        loadMetrics();

    }

    /**
     * Set up the {@link net.minecraft.server} handler
     */
    private void setupNMS() {
        String version = null;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            if (version.equals("v1_11_R1")) {
                nmsUtil = new NMSUtil();
                packetReader = new PacketReader();
            } else {
                Bukkit.getConsoleSender().sendMessage("[QuickBedwars] §cERROR: This version is not supported! Supported versions: §ev1_11_R1");
                Bukkit.getServer().getPluginManager().disablePlugin(this);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    /**
     * Register the listeners for bukkit and custom events
     */
    private void registerListeners() {
        /**
         * BUKKIT
         */
        try {
            ClassPath.from(this.getClassLoader()).getTopLevelClasses(this.getClass().getPackage().getName() + ".listeners.bukkit").forEach(classInfo -> {
                try {
                    Class clazz = Class.forName(classInfo.getName());
                    if (Listener.class.isAssignableFrom(clazz)) {
                        getServer().getPluginManager().registerEvents((Listener) clazz.newInstance(), this);
                    }
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException exe) {
            exe.printStackTrace();
        }
    }

    /**
     * Sets up the database
     */
    private void setupDatabase() {
        switch (this.getConfig().getString("database.type").toLowerCase()) {
            case "mysql":
                databaseQueries = new SQLQueries(SQLQueries.SQLQueryType.MySQL);
                this.getLogger().log(Level.INFO, "Database type is mysql");
                break;
            case "mongodb":
                databaseQueries = new MongoDBQueries();
                this.getLogger().log(Level.INFO, "Database type is mongodb");
                break;
            case "sqlite":
                databaseQueries = new SQLQueries(SQLQueries.SQLQueryType.SQLite);
                this.getLogger().log(Level.INFO, "Database type is sqlite");
                break;
            default:
                databaseQueries = new SQLQueries(SQLQueries.SQLQueryType.SQLite);
                this.getLogger().log(Level.WARNING, "Database not found. Using SQLite...");
                break;
        }
    }

    /**
     * Caches all arenas
     */
    private void loadArenas() {
        arenas = this.getDatabaseQueries().getArenas();
    }

    /**
     * Loads metrics if enabled in config
     */
    private void loadMetrics() {
        if (this.getCfg().getConfiguration().getBoolean("metrics")) {
            try {
                new MetricsLite(this).start();
                this.getLogger().info("Thanks for enabling metrics! :)");
            } catch (IOException ignored) {
            }
            return;
        }
        this.getLogger().info("You disabled metrics :c Please enable it again :)!");
    }

    public static Qbw getInstance() {
        return instance;
    }

    public INMSUtil getNmsUtil() {
        return nmsUtil;
    }

    public IPacketReader getPacketReader() {
        return packetReader;
    }

    public Config getCfg() {
        return cfg;
    }

    public MessagesFile getMessagesFile() {
        return messagesFile;
    }

    public CustomEventHandler getCustomEventHandler() {
        return customEventHandler;
    }

    public IDatabaseQueries getDatabaseQueries() {
        return databaseQueries;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<QbwArena> getArenas() {
        return arenas;
    }
}