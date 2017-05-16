package de.zargornet.qbw.customevent.events.player;

import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.worlds.QbwTeam;
import lombok.Data;
import org.bukkit.entity.Player;

/**
 * Fired when a player selects a team
 */
@Data
public class PlayerTeamSelectedEvent extends PlayerCustomEvent {
    private final QbwTeam team;
    private final QbwArena arena;

    public PlayerTeamSelectedEvent(Player player, QbwTeam team, QbwArena arena) {
        super(player);
        this.team = team;
        this.arena = arena;
    }
}
