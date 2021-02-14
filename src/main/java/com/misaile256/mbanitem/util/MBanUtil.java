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
	public static final String addMenuTitle = prefix + color("&c������ӷ����Ʒ");
	public static final String listMenuTitle = color("[&9&lMBanItem&f] &c�����Ʒ��");

	public static String color(String text) {
		char[] a = text.toCharArray();
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(a[i + 1]) > -1) {
				a[i] = '��';
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
		public static final String reason = color("&c����Ʒ���ܻ���ɿ������ߴ���ˢ��Ʒ��bug");
		public static final String destroy = color("&4���ɻ�ȡ");
		public static final String craft = color("&4���ɺϳ�");
		public static final String interact = color("&4����ʹ��/����");
		public static final String drop = color("&4���ɶ���");
		public static final String held = color("&�����ֳ�");
		public static final String pickup = color("&4���ɼ���");
		public static final String break0 = color("&4�����ƻ�");
		public static final String checkBanItem = prefix + color("&4ϵͳ�Ѳ�������Ʒ,�Զ����");
		public static final String pickupBanItem = prefix + color("&cϵͳ��⵽�����з����Ʒ,�Զ����");

		public static final String noDrop = prefix + color("&c����Ʒ��������");
		public static final String noCraft = prefix + color("&c�����Ʒ������ϳ�!");
		public static final String noPickup = prefix + color("&c������Ʒ���������");
		public static final String noHeld = prefix + color("&c����Ʒ�������ֳ�");
		public static final String noInteract = prefix + color("&c������Ʒʹ��/����");

		public static final String removeSuccessful = color("&a�Ƴ��ɹ�!");
		public static final String removeFailure = color("&c�Ƴ�ʧ��");
		public static final String addSuccessful = color("&a��ӳɹ�!");
		public static final String addFailure = color("&c���ʧ��.");
		public static final String addMenu = color("&a�� &c&l{count} &a��ӳɹ�.");
		public static final String reload = color("&a���سɹ�!");

		public static void infoItem(ItemStack item, CommandSender sender, ItemType type, Action action, String reason) {
			sender.sendMessage(splitline);
			String d = " &8&l-&c ";
			sender.sendMessage(color("&b��ƷID" + d + item.getTypeId() + ":" + item.getDurability()));
			sender.sendMessage(color("&b��Ʒ����" + d + item.getType().name()));
//			sender.sendMessage(
//					color("&b����Ȩ��" + d + MBanItemPermission.permission + "<action>." + item.getType().name()));
			if (reason != null) {
				sender.sendMessage(color("&b���ԭ��" + d + reason));
			}
			if (type != null) {
				sender.sendMessage(color("&b�����Ʒ����" + d + type));
			}
			if (action != null) {
				sender.sendMessage(color("&b�����Ϊ" + d + action));
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
			sender.sendMessage(color(t + "add <action> [reason]" + d + "��ӷ����Ʒ(�ֳֵ�),action��Ϊ,reasonԭ��(��ѡ)"));
			sender.sendMessage(color(t + "addmenu [action] [reason]" + d + "������ӷ����Ʒ,actionĬ��destroy"));
			sender.sendMessage(color(t + "addtool <action> [reason]" + d + "��ӷ����Ʒ(����,���ĵ��������,ð�Ÿ���ֵ��䶯)"));
			// sender.sendMessage(color(t + "addnbt <action> [reason]" + d + "��ӷ��NBT��Ʒ"));
			// sender.sendMessage(color(t + "sa" + d + "��ӷ��SA"));
			sender.sendMessage(color(t + "remove" + d + "�Ƴ������Ʒ(����)"));
			sender.sendMessage(color(t + "action" + d + "�г�action"));
			// sender.sendMessage(color(t + "list [type]" + d +
			// "�˵��鿴�����Ʒ(type��normal��sa��nbt),Ĭ�ϴ�normal"));
			sender.sendMessage(color(t + "list" + d + "�˵��鿴�����Ʒ"));
			sender.sendMessage(color(t + "info" + d + "�鿴������Ʒ��Ϣ"));
			sender.sendMessage(color(t + "reload" + d + "���������ļ��Լ������Ʒ�ļ�"));
			sender.sendMessage(color(t + "&e����дΪ&b/mbi"));
			sender.sendMessage(color("&9MBanItem&6�汾&8=&d" + version));
			sender.sendMessage(splitline);
		}

		public static void action(CommandSender sender) {
			String d = " &8&l-&e ";
			sender.sendMessage(splitline);
			sender.sendMessage(color("&bdestroy" + d + "�Ѳ鵽�����Ʒ������"));
			sender.sendMessage(color("&binteract" + d + "����/ʹ��/���Ҽ�"));
			sender.sendMessage(color("&bpickup" + d + "����"));
			sender.sendMessage(color("&bdrop" + d + "����"));
			sender.sendMessage(color("&bheld" + d + "�ֳ�"));
			sender.sendMessage(color("&bcraft" + d + "�ϳ�"));
			// sender.sendMessage(color("&bbreak0" + d + "�ƻ�(����)"));
			sender.sendMessage(color("&d����ֻ֧�ַ��һ����Ϊ"));
			sender.sendMessage(splitline);
		}
	}
}
