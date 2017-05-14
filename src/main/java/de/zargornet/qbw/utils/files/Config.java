package de.zargornet.qbw.utils.files;

import de.zargornet.qbw.Qbw;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Config file
 */
public class Config {
    FileConfiguration configuration = Qbw.getInstance().getConfig();

    public Config() {
        Qbw.getInstance().saveDefaultConfig();
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
