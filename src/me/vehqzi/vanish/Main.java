package me.vehqzi.vanish;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import command.VanishCommand;
import command.Version;
import listeners.VanishListener;

public class Main 
extends JavaPlugin 
{
	
	
public static Main plugin;
private Random random;

public void onEnable() {
	this.registerCommands();
	this.registerListeners();
	Config.getInstance().setupFiles(this);
	saveConfig();
	reloadConfig();
}

public void onDisable() {}

public void registerListeners() {

	Bukkit.getPluginManager().registerEvents(new VanishListener(), this);
}

public void registerCommands() {
	
	getCommand("vanish").setExecutor(new VanishCommand(this));
    getCommand("vanishver").setExecutor(new Version(this));
}

public static Main getPlugin() {
	return plugin;
}

public Random getRandom() {
    return this.random;
}
}

