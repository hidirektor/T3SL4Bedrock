package me.t3sl4.bedrock.util;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {
    public static String PREFIX;
    public static List<String> ENABLED_WORLDS = new ArrayList<>();
    public static String ITEMNAME;
    public static List<String> ITEMLORE;
    public static boolean HIDEENCHANT;
    public static String DIGERROR;
    public static long TIME;
    public static List<String> INFO;
    public static String PERMERROR;
    public static String CONSOLE;
    public static String ARGTAKEERROR;
    public static String NUMBERERROR;
    public static String INVENTORYFULL;
    public static String INVENTORYISFULL;
    public static boolean PICKAXESYSTEM;
    public static List<String> ALLOWED;
    public static String WORLDERROR;
    public static boolean ENABLEWORLD;
    public static String PICKAXESYSTEMERROR;
    public static String PICKAXETAKEN;
    public static String ARGGIVEERROR;
    public static String PLAYERNOTFOUND;
    public static String GIVEN;
    public static String ARGDISTRIBUTEERROR;
    public static String SERVERGIVEN;

    static SettingsManager manager = SettingsManager.getInstance();

    public static void loadMessages() {
        PREFIX = colorize(manager.getConfig().getString("Prefix"));
        ENABLED_WORLDS = manager.getConfig().getStringList("Settings.enabled-worlds.list");
        ITEMNAME = colorize(manager.getConfig().getString("Item.name"));
        ITEMLORE = colorizeList(manager.getConfig().getStringList("Item.lore"));
        HIDEENCHANT = manager.getConfig().getBoolean("Item.HideEnchant");
        DIGERROR = PREFIX + colorize(manager.getConfig().getString("Messages.DigError"));
        TIME = manager.getConfig().getLong("Settings.time");
        INFO = colorizeList(manager.getConfig().getStringList("Info"));
        PERMERROR = PREFIX + colorize(manager.getConfig().getString("Messages.PermError"));
        CONSOLE = PREFIX + colorize(manager.getConfig().getString("Messages.Console"));
        ARGTAKEERROR = PREFIX + colorize(manager.getConfig().getString("Messages.ArgTakeError"));
        NUMBERERROR = PREFIX + colorize(manager.getConfig().getString("Messages.NumberError"));
        INVENTORYFULL = PREFIX + colorize(manager.getConfig().getString("Messages.InventoryFull"));
        INVENTORYISFULL = PREFIX + colorize(manager.getConfig().getString("Messages.InventoryIsFull"));
        PICKAXESYSTEM = manager.getConfig().getBoolean("Item.enable");
        ALLOWED = manager.getConfig().getStringList("Item.materials");
        WORLDERROR = PREFIX + colorize(manager.getConfig().getString("Messages.WorldError"));
        ENABLEWORLD = manager.getConfig().getBoolean("Settings.enabled-worlds.enable");
        PICKAXESYSTEMERROR = PREFIX + colorize(manager.getConfig().getString("Messages.PickaxeSystemError"));
        PICKAXETAKEN = PREFIX + colorize(manager.getConfig().getString("Messages.PickaxeTaken"));
        ARGGIVEERROR = PREFIX + colorize(manager.getConfig().getString("Messages.ArgGiveError"));
        PLAYERNOTFOUND = PREFIX + colorize(manager.getConfig().getString("Messages.PlayerNotFound"));
        GIVEN = PREFIX + colorize(manager.getConfig().getString("Messages.Given"));
        ARGDISTRIBUTEERROR = PREFIX + colorize(manager.getConfig().getString("Messages.ArgDistributeError"));
        SERVERGIVEN = PREFIX + colorize(manager.getConfig().getString("Messages.ServerGiven"));
    }

    public static String colorize(String str) {
        return str.replace("&", "§");
    }

    public static List<String> colorizeList(List<String> str) {
        for(int x=0; x<str.size(); x++) {
            str.set(x, str.get(x).replace("&", "§"));
        }
        return str;
    }
}