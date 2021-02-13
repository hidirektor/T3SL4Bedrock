package me.t3sl4.bedrock;

import com.comphenix.protocol.ProtocolLibrary;
import me.t3sl4.bedrock.commands.BedrockCommand;
import me.t3sl4.bedrock.listeners.PacketListener;
import me.t3sl4.bedrock.util.Item;
import me.t3sl4.bedrock.util.MessageUtil;
import me.t3sl4.bedrock.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class T3SL4Bedrock extends JavaPlugin {
    SettingsManager manager = SettingsManager.getInstance();
    public static Item item = new Item();

    public void onEnable() {
        initialize();
    }

    @Override
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }

    public void initialize() {
        Bukkit.getConsoleSender().sendMessage("   ");
        Bukkit.getConsoleSender().sendMessage("  ____   __   __  _   _   _____   _____   ____    _       _  _   ");
        Bukkit.getConsoleSender().sendMessage(" / ___|  \\ \\ / / | \\ | | |_   _| |___ /  / ___|  | |     | || |  ");
        Bukkit.getConsoleSender().sendMessage(" \\___ \\   \\ V /  |  \\| |   | |     |_ \\  \\___ \\  | |     | || |_ ");
        Bukkit.getConsoleSender().sendMessage("  ___) |   | |   | |\\  |   | |    ___) |  ___) | | |___  |__   _|");
        Bukkit.getConsoleSender().sendMessage(" |____/    |_|   |_| \\_|   |_|   |____/  |____/  |_____|    |_|  ");
        Bukkit.getConsoleSender().sendMessage("    ");
        Bukkit.getConsoleSender().sendMessage("T3SL4 Series: T3SL4Bedrock");

        loadCommands();
        loadListeners();
        manager.setup(this);
        MessageUtil.loadMessages();
        item.loadItem(item);
    }

    private void loadCommands() {
        getCommand("trock").setExecutor((CommandExecutor)new BedrockCommand());
    }

    private void loadListeners() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
    }
}
