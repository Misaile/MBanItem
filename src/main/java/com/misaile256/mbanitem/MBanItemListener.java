package com.misaile256.mbanitem;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.util.MBanItemCheck;
import com.misaile256.mbanitem.util.MBanItemPermission;
import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemListener implements Listener {
	public static final String alert = MBanUtil.prefix + MBanUtil.color("&c警告!系统搜查出封禁物品,共 &4&l");

	@EventHandler
	public void inventoryOpen(InventoryOpenEvent e) {
		Player player = (Player) e.getPlayer();
		if (player.isOp()) {
			return;
		} else if (!player.hasPermission(MBanItemPermission.destroy + "*")) {
			int b = MBanItemCheck.destroyBanItem(player.getInventory(), e.getInventory());
			if (b > 0) {
				player.sendMessage(alert + b);
			}
		}
	}

	@EventHandler
	public void inventoryClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if (player.isOp()) {
			return;
		} else if (!player.hasPermission(MBanItemPermission.destroy + "*")) {
			int b = MBanItemCheck.destroyBanItem(player.getInventory(), e.getInventory());
			if (b > 0) {
				player.sendMessage(alert + b);
			}
		}
	}

	@EventHandler
	public void heldItem(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		if (player.isOp()) {
			return;
		}
		ItemStack is = player.getInventory().getItem(e.getNewSlot());
		Action action = MBanItemCheck.getBanItemAction(is);
		if (action == Action.held) {
			e.setCancelled(true);
			player.sendMessage(MBanUtil.Message.noHeld);
		} else if (action == Action.destroy) {
			player.getInventory().clear(e.getNewSlot());
			player.sendMessage(MBanUtil.Message.checkBanItem);
		}
	}

	@EventHandler
	public void interactItem(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (player.isOp()) {
			return;
		}
		ItemStack is = e.getItem();
		Action action = MBanItemCheck.getBanItemAction(is);
		if (action == Action.interact) {
			e.setCancelled(true);
			player.sendMessage(MBanUtil.Message.noInteract);
		} else if (action == Action.destroy) {
			e.setCancelled(true);
			player.getInventory().remove(is);
			player.sendMessage(MBanUtil.Message.checkBanItem);
		}
	}

	@EventHandler
	public void pickupItem(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		if (player.isOp()) {
			return;
		}
		Item is = e.getItem();
		Action action = MBanItemCheck.getBanItemAction(is.getItemStack());
		if (action == Action.pickup) {
			e.setCancelled(true);
			player.sendMessage(MBanUtil.Message.noPickup);
		} else if (action == Action.destroy) {
			e.setCancelled(true);
			is.remove();
			player.sendMessage(MBanUtil.Message.pickupBanItem);
		}
	}

	@EventHandler
	public void craftItem(CraftItemEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (player.isOp()) {
			return;
		}
		ItemStack is = e.getCurrentItem();
		Action action = MBanItemCheck.getBanItemAction(is);
		if (action == Action.craft || action == Action.destroy) {
			e.setCancelled(true);
			player.sendMessage(MBanUtil.Message.noCraft);
		}

	}

	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (player.isOp()) {
			return;
		}
		Item is = e.getItemDrop();
		Action action = MBanItemCheck.getBanItemAction(is.getItemStack());
		if (action == Action.drop) {
			e.setCancelled(true);
			player.sendMessage(MBanUtil.Message.noDrop);
		}
	}

}
