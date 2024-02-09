package com.github.pozzoo.treasurehunt.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtils {
    public static void spawnFirework(Location location) {
        org.bukkit.entity.Firework firework = (org.bukkit.entity.Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();

        meta.setPower(2);
        meta.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).flicker(true).build());

        firework.setFireworkMeta(meta);
        firework.detonate();

    }
}
