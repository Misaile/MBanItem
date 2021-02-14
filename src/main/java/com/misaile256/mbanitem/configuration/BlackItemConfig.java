package com.misaile256.mbanitem.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.option.ItemType;
import com.misaile256.mbanitem.util.MBanUtil;

public class BlackItemConfig extends ItemConfig {

	private Inventory[] menu;
	private List<ItemStack> items = new ArrayList<ItemStack>();
	protected Map<String, BanItem> map = new HashMap<String, BanItem>();
	public static final String nextName = MBanUtil.prefix + MBanUtil.color("&d&l下一页");
	public static final String previousName = MBanUtil.prefix + MBanUtil.color("&d&l上一页");

	public BlackItemConfig(String configName) {
		super(configName);
		if (getConfig().length() > 0) {
			loadJson();
		}
		loadConfig();
	}

	private void loadConfig() {
		if (items.size() > 0) {
			items.clear();
		}
		for (Entry<String, BanItem> e : map.entrySet()) {
			for (Entry<Short, ItemStack> e2 : e.getValue().getMap().entrySet()) {
				items.add(e2.getValue());
			}
		}

		menu = new Inventory[(int) (items.size() / (5 * 9)) + 1];
		ItemStack next = new ItemStack(Material.PAPER);
		ItemMeta imn = next.getItemMeta();
		imn.setDisplayName(nextName);
		next.setItemMeta(imn);
		ItemStack previous = new ItemStack(Material.PAPER);
		ItemMeta imp = previous.getItemMeta();
		imp.setDisplayName(previousName);
		previous.setItemMeta(imp);
		int i = 0;
		for (int j = 0; j < menu.length; j++) {
			menu[j] = Bukkit.createInventory(null, 6 * 9, MBanUtil.listMenuTitle + MBanUtil.color("&d&l" + (j + 1)));
			if (menu.length > 1) {
				if (j == 0) {
					menu[j].setItem(53, next);
				} else if (j == menu.length - 1) {
					menu[j].setItem(45, previous);
				} else {
					menu[j].setItem(53, next);
					menu[j].setItem(45, previous);
				}
			}
			for (int e = 0; e < 5 * 9; e++) {
				if (i == this.items.size()) {
					break;
				}
				menu[j].addItem(items.get(i++));
			}
		}
	}

	protected boolean addBanItem(ItemStack itemStack, ItemType type, Action action, String reason, boolean save) {
		if (MBanUtil.inull(itemStack) || itemStack.getType() == Material.AIR) {
			return false;
		} else if (map.containsKey(itemStack.getType().name())) {
			BanItem bi = map.get(itemStack.getType().name());
			if (bi.putAdditional(itemStack.getDurability())) {
				putJson(bi.getName(), bi.getBanItem());
				return true;
			} else {
				return false;
			}
		} else {
			BanItem bi = new BanItem(itemStack, type, action, reason);
			map.put(bi.getName(), bi);
			putJson(bi.getName(), bi.getBanItem());
		}
		if (save) {
			write();
			loadConfig();
		}
		return true;
	}

	public boolean addBanItem(ItemStack itemStack, ItemType type, Action action, String reason) {
		return addBanItem(itemStack, type, action, reason, true);
	}

	public boolean removeBanItem(ItemStack itemStack) {
		if (!map.containsKey(itemStack.getType().name())) {
			return false;
		}
		BanItem bi = map.get(itemStack.getType().name());
		if (bi.getMap().size() == 1) {
			json.remove(itemStack.getType().name());
			map.remove(bi.getName());
		} else {
			bi.removeAdditional(itemStack.getDurability());
			putJson(bi.getName(), bi.getBanItem());
		}
		write();
		loadConfig();
		return true;
	}

	public int addBanItems(ItemStack[] items, ItemType type, Action action, String reason) {
		int total = 0;
		if (reason == null) {
			reason = MBanUtil.Message.reason;
		}
		for (int i = 0; i < 5 * 9; i++) {
			if (addBanItem(items[i], type, action, reason, false)) {
				total++;
			}
		}
		if (total > 0) {
			write();
			loadConfig();
		}
		return total;
	}

	public Inventory[] getMenu() {
		return menu;
	}

	@Override
	public void reload() {
		read();
		loadJson();
		loadConfig();
	}

	public Map<String, BanItem> getMap() {
		return map;
	}

	private void loadJson() {
		if (map.size() > 0) {
			map.clear();
		}
		for (Entry<String, JsonElement> e : json.entrySet()) {
			map.put(e.getKey(), BanItem.parseBanItem(e.getKey(), e.getValue()));
		}
	}

}
