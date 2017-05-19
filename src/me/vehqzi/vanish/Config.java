package me.vehqzi.vanish;

import java.io.File;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

	static Config instance = new Config();
	
	public static Config getInstance() {
		return instance;
	}
	
	Plugin p;
	
	FileConfiguration lang, config, storage;
	File langFile, configFile, storageFile;
	
	
	public void setupFiles(Plugin p) {
		configFile = new File(p.getDataFolder(), "config.yml");
		langFile = new File(p.getDataFolder(), "vehqzi.yml");
		storageFile = new File(p.getDataFolder(), "storage.yml");
		
		config = p.getConfig();
		config.options().copyDefaults(true);
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		
		if (!langFile.exists()) {
			try {
				langFile.createNewFile();
			}
			catch (IOException e) { 
				
			}
		}
		
		lang = YamlConfiguration.loadConfiguration(langFile);
		storage = YamlConfiguration.loadConfiguration(storageFile);
		saveFiles();
	}
	
	public FileConfiguration getConfigFile() {
		return config;
	}
	
	public FileConfiguration getMannyFile() {
		return lang;
	}
	
	public FileConfiguration getStorageFile() {
		return storage;
	}
	
	public void saveFiles() {
		try {
			config.save(configFile);
		} catch (IOException e) { 
			
		}
		
		try {
			lang.save(langFile);
		} catch (IOException e) { 
			
		}
		
		try {
			storage.save(storageFile);
		} catch (IOException e) { 
			
		}
		
	}
	
	public void reloadFiles() {
		config = YamlConfiguration.loadConfiguration(configFile);
		lang = YamlConfiguration.loadConfiguration(langFile);
		storage = YamlConfiguration.loadConfiguration(storageFile);
	}
	
}