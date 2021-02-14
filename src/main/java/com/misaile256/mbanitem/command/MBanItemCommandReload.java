package com.misaile256.mbanitem.command;

import org.bukkit.entity.Player;

import com.misaile256.mbanitem.MBanItem;

public class MBanItemCommandReload extends MBanItemCommand{

	protected MBanItemCommandReload() {
		super("reload");
		
	}

	@Override
	public boolean execute(Player player, String[] args) {
		MBanItem mbi = MBanItem.getInstance();
		mbi.reloadConfig();
		mbi.getBlackItemConfig().reload();
		message(player, "&e÷ÿ‘ÿ≥…π¶!");
		return false;
	}

	
}
