package com.jarodsjogren.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.earth2me.essentials.Essentials;

public class Kits implements Listener {

	Essentials e = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
	String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Kits" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY;
	
	public void openGui(Player user)
	{
		Inventory kits = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Kits");
		ArrayList<String> list = new ArrayList<String>(e.getSettings().getKits().getKeys(false));
		ItemStack is = null;
		ItemMeta meta = null;
		
		//Iterating over GUI to add kits.
		for (int x = 0; x < 27; x++)
		{
			if (list.size() != 0 && user.hasPermission("essentials.kits." + list.get(0)))
			{
				is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)3);
				meta = is.getItemMeta();
				meta.setDisplayName(ChatColor.BLUE + list.get(0).substring(0,1).toUpperCase() + list.get(0).substring(1));
				list.remove(0);
			} 
			else if (list.size() != 0 && !user.hasPermission("essentials.kits." + list.get(0)))
			{
				is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)14);
				meta = is.getItemMeta();
				meta.setDisplayName(ChatColor.BLUE + list.get(0).substring(0,1).toUpperCase() + list.get(0).substring(1));
				list.remove(0);
			}
			else if (list.size() == 0)
			{
				is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
				meta = is.getItemMeta();
				meta.setDisplayName(" ");
			}
			is.setItemMeta(meta);
			kits.setItem(x, is);
		}
		user.openInventory(kits);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e)
	{
		if(!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Kits"))
			return;
		
		e.setCancelled(true);
		
		Player p = (Player) e.getWhoClicked();
		ItemStack currentItem = e.getCurrentItem();
		
		if (currentItem.getType().equals(Material.STAINED_GLASS_PANE) && currentItem.getData().getData() == 3)
		{
			p.performCommand("kit " + ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
		}
		else if (currentItem.getType().equals(Material.STAINED_GLASS_PANE) && currentItem.getData().getData() == 14)
		{
			p.sendMessage(prefix + " You don't have access to that kit.");
		}
	}

}
