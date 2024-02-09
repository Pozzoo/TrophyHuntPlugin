package com.github.pozzoo.treasurehunt.listeners;

import com.github.pozzoo.treasurehunt.TreasureHunt;
import com.github.pozzoo.treasurehunt.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {

    public BlockListener(TreasureHunt plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack treasure = ItemUtils.createTreasure();

        if (!(treasure.isSimilar(event.getItemInHand()))) return;

        TreasureHunt.getInstance().getTreasureManager().saveALocation(event.getBlock().getLocation());

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!(TreasureHunt.getInstance().getTreasureManager().isTreasure(event.getBlock().getLocation()))) return;

        if (!(event.getPlayer().isOp())) {
            event.setCancelled(true);
            return;
        }

        TreasureHunt.getInstance().getTreasureManager().removeLocation(event.getBlock().getLocation());
    }
}
