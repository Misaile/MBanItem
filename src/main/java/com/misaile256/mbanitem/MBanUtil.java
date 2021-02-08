package com.misaile256.mbanitem;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.misaile256.mbanitem.options.BanAction;
import com.misaile256.mbanitem.options.BanItemType;

public class MBanUtil {

	public static final String color(String text) {
		return text.replace('&', '§');
	}

	public static final String VERSION = "V1.0b";
	public static final String PREFIX = color("&f[&9&lMBanItem&f] ");
	public static final String TITLE = color("&8&m        " + "&f[&9&lMBanItem&dV1.0b&f]" + "&8&m        ");
	public static final String BANADDMENUTITLE = PREFIX + color("&e批量ban");
	public static final String BANVIEWMENUTITLE = PREFIX + color("&cban物品列表");

	public static final BanItemType string2BanItemType(String type) {
		if (type.equalsIgnoreCase(BanItemType.NORMAL.toString())) {
			return BanItemType.NORMAL;
		} else if (type.equalsIgnoreCase(BanItemType.TOOL.toString())) {
			return BanItemType.TOOL;
		} else if (type.equalsIgnoreCase(BanItemType.NBT.toString())) {
			return BanItemType.NBT;
		}
		if (type.equalsIgnoreCase(BanItemType.HASH.toString())) {
			return BanItemType.HASH;
		}
		return null;
	}

	public static final BanAction string2BanAction(String banAction) {
		switch (banAction.toUpperCase()) {
		case "DESTROY":
			return BanAction.DESTROY;
		case "PLACE":
			return BanAction.PLACE;
		case "HELD":
			return BanAction.HELD;
		case "PICKUP":
			return BanAction.PICKUP;
		case "DROP":
			return BanAction.DROP;
		case "CRAFT":
			return BanAction.CRAFT;
		default:
			return null;
		}
	}

	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;
		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}

		return false;
	}

	public static boolean destroyBanItem(Player player, int itemSlot, ItemStack itemStack, Inventory inventory) {
		if (!isNullOrEmpty(itemStack)) {
			Map<Integer, List<ItemStack>> imaps = MBanItem.getInstance().getBlackItemConfig().getBlackItemMap();
			int id = itemStack.getTypeId();
			if (imaps.containsKey(id)) {
				List<ItemStack> list = imaps.get(id);
				Map<Integer, BanItemType> itype = MBanItem.getInstance().getBlackItemConfig().getBlackItemTypeMap();
				for (ItemStack item : list) {
					BanItemType bitype = itype.get(item.getTypeId());
					if (bitype == BanItemType.NORMAL && item.getDurability() == itemStack.getDurability()) {
						switch (MBanItem.getInstance().getBlackItemConfig().getBlackItemActionMap().get(id)) {
						case DESTROY:
							inventory.clear(itemSlot);
							return true;
						default:
							return false;
						}
					} else if (bitype == BanItemType.TOOL) {
						switch (MBanItem.getInstance().getBlackItemConfig().getBlackItemActionMap().get(id)) {
						case DESTROY:
							inventory.clear(itemSlot);
							return true;
						default:
							return false;
						}
					}
				}

			}
		}
		return false;
	}

	public static int destroyBanItemInventory(Player player, Inventory... inventories) {
		if (player.isOp()) {
			return 0;
		}
		int total = 0;
		for (Inventory inventory : inventories) {
			if (inventory.getTitle().equalsIgnoreCase(BANVIEWMENUTITLE)) {
				return 0;
			}
			for (int i = 0; i < inventory.getSize(); i++) {
				ItemStack itemStack = inventory.getItem(i);
				if (destroyBanItem(player, i, itemStack, inventory)) {
					player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c&l违禁物品!系统自动删除."));
					i++;
				}
			}
		}
		return total;
	}
}
