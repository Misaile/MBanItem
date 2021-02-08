package com.misaile256.mbanitem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.misaile256.mbanitem.options.BanItemType;

public class MBanItemCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp()) {
				if (args.length == 0) {
					sendHelpMessage(player);
					return true;
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("action")) {
						sendOptionAction(player);
						return true;
					} else if (args[0].equalsIgnoreCase("reload")) {
						return MCommand.reload(player);
					} else if (args[0].equalsIgnoreCase("addm")) {
						return MCommand.addBanItemMenu(player);
					} else if (args[0].equalsIgnoreCase("list")) {
						return MCommand.openBanViewMenu(player);
					} else if (args[0].equalsIgnoreCase("add")) {
						sendOptionAction(player);
						return true;
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("add")) {
						return MCommand.addBanItem(player, player.getItemInHand(), args[1], BanItemType.NORMAL);
					} else if (args[0].equalsIgnoreCase("addt")) {
						return MCommand.addBanItem(player, player.getItemInHand(), args[1], BanItemType.TOOL);
					}
				}
			} else {
				return MCommand.openBanViewMenu(player);
			}
			return true;
		}
		return false;
	}

	private void sendOptionAction(Player player) {
		player.sendMessage(MBanUtil.TITLE);
		player.sendMessage(MBanUtil.color("&bdestroy &8- &e检测到直接销毁物品(包括以下)"));
		player.sendMessage(MBanUtil.color("&bplace &8- &e不可放置和左键"));
//		player.sendMessage(MBanUtil.color("&battack &8- &e不可左键"));
		player.sendMessage(MBanUtil.color("&bheld &8- &e不可手持"));
		player.sendMessage(MBanUtil.color("&bpickup &8- &e不可捡起"));
		player.sendMessage(MBanUtil.color("&bdrop &8- &e不可丢弃"));
//		player.sendMessage(MBanUtil.color("&bcraft &8- &e不可合成"));
		player.sendMessage(MBanUtil.color("&d暂且只支持ban一种行为"));
	}

	private void sendHelpMessage(Player player) {
		player.sendMessage(MBanUtil.TITLE);
		player.sendMessage(MBanUtil.color("&b/mbanitem add <action> &8- &e添加ban物品(带冒号)"));
		player.sendMessage(MBanUtil.color("&b/mbanitem addm &8- &e批量添加ban物品(默认action为destroy,带冒号)"));
		player.sendMessage(MBanUtil.color("&b/mbanitem addt <action>  &8- &eban物品id(不带冒号,一般是工具有耐久或电)"));
		player.sendMessage(MBanUtil.color("&b/mbanitem list &8- &e查看ban物品菜单"));
		player.sendMessage(MBanUtil.color("&b/mbanitem action &8- &e列出action行为"));
		player.sendMessage(MBanUtil.color("&b/mbanitem reload &8- &e重载配置文件(config和black-item)"));
		player.sendMessage(MBanUtil.color("&d/mbanitem可缩写为/mbi"));
	}

}
