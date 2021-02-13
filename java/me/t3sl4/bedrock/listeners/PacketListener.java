package me.t3sl4.bedrock.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;
import me.t3sl4.bedrock.T3SL4Bedrock;
import me.t3sl4.bedrock.util.Item;
import me.t3sl4.bedrock.util.MessageUtil;
import me.t3sl4.bedrock.util.PacketUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PacketListener extends PacketAdapter {
    private T3SL4Bedrock plugin;
    private Map<Player, Integer> players;

    public PacketListener(T3SL4Bedrock plugin) {
        super(plugin, PacketType.Play.Client.BLOCK_DIG);
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    private void stopDigging(final BlockPosition position, final Player player) {
        if (players.containsKey(player)) {
            Bukkit.getScheduler().cancelTask(players.remove(player));
            new BukkitRunnable() {
                @Override
                public void run() {
                    PacketUtils.broadcastBlockBreakAnimationPacket(position, -1);
                }
            }.runTaskLater(plugin, 1);
        }
    }

    private void breakBlock(Block block, BlockPosition position, Player player) {
        BlockBreakEvent breakEvt = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(breakEvt);
        if (!breakEvt.isCancelled()) {
            block.breakNaturally();
            PacketUtils.broadcastBlockBreakEffectPacket(position, Material.BEDROCK);
        }
    }

    public void onPacketReceiving(PacketEvent evt) {
        final Player player = evt.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        final BlockPosition position = evt.getPacket().getBlockPositionModifier().read(0);
        PlayerDigType type = evt.getPacket().getPlayerDigTypes().read(0);
        switch (type) {
            case ABORT_DESTROY_BLOCK:
            case STOP_DESTROY_BLOCK:
                stopDigging(position, player);
                break;
            case START_DESTROY_BLOCK:
                if (position.getY() < 5 || (player.getWorld().getEnvironment() == World.Environment.NETHER && position.getY() > 123)) {
                    return;
                }
                Location location = position.toLocation(player.getWorld());
                if (!location.getChunk().isLoaded() || location.getBlock().getType() != Material.BEDROCK) {
                    return;
                }
                players.put(player, new BukkitRunnable() {
                    int ticks = 0;

                    @Override
                    public void run() {
                        if (!player.isOnline()) {
                            stopDigging(position, player);
                            return;
                        }
                        if(MessageUtil.ENABLEWORLD) {
                            if(!Item.checkASkyBlock()) {
                                if(!MessageUtil.ENABLED_WORLDS.contains(player.getWorld().getName())) {
                                    player.sendMessage(MessageUtil.WORLDERROR);
                                    return;
                                }
                            }
                        }

                        if(!Item.checkIsland(player)) {
                            player.sendMessage(MessageUtil.ISLANDERROR);
                            return;
                        }
                        ItemStack inHand = player.getItemInHand();
                        if(MessageUtil.PICKAXESYSTEM) {
                            if (!Item.checkPickaxe(inHand.getItemMeta())) {
                                player.sendMessage(MessageUtil.DIGERROR);
                                return;
                            }
                        }
                        if(Item.checkPickaxe(inHand.getItemMeta())) {
                            player.sendMessage(MessageUtil.PICKAXEERROR);
                            return;
                        }
                        if(!MessageUtil.ALLOWED.contains(inHand.getType().toString())) {
                            return;
                        }
                        ticks += 5;
                        int stage;
                        long ticksPerStage = Math.round(MessageUtil.TIME / Math.pow(1.3, inHand.getEnchantmentLevel(Enchantment.DIG_SPEED)) / 9);
                        Block block = position.toLocation(player.getWorld()).getBlock();
                        if (block.getType() == Material.BEDROCK && ticksPerStage != 0 && (stage = (int) (ticks / ticksPerStage)) <= 9) {
                            PacketUtils.broadcastBlockBreakAnimationPacket(position, stage);
                        } else {
                            stopDigging(position, player);
                            if (block.getType() == Material.BEDROCK)
                                breakBlock(block, position, player);
                        }
                    }
                }.runTaskTimer(plugin, 0, 5).getTaskId());
                break;
        }
    }
}
