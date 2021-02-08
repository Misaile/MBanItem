package com.misaile256.mbanitem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.misaile256.mbanitem.options.BanItemType;

public class MCommand {
	public static boolean addBanItem(Player player, ItemStack itemStack, String action, BanItemType type) {
		if (MBanItem.getInstance().getBlackItemConfig().addSingleBlackItem(itemStack, type, action)) {
			player.sendMessage(MBanUtil.TITLE);
			player.sendMessage(MBanUtil.color("&bid &8- &c" + itemStack.getTypeId() + ":" + itemStack.getDurability()));
			player.sendMessage(MBanUtil.color("&b全名 &8- &c" + itemStack.getType().name()));
			player.sendMessage(MBanUtil.color("&b禁止行为 &8- &c" + action));
			player.sendMessage(MBanUtil.color("&b物品类型 &8- &e" + type.toString().toLowerCase()));
			player.sendMessage(MBanUtil.color("&6添加成功!"));
		} else {
			player.sendMessage(MBanUtil.color(MBanUtil.PREFIX + "&c添加失败!"));
		}
		return true;
	}

	public static boolean addBanItemMenu(Player player) {
		Inventory menu = Bukkit.createInventory(player, 6 * 9, MBanUtil.BANADDMENUTITLE);
		ItemStack is = new ItemStack(Material.STONE_BUTTON);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(MBanUtil.PREFIX + MBanUtil.color("&c&l确定"));
		is.setItemMeta(im);
		menu.setItem(53, is);
		player.openInventory(menu);
		return true;
	}

	public static boolean openBanViewMenu(Player player) {
		player.openInventory(MBanItem.getInstance().getBlackItemConfig().getBanViewMenu()[0]);
		return true;
	}

	public static boolean reload(Player player) {
		MBanItem.getInstance().getBlackItemConfig().reload();
		MBanItem.getInstance().reloadConfig();
		player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&6重载成功!"));
		return true;
	}

	class MenuClickListener implements Listener {
		public int i;

		@EventHandler
		public void click(InventoryClickEvent e) {
			Player player = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equalsIgnoreCase(MBanUtil.BANVIEWMENUTITLE)) {
				e.setCancelled(true);
				Inventory[] invs = MBanItem.getInstance().getBlackItemConfig().getBanViewMenu();
				if (invs.length > 1) {
					if (i < 0) {
						i = 0;
					} else if (i >= invs.length) {
						i = 0;
					} else if (e.getSlot() == 53) {
						player.openInventory(invs[++i]);
					} else if (e.getSlot() == 45) {
						player.openInventory(invs[--i]);
					}
				}
			} else if (e.getInventory().getTitle().equalsIgnoreCase(MBanUtil.BANADDMENUTITLE)) {
				if (e.getSlot() == 53) {
					e.setCancelled(true);
					player.closeInventory();
					player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&b共添加 &c&l" + MBanItem.getInstance()
							.getBlackItemConfig().addMultipleBlackItem(e.getView().getTopInventory()) + " &b个ban物品"));
				}
			}

		}
	}
}
