package com.misaile256.mbanitem.command;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.misaile256.mbanitem.MBanItem;
import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemCommandList extends MBanItemCommand {

	public static int als = 0;

	protected MBanItemCommandList() {
		super("list");
		MBanItem.getInstance().getServer().getPluginManager().registerEvent(InventoryClickEvent.class, new Listener() {
		}, EventPriority.NORMAL, ((l, e) -> {
			InventoryClickEvent i = (InventoryClickEvent) e;
			if (i.getInventory().getTitle().indexOf(MBanUtil.listMenuTitle) != -1) {
				i.setCancelled(true);
				if (MBanItem.getInstance().getBlackItemConfig().getMenu().length > 1) {
					Player player = (Player) i.getWhoClicked();
					Inventory[] invs = MBanItem.getInstance().getBlackItemConfig().getMenu();
					try {
						if (i.getSlot() == 53) {
							player.openInventory(invs[++als]);
						} else if (i.getSlot() == 45) {
							player.openInventory(invs[--als]);
						}
					} catch (ArrayIndexOutOfBoundsException e2) {
						als = 0;
						player.openInventory(invs[als]);
					}
				}
			}
		}), MBanItem.getInstance());
//		MBanItem.getInstance().getServer().getPluginManager().registerEvent(InventoryCloseEvent.class, new Listener() {
//		}, EventPriority.NORMAL, ((l, e) -> {
//		}), MBanItem.getInstance());
	}

	@Override
	public boolean execute(Player player, String[] args) {
		als = 0;
		player.openInventory(MBanItem.getInstance().getBlackItemConfig().getMenu()[als]);
		return true;
	}

}
