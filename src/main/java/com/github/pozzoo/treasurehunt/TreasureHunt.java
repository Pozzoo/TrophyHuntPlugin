package com.github.pozzoo.treasurehunt;

import com.github.pozzoo.treasurehunt.commands.ClearFoundCommand;
import com.github.pozzoo.treasurehunt.commands.GiveHeadCommand;
import com.github.pozzoo.treasurehunt.config.HeadConfig;
import com.github.pozzoo.treasurehunt.listeners.BlockListener;
import com.github.pozzoo.treasurehunt.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TreasureHunt extends JavaPlugin {

    private static TreasureHunt instance;
    private TreasureManager treasureManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        HeadConfig.getInstance().load();
        getCommand("tesouro").setExecutor(new GiveHeadCommand());
        getCommand("clear_tesouros").setExecutor(new ClearFoundCommand());
        treasureManager = new TreasureManager();
        treasureManager.loadTreasure();
        new BlockListener(instance);
        new PlayerListener(instance);
    }

    @Override
    public void onDisable() {
        treasureManager.saveLocations();
        // Plugin shutdown logic
    }

    public static TreasureHunt getInstance() {
        return instance;
    }

    public TreasureManager getTreasureManager() {
        return treasureManager;
    }
}
