package com.misaile256.mbanitem.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.util.MBanUtil;

public class MBanItemCommandTabExecutor implements TabExecutor {
	static List<String> commandName = new ArrayList<String>();
	static List<String> action = new ArrayList<String>();
	static Map<String, MBanItemCommand> command = new HashMap<String, MBanItemCommand>();

	enum Tab {
		add, addtool, addnbt, addmenu, action, reload, list, sa, info, remove;
	}

	static {
		for (Tab string : Tab.values()) {
			commandName.add(string.name());
		}
		for (Action string : Action.values()) {
			action.add(string.name());
		}
		command.put(Tab.add.name(), new MBanItemCommandAdd());
		command.put(Tab.addnbt.name(), new MBanItemCommandAddNBT());
		command.put(Tab.addtool.name(), new MBanItemCommandAddTool());
		command.put(Tab.addmenu.name(), new MBanItemCommandAddMenu());
		command.put(Tab.sa.name(), new MBanItemCommandSA());
		command.put(Tab.list.name(), new MBanItemCommandList());
		command.put(Tab.action.name(), new MBanItemCommandAction());
		command.put(Tab.remove.name(), new MBanItemCommandRemove());
		command.put(Tab.reload.name(), new MBanItemCommandReload());
		command.put(Tab.info.name(), new MBanItemCommandInfo());
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			return commandName;
		} else if (args.length == 2) {
			return action;
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp()) {
				if (args.length == 0) {
					MBanUtil.Message.help(player);
					return true;
				} else if (args.length > 0) {
					if (command.containsKey(args[0])) {
						return command.get(args[0]).execute(player, args);
					} else {
						player.sendMessage(MBanUtil.prefix + MBanUtil.color("&cŒﬁ–ß÷∏¡Ó"));
					}
				}
			} else {
				return command.get(Tab.list.name()).execute(player, args);
			}
		}
		return true;
	}
}
