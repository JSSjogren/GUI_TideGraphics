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
import com.earth2me.essentials.commands.WarpNotFoundException;

import net.ess3.api.InvalidWorldException;

public class Warps implements Listener{
	
	Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
	String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Kits" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY;
	
	public void openGui(Player user)
	{
		Inventory warps = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Warps");
		ArrayList<String> list = new ArrayList<String>(ess.getWarps().getList());
		ItemStack is = null;
		ItemMeta meta = null;
		
		//Iterating over GUI to add kits.
		for (int x = 0; x < 27; x++)
		{
			if (list.size() != 0 && user.hasPermission("essentials.warps." + list.get(0)))
			{
				is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
				meta = is.getItemMeta();
				meta.setDisplayName(ChatColor.DARK_GREEN + list.get(0));
				list.remove(0);
			} 
			else if ((list.size() != 0 && !user.hasPermission("essentials.warps." + list.get(0))) || list.size() == 0)
			{
				is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
				meta = is.getItemMeta();
				meta.setDisplayName(" ");
			}
			is.setItemMeta(meta);
			warps.setItem(x, is);
		}
		user.openInventory(warps);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e)
	{
		if(!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Warps"))
			return;
		
		e.setCancelled(true);
		
		Player p = (Player) e.getWhoClicked();
		ItemStack currentItem = e.getCurrentItem();
		
		if (currentItem.getType().equals(Material.STAINED_GLASS_PANE) && currentItem.getData().getData() == 5)
		{
			Bukkit.dispatchCommand(p, "warp " + ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()));
			p.closeInventory();
		}
		else if (currentItem.getType().equals(Material.STAINED_GLASS_PANE) && currentItem.getData().getData() == 14)
		{
			p.sendMessage(prefix + " You don't have access to that warp");
		}
	}
}
