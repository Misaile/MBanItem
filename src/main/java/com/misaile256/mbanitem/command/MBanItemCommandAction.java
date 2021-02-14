package com.misaile256.mbanitem.command;

import org.bukkit.entity.Player;

import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemCommandAction extends MBanItemCommand {

	protected MBanItemCommandAction() {
		super("action");
	}

	@Override
	public boolean execute(Player player, String[] args) {
		MBanUtil.Message.action(player);
		return true;
	}

}
