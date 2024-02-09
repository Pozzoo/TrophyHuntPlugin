package com.github.pozzoo.treasurehunt.utils;

import br.com.rabbithole.common.core.items.CustomHeadItem;
import com.github.pozzoo.treasurehunt.config.HeadConfig;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    private static final String url = "http://textures.minecraft.net/texture/";

    public static ItemStack createTreasure() {
        return new CustomHeadItem().createCustomHead(StringUtils.formatString("Tesouro"), url + HeadConfig.getInstance().getHeadId());
    }
}
