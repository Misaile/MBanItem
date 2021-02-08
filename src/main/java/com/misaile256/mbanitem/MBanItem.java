package com.misaile256.mbanitem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.misaile256.mbanitem.config.BlackItemConfig;

public class MBanItem extends JavaPlugin {
	private static MBanItem instance;
	private BlackItemConfig blackItemConfig;
	private String dataFolderPath;

	@Override
	public void onLoad() {
		saveDefaultConfig();
		dataFolderPath = getDataFolder().getPath();
	}

	@Override
	public void onEnable() {
		if (getConfig().getBoolean("settings.enable")) {
			instance = this;
			blackItemConfig = BlackItemConfig.loadBlackItemConfig(dataFolderPath + "\\black-item.xml");
			getServer().getPluginCommand("mbanitem").setExecutor(new MBanItemCommand());
			getServer().getPluginCommand("mbanlist").setExecutor(new CommandExecutor() {
				@Override
				public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						MCommand.openBanViewMenu(player);
					}
					return false;
				}
			});
			getServer().getPluginManager().registerEvents(new MCommand().new MenuClickListener(), instance);
			InventoryListener.registerBanItemEventListener();
		}
		getLogger().info("enable = " + getConfig().getBoolean("settings.enable"));

	}

	@Override
	public void onDisable() {
	}

	public static MBanItem getInstance() {
		return instance;
	}

	public BlackItemConfig getBlackItemConfig() {
		return this.blackItemConfig;
	}

	public String getDataFolderPath() {
		return dataFolderPath;
	}
}
