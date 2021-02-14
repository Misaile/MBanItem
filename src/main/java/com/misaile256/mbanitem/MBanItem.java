package com.misaile256.mbanitem;

import org.bukkit.plugin.java.JavaPlugin;

import com.misaile256.mbanitem.command.MBanItemCommandTabExecutor;
import com.misaile256.mbanitem.configuration.BlackItemConfig;
import com.misaile256.mbanitem.configuration.Config;

public class MBanItem extends JavaPlugin {
	private static MBanItem instance;
	private Config config;
	private BlackItemConfig blackItemConfig;

	@Override
	public void onLoad() {
		saveDefaultConfig();

	}

	@Override
	public void onEnable() {
		getLogger().info("Author : Misaile");
		getLogger().info("如果有bug之类的,请联系作者邮箱laciam@qq.com");
		if (super.getConfig().getBoolean("settings.enable")) {
			instance = this;
			Config.loadConfig(this);
			getServer().getPluginManager().registerEvents(new MBanItemListener(), this);
			MBanItemCommandTabExecutor mbicte = new MBanItemCommandTabExecutor();
			getServer().getPluginCommand("mbanitem").setExecutor(mbicte);
			getServer().getPluginCommand("mbanitem").setTabCompleter(mbicte);
			blackItemConfig = new BlackItemConfig("black-item");
		}
		getLogger().info("enable = " + super.getConfig().getBoolean("settings.enable"));

	}

	@Override
	public void onDisable() {
	}

	public static MBanItem getInstance() {
		return instance;
	}

	public Config getConfiguration() {
		return config;
	}

	public BlackItemConfig getBlackItemConfig() {
		return blackItemConfig;
	}

}
