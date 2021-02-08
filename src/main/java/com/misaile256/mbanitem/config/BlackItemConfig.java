package com.misaile256.mbanitem.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.misaile256.mbanitem.MBanUtil;
import com.misaile256.mbanitem.options.BanAction;
import com.misaile256.mbanitem.options.BanItemType;

public class BlackItemConfig {
	private File config;
	public static final String rootName = "black-items";
	public static final String nodeName = "black-item";
	private Document document;
	private Element root;
	private Map<Integer, List<ItemStack>> blackItemMap;
	private Map<Integer, BanItemType> blackItemTypeMap;
	private Map<Integer, BanAction> blackItemActionMap;
	private ItemStack[] blackItems;
	private Inventory[] banViewMenu;
	public static final String encoding = "GBK";

	private BlackItemConfig(String fileConfig) {
		config = new File(fileConfig);
		if (!config.exists()) {
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			document = DocumentHelper.createDocument();
			root = document.addElement(rootName);
			write();
		} else {
			read();
		}
		loadBlackItem();
		document.setXMLEncoding(encoding);
	}

	public void reload() {
		read();
		loadBlackItem();
	}

	private void loadBlackItem() {
		blackItemMap = new HashMap<Integer, List<ItemStack>>();
		blackItemTypeMap = new HashMap<Integer, BanItemType>();
		blackItemActionMap = new HashMap<Integer, BanAction>();
		List<Element> elist = root.elements(nodeName);
		for (Element element : elist) {
			int id = Integer.valueOf(element.attributeValue("id"));
			Short durability = Short.valueOf(element.attributeValue("durability"));
			ItemStack is = new ItemStack(id);
			is.setDurability(durability);
			ItemMeta im = is.getItemMeta();
			List<String> lore = null;
			if (MBanUtil.isNullOrEmpty(im.getLore())) {
				lore = new ArrayList<String>();
			} else {
				lore = im.getLore();
			}
			BanAction banAction = MBanUtil.string2BanAction(element.attributeValue("action"));
			// lore.add(MBanUtil.color(element.attributeValue("reason")));
			// lore.add(MBanUtil.color("&c此物品有bug或会卡服"));
			switch (banAction) {
			case DESTROY:
				lore.add(MBanUtil.color("&4不可获取"));
				break;
			case PLACE:
				lore.add(MBanUtil.color("&4不可放置和左键"));
				break;
			case DROP:
				lore.add(MBanUtil.color("&4不可丢弃"));
				break;
			case HELD:
				lore.add(MBanUtil.color("&4不可手持"));
				break;
			case PICKUP:
				lore.add("&4不可拾取");
				break;
			default:
				break;
			}
			lore.add(MBanUtil.color("&b禁止行为 &8- &e" + banAction.toString()));
			im.setLore(lore);
			is.setItemMeta(im);
			List<ItemStack> alist = null;
			if (blackItemMap.get(id) == null) {
				alist = new ArrayList<ItemStack>();
			} else {
				alist = blackItemMap.get(id);
			}
			alist.add(is);
			blackItemActionMap.put(id, banAction);
			blackItemTypeMap.put(id, MBanUtil.string2BanItemType(element.attributeValue("type")));
			blackItemMap.put(id, alist);
		}
		blackItems = new ItemStack[elist.size()];
		int arri = 0;
		for (Entry<Integer, List<ItemStack>> entry : blackItemMap.entrySet()) {
			for (ItemStack itemStack : entry.getValue()) {
				blackItems[arri++] = itemStack;
			}
		}

		banViewMenu = new Inventory[(int) blackItems.length / (5 * 9) + 1];
		ItemStack next = new ItemStack(Material.PAPER);
		ItemMeta imn = next.getItemMeta();
		imn.setDisplayName(MBanUtil.color("&d&l下一页"));
		next.setItemMeta(imn);
		ItemStack previous = new ItemStack(Material.PAPER);
		ItemMeta imp = next.getItemMeta();
		imp.setDisplayName(MBanUtil.color("&d&l上一页"));
		previous.setItemMeta(imp);
		int imas = 0;
		for (int i = 0; i < banViewMenu.length; i++) {
			banViewMenu[i] = Bukkit.createInventory(null, 6 * 9, MBanUtil.BANVIEWMENUTITLE);
			if (banViewMenu.length > 1) {
				if (i == 0) {
					banViewMenu[i].setItem(53, next);
				} else if (i == banViewMenu.length - 1) {
					banViewMenu[i].setItem(45, previous);
				} else {
					banViewMenu[i].setItem(53, next);
					banViewMenu[i].setItem(45, previous);
				}
			}
			for (int j = 0; j < 45; j++) {
				if (imas == blackItems.length) {
					break;
				}
				banViewMenu[i].addItem(blackItems[imas++]);
			}
		}
	}

