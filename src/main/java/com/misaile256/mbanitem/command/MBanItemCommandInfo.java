package com.misaile256.mbanitem.command;

import org.bukkit.entity.Player;

import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemCommandInfo extends MBanItemCommand{

	protected MBanItemCommandInfo() {
		super("info");
		
	}

	@Override
	public boolean execute(Player player, String[] args) {
		MBanUtil.Message.infoItem(player.getItemInHand(), player);
		return true;
	}

}
