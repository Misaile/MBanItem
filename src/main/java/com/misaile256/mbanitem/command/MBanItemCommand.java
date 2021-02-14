package com.misaile256.mbanitem.command;

import org.bukkit.entity.Player;

import com.misaile256.mbanitem.util.MBanUtil;

public abstract class MBanItemCommand {
	private String commandName;

	protected MBanItemCommand(String commandName) {
		this.commandName = commandName;
	}

	public abstract boolean execute(Player player, String[] args);

	public String getCommandName() {
		return this.commandName;
	}

	public void message(Player player, String text) {
		player.sendMessage(MBanUtil.prefix + MBanUtil.color(text));
	}
}
