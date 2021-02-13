package me.t3sl4.bedrock.commands;

import me.t3sl4.bedrock.T3SL4Bedrock;
import me.t3sl4.bedrock.util.Item;
import me.t3sl4.bedrock.util.MessageUtil;
import me.t3sl4.bedrock.util.SettingsManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BedrockCommand implements CommandExecutor {
    SettingsManager manager = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        TextComponent msg = new TextComponent("§e§lAuthor §7|| §e§lYapımcı");
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7Eklenti Yapımcısı:\n   §8§l» §eSYN_T3SL4 \n   §8§l» §7Discord: §eHalil#4439")).create()));

        if(cmd.getName().equalsIgnoreCase("trock")) {
            if(args.length == 0) {
                if(sender instanceof Player) {
                    Player p = (Player) sender;
                    if(p.isOp() || p.hasPermission("t3sl4bedrock.general")) {
                        for(String s: MessageUtil.INFO) {
                            sender.sendMessage(s);
                        }
                        p.spigot().sendMessage(msg);
                    } else {
                        p.sendMessage(MessageUtil.PERMERROR);
                    }
                } else {
                    for(String s: MessageUtil.INFO) {
                        sender.sendMessage(s);
                    }
                }
            } else if(args[0].equalsIgnoreCase("al") || args[0].equalsIgnoreCase("take")) {
                if(MessageUtil.PICKAXESYSTEM) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        if(p.isOp() || p.hasPermission("t3sl4bedrock.take")) {
                            if(args.length < 2 || args.length > 2) {
                                p.sendMessage(MessageUtil.ARGTAKEERROR);
                            } else {
                                if(Item.isInteger(args[1])) {
                                    if(Item.checkInventory(p)) {
                                        int amount = Integer.parseInt(args[1]);
                                        for(int i=0; i<amount; i++) {
                                            p.getInventory().addItem(T3SL4Bedrock.item.pickaxe);
                                        }
                                        p.sendMessage(MessageUtil.PICKAXETAKEN.replaceAll("%adet%", String.valueOf(amount)));
                                    } else {
                                        p.sendMessage(MessageUtil.INVENTORYFULL);
                                    }
                                } else {
                                    p.sendMessage(MessageUtil.NUMBERERROR);
                                }
                            }
                        } else {
                            p.sendMessage(MessageUtil.PERMERROR);
                        }
                    } else {
                        sender.sendMessage(MessageUtil.CONSOLE);
                    }
                } else {
                    sender.sendMessage(MessageUtil.PICKAXESYSTEMERROR);
                }
            } else if(args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("give")) {
                if(MessageUtil.PICKAXESYSTEM) {
                    if(sender.isOp() || !(sender instanceof  Player) || sender.hasPermission("t3sl4bedrock.give")) {
                        if(args.length < 3 || args.length > 3) {
                            sender.sendMessage(MessageUtil.ARGGIVEERROR);
                        } else {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                Player pl = (Player) Bukkit.getPlayer(args[1]);
                                if(Item.isInteger(args[2])) {
                                    int amount = Integer.parseInt(args[2]);
                                    if(Item.checkInventory(pl)) {
                                        for(int i=0; i<amount; i++) {
                                            pl.getInventory().addItem(T3SL4Bedrock.item.pickaxe);
                                        }
                                        sender.sendMessage(MessageUtil.GIVEN.replaceAll("%player%", pl.getName()).replaceAll("%adet%", String.valueOf(amount)));
                                        pl.sendMessage(MessageUtil.PICKAXETAKEN.replaceAll("%adet%", String.valueOf(amount)));
                                    } else {
                                        sender.sendMessage(MessageUtil.INVENTORYISFULL.replaceAll("%player%", pl.getName()));
                                    }
                                } else {
                                    sender.sendMessage(MessageUtil.NUMBERERROR);
                                }
                            } else {
                                sender.sendMessage(MessageUtil.PLAYERNOTFOUND);
                            }
                        }
                    } else {
                        sender.sendMessage(MessageUtil.PERMERROR);
                    }
                } else {
                    sender.sendMessage(MessageUtil.PICKAXESYSTEMERROR);
                }
            } else if(args[0].equalsIgnoreCase("dagit") || args[0].equalsIgnoreCase("distribute")) {
                if(MessageUtil.PICKAXESYSTEM) {
                    if(!(sender instanceof Player) || sender.isOp() || sender.hasPermission("t3sl4bedrock.distribute")) {
                        if(args.length < 2 || args.length > 2) {
                            sender.sendMessage(MessageUtil.ARGDISTRIBUTEERROR);
                        } else {
                            if(Item.isInteger(args[1])) {
                                int amount = Integer.parseInt(args[1]);
                                Set<Player> pls = new HashSet<Player>();
                                for(int i=0; i<amount; i++) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.getInventory().addItem(T3SL4Bedrock.item.pickaxe);
                                        pls.add(p);
                                    }
                                }
                                for(Iterator<Player> it = pls.iterator(); it.hasNext();) {
                                    Player player = it.next();
                                    player.sendMessage(MessageUtil.PICKAXETAKEN.replaceAll("%adet%", String.valueOf(amount)));
                                }
                                sender.sendMessage(MessageUtil.SERVERGIVEN.replaceAll("%adet%", String.valueOf(amount)));
                            } else {
                                sender.sendMessage(MessageUtil.NUMBERERROR);
                            }
                        }
                    } else {
                        sender.sendMessage(MessageUtil.PERMERROR);
                    }
                } else {
                    sender.sendMessage(MessageUtil.PICKAXESYSTEMERROR);
                }
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(sender instanceof Player) {
                    if(sender.isOp() || sender.hasPermission("t3sl4bedrock.reload")) {
                        manager.reloadConfig();
                        MessageUtil.loadMessages();
                        manager.saveConfig();
                        sender.sendMessage(MessageUtil.RELOAD);
                    } else {
                        sender.sendMessage(MessageUtil.PERMERROR);
                    }
                } else {
                    manager.reloadConfig();
                    MessageUtil.loadMessages();
                    manager.saveConfig();
                    sender.sendMessage(MessageUtil.RELOAD);
                }
            }
        }
        return true;
    }
}
