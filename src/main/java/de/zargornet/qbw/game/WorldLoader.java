package de.zargornet.qbw.game;

import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

/**
 * Managing loading and copying {@linkplain de.zargornet.qbw.game.worlds.QbwWorld}
 */
public class WorldLoader {
    private QbwWorld world;
    private World bukkitWorld;
    private final String NAME;

    public WorldLoader(QbwWorld world, QbwArena arena) {
        this.world = world;
        this.bukkitWorld = Bukkit.getWorld(world.getName());
        this.NAME = "qbw_" + world.getName() + "_" + arena.getName();
    }

    public WorldLoader(QbwWorld world, QbwArena arena, boolean loaded) {
        this.world = world;
        this.bukkitWorld = Bukkit.getWorld(world.getName());
        this.NAME = "qbw_" + world.getName() + "_" + arena.getName();
        world.setLoaded(loaded);
    }

    /**
     * Copy a world and load it
     *
     * @throws IOException Exception
     */
    public void load() throws IOException {
        if (world.isLoaded())
            return;
        if (bukkitWorld != null) {
            if (!bukkitWorld.getPlayers().isEmpty())
                bukkitWorld.getPlayers().forEach(player -> player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation()));
            Bukkit.unloadWorld(bukkitWorld, true);
        }
        File original = new File(Bukkit.getWorldContainer() + "/" + world.getName());
        File temp = new File(Bukkit.getWorldContainer() + "/" + NAME);
        FileUtils.copyDirectory(original, temp);
        world.setLoaded(true);
        Bukkit.createWorld(new WorldCreator(NAME));
    }

    /**
     * Unload a world and delete it
     *
     * @throws IOException Exception
     */
    public void unload() throws IOException {
        if (!world.isLoaded())
            return;
        World unloadWorld = Bukkit.getWorld(NAME);
        if (unloadWorld != null) {
            if (!unloadWorld.getPlayers().isEmpty())
                unloadWorld.getPlayers().forEach(player -> player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation()));
            Bukkit.unloadWorld(unloadWorld, false);
            unloadWorld.getWorldFolder().delete();
        } else {
            FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer() + "/" + NAME));
        }
        world.setLoaded(false);
    }
}
