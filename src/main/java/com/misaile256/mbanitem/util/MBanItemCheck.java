package com.misaile256.mbanitem.util;

import java.util.Map;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.misaile256.mbanitem.MBanItem;
import com.misaile256.mbanitem.configuration.BanItem;
import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.option.ItemType;

public class MBanItemCheck {
	public static boolean isBanItem(ItemStack itemStack) {
		if (!MBanUtil.inull(itemStack)) {
			Map<String, BanItem> map = MBanItem.getInstance().getBlackItemConfig().getMap();
			if (map.containsKey(itemStack.getType().name())) {
				BanItem bi = map.get(itemStack.getType().name());
				if (bi.getType() == ItemType.NORMAL) {
					if (bi.hasAdditional(itemStack.getDurability())) {
						return true;
					} else if (bi.getType() == ItemType.TOOL) {
						return true;
					} else if (bi.getType() == ItemType.NBT) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static Action getBanItemAction(ItemStack itemStack) {
		if (isBanItem(itemStack)) {
			return MBanItem.getInstance().getBlackItemConfig().getMap().get(itemStack.getType().name()).getAction();
		}
		return null;
	}

	public static int destroyBanItem(Inventory... invs) {
		int total = 0;
		for (Inventory inv : invs) {
			if (inv.getTitle().indexOf(MBanUtil.listMenuTitle) != -1) {
				continue;
			}
			for (int i = 0; i < inv.getContents().length; i++) {
				if (getBanItemAction(inv.getContents()[i]) == Action.destroy) {
					inv.clear(i);
					total++;
				}
			}
		}
		return total;
	}
}
