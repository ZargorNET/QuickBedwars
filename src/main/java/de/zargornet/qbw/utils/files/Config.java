package de.zargornet.qbw.utils.files;

import de.zargornet.qbw.Qbw;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Config file
 */
public class Config {
    @Getter
    FileConfiguration configuration = Qbw.getInstance().getConfig();

    public Config() {
        Qbw.getInstance().saveDefaultConfig();
    }
}
