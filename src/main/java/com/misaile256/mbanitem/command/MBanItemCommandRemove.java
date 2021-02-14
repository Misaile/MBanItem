package com.misaile256.mbanitem.command;

import org.bukkit.entity.Player;

import com.misaile256.mbanitem.MBanItem;
import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemCommandRemove extends MBanItemCommand {

	protected MBanItemCommandRemove() {
		super("remove");
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if (MBanItem.getInstance().getBlackItemConfig().removeBanItem(player.getItemInHand())) {
			message(player, MBanUtil.Message.removeSuccessful);
			return true;
		} else {
			message(player, MBanUtil.Message.removeFailure);
			return false;
		}
	}

}
