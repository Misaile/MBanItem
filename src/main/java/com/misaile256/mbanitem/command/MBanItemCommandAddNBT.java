package com.misaile256.mbanitem.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.misaile256.mbanitem.MBanItem;
import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.option.ItemType;
import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemCommandAddNBT extends MBanItemCommand {

	protected MBanItemCommandAddNBT() {
		super("addnbt");

	}

	@Override
	public boolean execute(Player player, String[] args) {
		String reason = null;
		if (args.length > 2) {
			reason = args[2];
		} else {
			reason = MBanUtil.Message.reason;
		}
		ItemStack itemStack = player.getItemInHand();
		Action action = null;
		try {
			action = Action.valueOf(args[1].toLowerCase());
			MBanUtil.Message.infoItem(itemStack, player, ItemType.NBT, action, reason);
			if (MBanItem.getInstance().getBlackItemConfig().addBanItem(itemStack, ItemType.NBT, action, reason)) {
				message(player, MBanUtil.Message.addSuccessful);
				return true;
			} else {
				message(player, MBanUtil.Message.addFailure);
			}
		} catch (Exception e) {
			message(player, MBanUtil.Message.addFailure + MBanUtil.color("可能是action输入错误."));
		}
		return false;
	}

}
