package com.peteh.stuff;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.peteh.stuff.commands.CustomCommands;
import com.peteh.stuff.cooldowns.CoolDownManager;
import com.peteh.stuff.events.eventthings;
import com.peteh.stuff.items.ItemManagers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class Stuff extends JavaPlugin {

    private ProtocolManager protocolManager;

    private Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }
    private Object getConnection(Player player) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InvocationTargetException {
        Method getHandle = player.getClass().getMethod("getHandle");
        Object nmsPlayer = getHandle.invoke(player);
        Field conField = nmsPlayer.getClass().getField("playerConnection");
        Object con = conField.get(nmsPlayer);
        return con;
    }

    CoolDownManager coolDownManager;

    @Override
    public void onEnable(){
        protocolManager = ProtocolLibrary.getProtocolManager();
        CustomCommands cmdevent = new CustomCommands();
        getServer().getPluginManager().registerEvents(new eventthings(), this);
        getCommand("heall").setExecutor(cmdevent);
        getCommand("brad").setExecutor(cmdevent);
        getCommand("farmtime").setExecutor(cmdevent);
        getCommand("netherblast").setExecutor(cmdevent);
        getCommand("aspect").setExecutor(cmdevent);
        getCommand("flamer").setExecutor(cmdevent);
        getCommand("dash").setExecutor(cmdevent);
        getCommand("aspectTeleport").setExecutor(cmdevent);
        getCommand("terrablade").setExecutor(cmdevent);
        getCommand("blast").setExecutor(cmdevent);
        ItemManagers.init();
        coolDownManager = new CoolDownManager();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "(Petehs plugin is amazing dont question this message)");
    }


    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Petehs plugin is amazing dont question this message");
    }


    public CoolDownManager getCoolDownManager() {
        return coolDownManager;
    }

    private void injectPlayer (Player player) {



    }


}
