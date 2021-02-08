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
		player.sendMessage(MBanUtil.color("&bdestroy &8- &e��⵽ֱ��������Ʒ(��������)"));
		player.sendMessage(MBanUtil.color("&bplace &8- &e���ɷ��ú����"));
//		player.sendMessage(MBanUtil.color("&battack &8- &e�������"));
		player.sendMessage(MBanUtil.color("&bheld &8- &e�����ֳ�"));
		player.sendMessage(MBanUtil.color("&bpickup &8- &e���ɼ���"));
		player.sendMessage(MBanUtil.color("&bdrop &8- &e���ɶ���"));
//		player.sendMessage(MBanUtil.color("&bcraft &8- &e���ɺϳ�"));
		player.sendMessage(MBanUtil.color("&d����ֻ֧��banһ����Ϊ"));
	}

	private void sendHelpMessage(Player player) {
		player.sendMessage(MBanUtil.TITLE);
		player.sendMessage(MBanUtil.color("&b/mbanitem add <action> &8- &e���ban��Ʒ(��ð��)"));
		player.sendMessage(MBanUtil.color("&b/mbanitem addm &8- &e�������ban��Ʒ(Ĭ��actionΪdestroy,��ð��)"));
		player.sendMessage(MBanUtil.color("&b/mbanitem addt <action>  &8- &eban��Ʒid(����ð��,һ���ǹ������;û��)"));
		player.sendMessage(MBanUtil.color("&b/mbanitem list &8- &e�鿴ban��Ʒ�˵�"));
		player.sendMessage(MBanUtil.color("&b/mbanitem action &8- &e�г�action��Ϊ"));
		player.sendMessage(MBanUtil.color("&b/mbanitem reload &8- &e���������ļ�(config��black-item)"));
		player.sendMessage(MBanUtil.color("&d/mbanitem����дΪ/mbi"));
	}

}
