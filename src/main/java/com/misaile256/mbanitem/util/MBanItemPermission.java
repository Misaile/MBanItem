package com.misaile256.mbanitem.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MBanItemPermission {
	public static final String permission = "mbanitem.allow.";
	public static final String destroy = permission + "destroy.";
	public static final String interact = permission + "interact.";
	public static final String held = permission + "held.";
	public static final String pickup = permission + "pickup.";
	public static final String drop = permission + "drop.";
	public static final String break0 = permission + "break0.";

	public static boolean hasHeldPermission(Player player, ItemStack itemStack) {
		String name = itemStack.getType().name();
		if (player.hasPermission(held + name)) {

		}
		return true;
	}

	public static boolean hasDestroyPermission(Player player, ItemStack itemStack) {
		return true;
	}
}
