package com.misaile256.mbanitem;

import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.misaile256.mbanitem.options.BanAction;
import com.misaile256.mbanitem.options.BanItemType;

public class InventoryListener {
	public static final EventPriority ep = EventPriority.NORMAL;

	private static final Listener listener = new Listener() {

	};

	public static void registerBanItemEventListener() {
		PluginManager pm = MBanItem.getInstance().getServer().getPluginManager();
		// 容器打开
		pm.registerEvent(InventoryOpenEvent.class, listener, ep, ((l, e) -> {
			InventoryOpenEvent i = (InventoryOpenEvent) e;
			Player player = (Player) i.getPlayer();
			if (player.isOp()) {
				return;
			}
			MBanUtil.destroyBanItemInventory(player, i.getView().getTopInventory(), i.getView().getBottomInventory());
		}), MBanItem.getInstance());
		// 背包&容器鼠标点击
		pm.registerEvent(InventoryClickEvent.class, listener, ep, ((l, e) -> {
			InventoryClickEvent i = (InventoryClickEvent) e;
			Player player = (Player) i.getWhoClicked();
			if (player.isOp()) {
				return;
			}
			if (MBanUtil.destroyBanItem(player, i.getSlot(), i.getCurrentItem(), player.getInventory())) {
				i.setCancelled(true);
			}
		}), MBanItem.getInstance());
		// 关闭容器&背包
		pm.registerEvent(InventoryCloseEvent.class, listener, ep, ((l, e) -> {
			InventoryCloseEvent i = (InventoryCloseEvent) e;
			Player player = (Player) i.getPlayer();
			if (player.isOp()) {
				return;
			}
			MBanUtil.destroyBanItemInventory(player, i.getView().getTopInventory(), i.getView().getBottomInventory());
		}), MBanItem.getInstance());
		// 合成
		/*
		 * pm.registerEvent(PrepareItemCraftEvent.class, listener, ep, ((l, e) -> {
		 * PrepareItemCraftEvent i = (PrepareItemCraftEvent) e; ItemStack resultItem =
		 * i.getInventory().getResult(); Player player = (Player)
		 * i.getView().getPlayer(); Map<Integer, List<ItemStack>> imaps =
		 * MBanItem.getInstance().getBlackItemConfig().getBlackItemMap(); if
		 * (MBanUtil.isNullOrEmpty(resultItem)) { return; } else if
		 * (imaps.containsKey(resultItem.getTypeId())) { for (ItemStack item :
		 * imaps.get(resultItem.getTypeId())) { if (item.getDurability() ==
		 * resultItem.getDurability()) { player.sendMessage("qweqweqwe" +
		 * i.getView().getTitle()); } } }
		 * 
		 * }), MBanItem.getInstance());
		 */
		// 玩家手持
		pm.registerEvent(PlayerItemHeldEvent.class, listener, ep, ((l, e) -> {
			PlayerItemHeldEvent i = (PlayerItemHeldEvent) e;
			Player player = i.getPlayer();
			if (player.isOp()) {
				return;
			}
			Map<Integer, List<ItemStack>> imaps = MBanItem.getInstance().getBlackItemConfig().getBlackItemMap();
			ItemStack handItem = player.getInventory().getItem(i.getNewSlot());
			if (MBanUtil.isNullOrEmpty(handItem)) {
				return;
			} else if (imaps.containsKey(handItem.getTypeId())) {
				for (ItemStack item : imaps.get(handItem.getTypeId())) {
					switch (MBanItem.getInstance().getBlackItemConfig().getBlackItemTypeMap()
							.get(handItem.getTypeId())) {
					case NORMAL:
						if (item.getDurability() == handItem.getDurability()) {
							switch (MBanItem.getInstance().getBlackItemConfig().getBlackItemActionMap()
									.get(handItem.getTypeId())) {
							case DESTROY:
								player.getInventory().clear(i.getNewSlot());
								player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c&l违禁物品!系统自动删除."));
								return;
							case HELD:
								i.setCancelled(true);
								player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c此物品不允许手持"));
								return;
							default:
								return;
							}
						}
						return;
					case TOOL:
						switch (MBanItem.getInstance().getBlackItemConfig().getBlackItemActionMap()
								.get(handItem.getTypeId())) {
						case DESTROY:
							player.getInventory().clear(i.getNewSlot());
							player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c&l违禁物品!系统自动删除."));
							return;
						case HELD:
							i.setCancelled(true);
							player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c此物品不允许手持"));
							return;
						default:
							return;
						}
					default:
						return;
					}

				}

			}

		}), MBanItem.getInstance());
		// 玩家手持交互
		pm.registerEvent(PlayerInteractEvent.class, listener, ep, ((l, e) -> {
			PlayerInteractEvent i = (PlayerInteractEvent) e;
			Player player = i.getPlayer();
			if (player.isOp()) {
				return;
			}
			ItemStack handItem = i.getItem();
			Map<Integer, List<ItemStack>> imaps = MBanItem.getInstance().getBlackItemConfig().getBlackItemMap();
			if (MBanUtil.isNullOrEmpty(handItem)) {
				return;
			} else if (imaps.containsKey(handItem.getTypeId())) {
				for (ItemStack item : imaps.get(handItem.getTypeId())) {
					BanAction banAction = MBanItem.getInstance().getBlackItemConfig().getBlackItemActionMap()
							.get(handItem.getTypeId());
					if (i.hasBlock() || i.hasItem()) {
						if (MBanItem.getInstance().getBlackItemConfig().getBlackItemTypeMap()
								.get(handItem.getTypeId()) == BanItemType.NORMAL) {
							if (item.getDurability() == handItem.getDurability()) {
								switch (banAction) {
								case PLACE:
									i.setCancelled(true);
									player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c此物品不允许左或右键"));
									return;
								case DESTROY:
									i.setCancelled(true);
									player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c&l违禁物品!系统自动删除."));
									return;
								default:
									return;
								}
							}
						} else if (MBanItem.getInstance().getBlackItemConfig().getBlackItemTypeMap()
								.get(handItem.getTypeId()) == BanItemType.TOOL) {
							switch (banAction) {
							case PLACE:
								i.setCancelled(true);
								player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c此物品不允许左右键"));
								return;
							case DESTROY:
								i.setCancelled(true);
								MBanUtil.destroyBanItem(player, player.getInventory().getHeldItemSlot(), item,
										player.getInventory());
								player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c&l违禁物品!系统自动删除."));
								return;
							default:
								return;
							}
						}

					}

				}
			}

		}), MBanItem.getInstance());
		pm.registerEvent(PlayerDropItemEvent.class, listener, ep, ((l, e) -> {
			PlayerDropItemEvent i = (PlayerDropItemEvent) e;
			Player player = i.getPlayer();
			if (player.isOp()) {
				return;
			}
			ItemStack dropItem = i.getItemDrop().getItemStack();
			Map<Integer, List<ItemStack>> imaps = MBanItem.getInstance().getBlackItemConfig().getBlackItemMap();
			if (imaps.containsKey(dropItem.getTypeId())) {
				for (ItemStack item : imaps.get(dropItem.getTypeId())) {
					if (item.getDurability() == dropItem.getDurability()) {
						if (MBanItem.getInstance().getBlackItemConfig().getBlackItemActionMap()
								.get(dropItem.getTypeId()) == BanAction.DROP) {
							i.setCancelled(true);
							player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c此物品不允许丢弃"));
							return;
						}
					}
				}
			}
		}), MBanItem.getInstance());
		pm.registerEvent(PlayerPickupItemEvent.class, listener, ep, ((l, e) -> {
			PlayerPickupItemEvent i = (PlayerPickupItemEvent) e;
			Player player = i.getPlayer();
			if (player.isOp()) {
				return;
			}
			ItemStack pickupItem = i.getItem().getItemStack();
			Map<Integer, List<ItemStack>> imaps = MBanItem.getInstance().getBlackItemConfig().getBlackItemMap();
			if (imaps.containsKey(pickupItem.getTypeId())) {
				for (ItemStack item : imaps.get(pickupItem.getTypeId())) {
					if (item.getDurability() == pickupItem.getDurability()) {
						switch (MBanItem.getInstance().getBlackItemConfig().getBlackItemActionMap()
								.get(pickupItem.getTypeId())) {
						case PICKUP:
							i.setCancelled(true);
							player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c此物品不允许捡起"));
							return;
						case DESTROY:
							i.setCancelled(true);
							i.getItem().remove();
							player.sendMessage(MBanUtil.PREFIX + MBanUtil.color("&c&l违禁物品!系统自动删除."));
							return;
						default:
							return;
						}

					}
				}
			}
		}), MBanItem.getInstance());
	}

}
