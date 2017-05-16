package de.zargornet.qbw.utils;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.utils.files.MessagesFile;
import org.bukkit.ChatColor;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

/**
 * Here are all messages listed
 *
 * @see MessagesFile
 */
public enum Messages {
    COMMAND_NOT_FOUND,
    NO_PERMISSIONS,
    NOT_IN_A_ARENA,
    ARENA_INVALID_STATE,
    INV_CLOSE_ITEM,
    NO_MAPS_FOUND,
    NO_MAPVOTE,
    WAIT_UNTIL_MAP_VOTE_FINSIHED,
    UNKNOWN_ERROR;


    private String text;

    Messages() {
        try {
            this.text = (ChatColor.translateAlternateColorCodes('&', new String(Qbw.getInstance().getMessagesFile().getResourceBundle().getString(
                    name()).getBytes("ISO-8859-1"), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            Qbw.getInstance().getLogger().log(Level.SEVERE, "Error in messages file!");
        }
    }

    @Override
    public String toString() {
        return text;
    }
}
