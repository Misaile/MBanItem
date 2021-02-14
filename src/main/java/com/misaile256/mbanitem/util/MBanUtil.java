package com.misaile256.mbanitem.util;

import java.util.Collection;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.option.ItemType;

public class MBanUtil {
	public static final String encoding = "utf-8";
	public static final String version = "v2.0a-1.7.10+";
	public static final String prefix = color("&f[&9&lMBanItem&f] ");
	public static final String splitline = prefix + color("&8&m                            ");
	public static final String addMenuTitle = prefix + color("&c批量添加封禁物品");
	public static final String listMenuTitle = color("[&9&lMBanItem&f] &c封禁物品表");

	public static String color(String text) {
		char[] a = text.toCharArray();
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(a[i + 1]) > -1) {
				a[i] = '§';
				a[i + 1] = Character.toLowerCase(a[i + 1]);
			}
		}
		return new String(a);
	}

	public static boolean inull(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;
		if (obj instanceof Collection)
			return ((Collection<?>) obj).isEmpty();
		if (obj instanceof Map)
			return ((Map<?, ?>) obj).isEmpty();
		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!inull(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	public static class Message {
		public static final String reason = color("&c此物品可能会造成卡服或者存在刷物品的bug");
		public static final String destroy = color("&4不可获取");
		public static final String craft = color("&4不可合成");
		public static final String interact = color("&4不可使用/放置");
		public static final String drop = color("&4不可丢弃");
		public static final String held = color("&不可手持");
		public static final String pickup = color("&4不可捡起");
		public static final String break0 = color("&4不可破坏");
		public static final String checkBanItem = prefix + color("&4系统搜查出封禁物品,自动清除");
		public static final String pickupBanItem = prefix + color("&c系统检测到地上有封禁物品,自动清除");

		public static final String noDrop = prefix + color("&c此物品不允许丢弃");
		public static final String noCraft = prefix + color("&c封禁物品不允许合成!");
		public static final String noPickup = prefix + color("&c地上物品不允许捡起");
		public static final String noHeld = prefix + color("&c此物品不允许手持");
		public static final String noInteract = prefix + color("&c手中物品使用/放置");

		public static final String removeSuccessful = color("&a移除成功!");
		public static final String removeFailure = color("&c移除失败");
		public static final String addSuccessful = color("&a添加成功!");
		public static final String addFailure = color("&c添加失败.");
		public static final String addMenu = color("&a共 &c&l{count} &a添加成功.");
		public static final String reload = color("&a重载成功!");

		public static void infoItem(ItemStack item, CommandSender sender, ItemType type, Action action, String reason) {
			sender.sendMessage(splitline);
			String d = " &8&l-&c ";
			sender.sendMessage(color("&b物品ID" + d + item.getTypeId() + ":" + item.getDurability()));
			sender.sendMessage(color("&b物品名称" + d + item.getType().name()));
//			sender.sendMessage(
//					color("&b允许权限" + d + MBanItemPermission.permission + "<action>." + item.getType().name()));
			if (reason != null) {
				sender.sendMessage(color("&b封禁原因" + d + reason));
			}
			if (type != null) {
				sender.sendMessage(color("&b封禁物品类型" + d + type));
			}
			if (action != null) {
				sender.sendMessage(color("&b封禁行为" + d + action));
			}
			sender.sendMessage(splitline);
		}

		public static void infoItem(ItemStack item, CommandSender sender) {
			infoItem(item, sender, null, null, null);
		}

		public static void help(CommandSender sender) {
			String t = "&b/mbanitem ";
			String d = " &8&l-&e ";
			sender.sendMessage(splitline);
			sender.sendMessage(color(t + "add <action> [reason]" + d + "添加封禁物品(手持的),action行为,reason原因(可选)"));
			sender.sendMessage(color(t + "addmenu [action] [reason]" + d + "批量添加封禁物品,action默认destroy"));
			sender.sendMessage(color(t + "addtool <action> [reason]" + d + "添加封禁物品(工具,消耗电或其它的,冒号附加值会变动)"));
			// sender.sendMessage(color(t + "addnbt <action> [reason]" + d + "添加封禁NBT物品"));
			// sender.sendMessage(color(t + "sa" + d + "添加封禁SA"));
			sender.sendMessage(color(t + "remove" + d + "移除封禁物品(手中)"));
			sender.sendMessage(color(t + "action" + d + "列出action"));
			// sender.sendMessage(color(t + "list [type]" + d +
			// "菜单查看封禁物品(type有normal、sa和nbt),默认打开normal"));
			sender.sendMessage(color(t + "list" + d + "菜单查看封禁物品"));
			sender.sendMessage(color(t + "info" + d + "查看手中物品信息"));
			sender.sendMessage(color(t + "reload" + d + "重载配置文件以及封禁物品文件"));
			sender.sendMessage(color(t + "&e可缩写为&b/mbi"));
			sender.sendMessage(color("&9MBanItem&6版本&8=&d" + version));
			sender.sendMessage(splitline);
		}

		public static void action(CommandSender sender) {
			String d = " &8&l-&e ";
			sender.sendMessage(splitline);
			sender.sendMessage(color("&bdestroy" + d + "搜查到封禁物品后销毁"));
			sender.sendMessage(color("&binteract" + d + "放置/使用/左右键"));
			sender.sendMessage(color("&bpickup" + d + "捡起"));
			sender.sendMessage(color("&bdrop" + d + "丢弃"));
			sender.sendMessage(color("&bheld" + d + "手持"));
			sender.sendMessage(color("&bcraft" + d + "合成"));
			// sender.sendMessage(color("&bbreak0" + d + "破坏(方块)"));
			sender.sendMessage(color("&d暂且只支持封禁一种行为"));
			sender.sendMessage(splitline);
		}
	}
}