	public Document getDocument() {
		return this.document;
	}

	public Element getRoot() {
		return this.root;
	}

	public File getFile() {
		return this.config;
	}

	private void write() {
		try {
			OutputFormat of = new OutputFormat();
			of.setIndentSize(2);
			of.setNewlines(true);
			of.setTrimText(true);
			of.setPadText(true);
			of.setNewLineAfterDeclaration(false);
			of.setEncoding(encoding);
			XMLWriter writer = new XMLWriter(new FileWriter(config), of);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void read() {
		SAXReader reader = new SAXReader();
		reader.setEncoding(encoding);
		try {
			document = reader.read(config);
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public boolean addSingleBlackItem(ItemStack itemStack, BanItemType type, String action) {
		boolean b = addBlackItem(itemStack, type, action);
		if (b) {
			write();
			loadBlackItem();
		}
		return b;
	}

	public int addMultipleBlackItem(Inventory inventory) {
		int total = 0;
		for (int i = 0; i < inventory.getContents().length - 1; i++) {
			ItemStack itemStack = inventory.getContents()[i];
			if (MBanUtil.isNullOrEmpty(itemStack)) {
				break;
			} else {
				if (addBlackItem(itemStack, BanItemType.NORMAL, BanAction.DESTROY)) {
					total++;
				}
			}
		}
		if (total > 0) {
			loadBlackItem();
			write();
		}
		return total;
	}

	private boolean addBlackItem(ItemStack itemStack, BanItemType type, BanAction action) {
		int id = itemStack.getTypeId();
		int durability = itemStack.getDurability();
		String name = itemStack.getType().name();
		List<Element> elist = root.elements("black-item");
		if (elist.size() > 0) {
			for (Element fel : root.elements("black-item")) {
				if (Integer.valueOf(fel.attributeValue("id")) == id
						&& Integer.valueOf(fel.attributeValue("durability")) == durability) {
					return false;
				}
			}
		}
		root.addElement(nodeName).addAttribute("id", Integer.toString(id))
				.addAttribute("durability", Integer.toString(durability)).addAttribute("type", type.toString())
				.addAttribute("action", action.toString()).setText(name);
		return true;
	}

//	private boolean addBlackItem(ItemStack itemStack, BanItemType type, BanAction action) {
//
//		return addBlackItem(itemStack, type, action,
//				MBanItem.getInstance().getConfig().getString("default-reason.normal"));
//	}

	private boolean addBlackItem(ItemStack itemStack, BanItemType type, String action) {
		BanAction ba = MBanUtil.string2BanAction(action);
		if (ba == null || itemStack.getType() == Material.AIR) {
			return false;
		}
		return addBlackItem(itemStack, type, ba);
	}

	public File getConfig() {
		return config;
	}

	public Map<Integer, List<ItemStack>> getBlackItemMap() {
		return blackItemMap;
	}

	public Map<Integer, BanItemType> getBlackItemTypeMap() {
		return blackItemTypeMap;
	}

	public ItemStack[] getBlackItems() {
		return blackItems;
	}

	public Inventory[] getBanViewMenu() {
		return banViewMenu;
	}

	public static BlackItemConfig loadBlackItemConfig(String configPath) {
		return new BlackItemConfig(configPath);
	}

	public Map<Integer, BanAction> getBlackItemActionMap() {
		return blackItemActionMap;
	}
}
