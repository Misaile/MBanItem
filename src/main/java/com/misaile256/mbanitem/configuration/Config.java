package com.misaile256.mbanitem.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import com.misaile256.mbanitem.MBanItem;

public class Config {
	private FileConfiguration config;
	private Config instance;

	private Config(MBanItem mBanItem) {
		instance = this;
		config = mBanItem.getConfig();
	}

	public static Config loadConfig(MBanItem mBanItem) {
		return new Config(mBanItem);
	}
	public void reload() {
		MBanItem.getInstance().reloadConfig();
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public Config getInstance() {
		return instance;
	}

}
