package com.github.pozzoo.treasurehunt.config;

import com.github.pozzoo.treasurehunt.TreasureHunt;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HeadConfig {
    private final static HeadConfig instance = new HeadConfig();
    private File file;
    private YamlConfiguration config;
    private String headId;

    public void load() {
        file = new File(TreasureHunt.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()) {
            TreasureHunt.getInstance().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        headId = config.getString("head_id");
    }

    public String getHeadId() {
        return headId;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HeadConfig getInstance() {
        return instance;
    }
}