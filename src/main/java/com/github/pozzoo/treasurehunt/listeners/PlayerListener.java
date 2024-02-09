package com.github.pozzoo.treasurehunt.listeners;

import com.github.pozzoo.treasurehunt.TreasureHunt;
import com.github.pozzoo.treasurehunt.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements Listener {

    public PlayerListener(TreasureHunt plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ((event.getHand() == EquipmentSlot.HAND)) return;
        if (!(event.hasBlock())) return;
        if (event.getClickedBlock() == null) return;
        if (!(TreasureHunt.getInstance().getTreasureManager().isTreasure(event.getClickedBlock().getLocation()))) return;
        event.setCancelled(true);
        if ((TreasureHunt.getInstance().getTreasureManager().collectTreasure(event.getPlayer(), event.getClickedBlock().getLocation()))) {
            event.getPlayer().sendActionBar(StringUtils.formatString("<red> Você já coletou este tesouro"));
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Firework)) return;

        event.setCancelled(true);

    }

}
