package me.t3sl4.bedrock.util;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.t3sl4.bedrock.T3SL4Bedrock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Item {
    public static ItemStack pickaxe;
    public static ItemMeta pickaxeMeta;
    private static T3SL4Bedrock trock;

    public static void loadItem(Item item) {
        item.pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        item.pickaxeMeta = item.pickaxe.getItemMeta();
        item.pickaxeMeta.setDisplayName(MessageUtil.ITEMNAME);
        item.pickaxeMeta.setLore(MessageUtil.ITEMLORE);
        item.pickaxeMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        if(MessageUtil.HIDEENCHANT) {
            item.pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.pickaxe.setItemMeta(item.pickaxeMeta);
    }

    public static boolean checkInventory(Player p) {
        int kontrol=0;
        for(ItemStack i : p.getInventory()) {
            if(i == null) {
                kontrol ++;
            }
        }
        if(kontrol != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkPickaxe(ItemMeta meta) {
        if(meta.hasDisplayName()) {
            if(meta.getDisplayName().equalsIgnoreCase(MessageUtil.ITEMNAME)) {
                if(meta.getLore().equals(MessageUtil.ITEMLORE)) {
                    if(MessageUtil.HIDEENCHANT) {
                        if(meta.getEnchants().equals(Item.pickaxe.getEnchantments())) {
                            if(meta.getItemFlags().equals(Item.pickaxe.getItemMeta().getItemFlags())) {
                                return true;
                            }
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
            Integer.parseInt(s);
            isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
        }
        return isValidInteger;
    }

    public static boolean checkIsland(Player pl) {
        if(checkASkyBlock()) {
            if(ASkyBlockAPI.getInstance().playerIsOnIsland(pl)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkASkyBlock() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock");
        if(p != null) {
            return true;
        }
        return false;
    }
}
