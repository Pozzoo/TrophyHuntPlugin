package com.github.pozzoo.treasurehunt;

import com.github.pozzoo.treasurehunt.utils.FireworkUtils;
import com.github.pozzoo.treasurehunt.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TreasureManager {
    private File locationsFile;
    private File playersFile;
    private List<Location> locations;
    private YamlConfiguration configLocations;
    private YamlConfiguration configPlayers;
    Map<UUID, List<Location>> players;

    public void loadTreasure() {
        this.locations = new ArrayList<>();
        this.players = new HashMap<>();
        locationsFile = new File(TreasureHunt.getInstance().getDataFolder(), "treasure_locations.yml");
        playersFile = new File(TreasureHunt.getInstance().getDataFolder(), "player_list.yml");

        if (!(locationsFile.exists())) {
            TreasureHunt.getInstance().saveResource("treasure_locations.yml", false);
        }

        if (!(playersFile.exists())) {
            TreasureHunt.getInstance().saveResource("player_list.yml", false);
        }

        configLocations = new YamlConfiguration();
        configLocations.options().parseComments(true);

        configPlayers = new YamlConfiguration();
        configPlayers.options().parseComments(true);

        try {
            configLocations.load(locationsFile);
            configPlayers.load(playersFile);
        } catch (InvalidConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }

        int index = 0;

        for (String string : configLocations.getKeys(true)) {
            if (string.equals("tesouros")) continue;


            locations.add(toLocation(configLocations.getString("tesouros." + index)));
            index++;
        }
    }

    public boolean collectTreasure(Player player, Location location) {
        boolean found = false;
        for (String string : configPlayers.getKeys(true)) {
            if (configPlayers.get(string).equals(locationToString(location))) {
                found = true;
                break;
            }
        }

        if (found) return true;

        configPlayers.set(player.getUniqueId() + "." + UUID.randomUUID(), locationToString(location));

        player.sendActionBar(StringUtils.formatString("<gold> Você encontrou um tesouro!!"));
        FireworkUtils.spawnFirework(player.getEyeLocation());

        try {
            configPlayers.save(playersFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void removeLocation(Location location) {
        locations.remove(location);
    }

    public boolean isTreasure(Location location) {
        return locations.contains(location);
    }

    public void saveALocation(Location location) {
        locations.add(location);
    }

    public void saveLocations() {
        int index = 0;

        configLocations.set("tesouros", null);

        for (Location location : locations) {
            configLocations.set("tesouros." + index, locationToString(location));
            index++;
        }

        try {
            configLocations.save(locationsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Location toLocation(String text) {
        assert text != null;
        String[] split = text.split(",");
        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public String locationToString(Location location) {

        return location.getWorld().getName() + "," +
                location.getX() + "," +
                location.getY() + "," +
                location.getZ() + "," +
                location.getYaw() + "," +
                location.getPitch();
    }

    public void clearPlayerCollected() {
        for (String string : configPlayers.getKeys(false)) {
            configPlayers.set(string, null);
        }

        try {
            configPlayers.save(playersFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
