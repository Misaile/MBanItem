package com.misaile256.mbanitem.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;

import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.option.ItemType;
import com.misaile256.mbanitem.util.MBanUtil;

public class BanItem {

	private JsonArray additionals = new JsonArray();
	private Map<Short, ItemStack> map = new HashMap<Short, ItemStack>();
	private String name;
	private ItemType type;
	private Action action;
	private String reason;
	private JsonObject banItem = new JsonObject();

	class JsonBanItem {
		public short[] additionals;
		public ItemType type;
		public Action action;
		public String reason;
	}

	public BanItem(String name, short[] additionals, ItemType type, Action action, String reason) {
		this.name = name;
		this.type = type;
		this.action = action;
		this.reason = reason;
		for (short s : additionals) {
			this.additionals.add(new JsonParser().parse(Short.toString(s)));
			ItemStack is = new ItemStack(Material.getMaterial(name), 1, s);
			setLore(is);
			map.put(s, is);
		}
		banItem.addProperty("id", Material.getMaterial(name).getId());
		banItem.addProperty("name", name);
		banItem.add("additionals", this.additionals);
		banItem.addProperty("type", type.toString());
		banItem.addProperty("action", action.toString());
		banItem.addProperty("reason", MBanUtil.color(reason));
	}

	public BanItem(ItemStack itemStack, ItemType type, Action action, String reason) {
		this(itemStack.getType().name(), new short[] { itemStack.getDurability() }, type, action, reason);
	}

	private BanItem(String name, JsonBanItem jbi) {
		this(name, jbi.additionals, jbi.type, jbi.action, jbi.reason);
	}

	public static BanItem parseBanItem(String name, JsonElement json) {
		try {
			return new BanItem(name, new Gson().fromJson(json, JsonBanItem.class));
		} catch (Exception e) {
			return null;
		}
	}

	public boolean putAdditional(Short s) {
		for (JsonElement e : additionals) {
			if (e.getAsShort() == s) {
				return false;
			}
		}
		additionals.add(new JsonParser().parse(Short.toString(s)));
		banItem.add("additionals", additionals);
		map.put(s, new ItemStack(Material.getMaterial(name), 1, s));
		return true;
	}

	public short removeAdditional(Short s) {
		if (!map.containsKey(s)) {
			return -1;
		} else {
			map.remove(s);
			JsonArray ja = new JsonArray();
			for (int i = 0; i < additionals.size(); i++) {
				if (additionals.get(i).getAsShort() != s) {
					ja.add(new JsonParser().parse(Short.toString(s)));
				}
			}
			this.additionals = ja;
			banItem.add("additionals", additionals);
			return s;
		}
	}

	@Override
	public String toString() {
		return banItem.toString();
	}

	void setLore(ItemStack itemStack) {
		ItemMeta im = itemStack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(MBanUtil.color("&b封禁原因 &8- &d" + reason));
		lore.add(MBanUtil.color("&b封禁行为 &8- &d" + action));
		switch (getAction()) {
		case destroy:
			lore.add(MBanUtil.Message.destroy);
			break;
		case interact:
			lore.add(MBanUtil.Message.interact);
			break;
		case break0:
			lore.add(MBanUtil.Message.break0);
			break;
		case craft:
			lore.add(MBanUtil.Message.craft);
			break;
		case pickup:
			lore.add(MBanUtil.Message.pickup);
			break;
		case drop:
			lore.add(MBanUtil.Message.drop);
			break;
		case held:
			lore.add(MBanUtil.Message.held);
			break;
		default:
			break;
		}
		im.setLore(lore);
		itemStack.setItemMeta(im);
	}

	public ItemStack getItemStack(Short s) {
		return map.get(s);
	}

	public boolean hasAdditional(Short s) {
		return map.containsKey(s);
	}

	public String getName() {
		return name;
	}

	public ItemType getType() {
		return type;
	}

	public Action getAction() {
		return action;
	}

	public String getReason() {
		return reason;
	}

	public Map<Short, ItemStack> getMap() {
		return map;
	}

	public JsonObject getBanItem() {
		return this.banItem;
	}

}
