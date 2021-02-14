package com.misaile256.mbanitem.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.misaile256.mbanitem.MBanItem;
import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.option.ItemType;
import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemCommandAddMenu extends MBanItemCommand {
	public static final String buttonName = MBanUtil.prefix + MBanUtil.color("&e&l确定");
	public static final String splitName = MBanUtil.color("&8&m ");

	protected MBanItemCommandAddMenu() {
		super("addmenu");
		MBanItem.getInstance().getServer().getPluginManager().registerEvent(InventoryClickEvent.class, new Listener() {
		}, EventPriority.NORMAL, ((l, e) -> {
			InventoryClickEvent i = (InventoryClickEvent) e;
			if (i.getInventory().getTitle().equalsIgnoreCase(MBanUtil.addMenuTitle)) {
				if (i.getSlot() > 44 && i.getSlot() < 54) {
					i.setCancelled(true);
					if (i.getSlot() == 53) {
						Player player = (Player) i.getWhoClicked();
						List<String> lore = i.getCurrentItem().getItemMeta().getLore();
						String s1 = lore.get(0);
						Action action = Action.valueOf(s1.substring(s1.lastIndexOf(" ") + 1));
						String reason = lore.get(1).substring(lore.get(1).lastIndexOf(" ") + 1);
						message(player,
								"&e共 &6&l" + MBanItem.getInstance().getBlackItemConfig().addBanItems(
										i.getView().getTopInventory().getContents(), ItemType.NORMAL, action, reason)
										+ " &e添加成功.");
						player.closeInventory();
					}
				}
			}
		}), MBanItem.getInstance());
	}

	@Override
	public boolean execute(Player player, String[] args) {
		Action action = null;
		String reason = null;
		if (args.length > 1) {
			try {
				action = Action.valueOf(args[1]);
			} catch (Exception e) {
				message(player, "&caction错误.");
				return false;
			}

		} else {
			action = Action.destroy;
		}
		if (args.length > 2) {
			reason = args[2];
		} else {
			reason = MBanUtil.Message.reason;
		}
		Inventory menu = Bukkit.createInventory(player, 6 * 9, MBanUtil.addMenuTitle);
		ItemStack button = new ItemStack(Material.STONE_BUTTON);
		ItemMeta im = button.getItemMeta();
		im.setDisplayName(buttonName);
		List<String> lore = new ArrayList<String>();
		lore.add(MBanUtil.color("&6封禁行为 &8-&c " + action));
		lore.add(MBanUtil.color("&6封禁原因 &8-&c " + reason));
		im.setLore(lore);
		button.setItemMeta(im);
		menu.setItem(53, button);
		ItemStack split = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta im2 = split.getItemMeta();
		im2.setDisplayName(splitName);
		split.setItemMeta(im2);
		for (int i = 45; i < 53; i++) {
			menu.setItem(i, split);
		}
		player.openInventory(menu);
		return true;
	}

}
