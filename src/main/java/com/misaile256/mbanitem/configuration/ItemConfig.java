package com.misaile256.mbanitem.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;
import org.bukkit.inventory.ItemStack;

import com.misaile256.mbanitem.MBanItem;
import com.misaile256.mbanitem.option.Action;
import com.misaile256.mbanitem.option.ItemType;

public abstract class ItemConfig {

	private File config;
	private String configName;
	protected JsonObject json = new JsonObject();
	public static final String encoding = "GBK";
	private Logger log;

	class MapBanItem {
		public String name;
		public BanItem banItem;
	}

	public ItemConfig(String configName) {
		this.configName = configName + ".json";
		this.log = MBanItem.getInstance().getLogger();
		this.config = new File(MBanItem.getInstance().getDataFolder() + "\\" + this.configName);

		if (!this.config.exists()) {
			try {
				this.config.createNewFile();
			} catch (IOException e) {
				e.getStackTrace();
				log.info(configName + "¥¥Ω® ß∞‹.");
			}
		} else if (config.length() > 0) {
			read();
		}
	}

	protected void putJson(String name, JsonElement je) {
		json.add(name, je);
	}

	public abstract void reload();

	public abstract boolean addBanItem(ItemStack itemStack, ItemType type, Action action, String reason);

	public abstract boolean removeBanItem(ItemStack itemStack);

	protected void read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(config));
			StringBuilder b = new StringBuilder();
			String r = null;
			while ((r = br.readLine()) != null) {
				b.append(r);
			}
			br.close();
			json = (JsonObject) new JsonParser().parse(b.toString());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(configName + "∂¡»° ß∞‹.");
		}
	}

	protected void write() {
		try {

			FileWriter fw = new FileWriter(config);
			fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(json));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.info(configName + "–¥»Î ß∞‹.");
		}
	}

	public File getConfig() {
		return config;
	}

	public String getConfigName() {
		return configName;
	}

}
