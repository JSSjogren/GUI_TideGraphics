package com.jarodsjogren.gui;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class Baltop implements Listener{
	
	Economy econ = null;
	
    private void setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }
    	
	public void openGui(Player user)
	{		
		setupEconomy();
		Inventory baltop = Bukkit.createInventory(null, 36, ChatColor.BLUE + "Top Balances");
		OfflinePlayer p = null;
		ArrayList<OfflinePlayer> offp = new ArrayList<OfflinePlayer>(Arrays.asList(Bukkit.getOfflinePlayers()));
		ArrayList<String> top = new ArrayList<String>();
		for (int i = 0; i < 27; i++)
		{
			if (i == 9)
				i += 9;
			double highest = 0;
			String highestP = " ";
			
			for (OfflinePlayer off : offp)
			{
				if (econ.getBalance(off) >= highest)
				{
					highest = econ.getBalance(off);
					highestP = off.getName();
				}
			}
			
			if (highestP != " ") {
				p = Bukkit.getOfflinePlayer(highestP);
				top.add(highestP);
			}
			if (offp.contains(p))
			{
				offp.remove(p);
			}
			
			ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
			ItemMeta meta = is.getItemMeta();
			meta.setDisplayName(" ");
			is.setItemMeta(meta);
			baltop.setItem(i, is);
		}
		
		int rank = 0;
		for (int m = 9; m < 36; m++)
		{
			ArrayList<String> lore = new ArrayList<String>();
			if (m == 18)
			{
				m += 9;
			}
			rank ++;
			lore.add(" ");
            		lore.add(ChatColor.GOLD + "* " + ChatColor.YELLOW + "Rank " + ChatColor.WHITE + "#" + rank);
           		 lore.add(ChatColor.GOLD + "* " + ChatColor.YELLOW + "Balance " + ChatColor.WHITE + "$" + econ.getBalance(Bukkit.getOfflinePlayer(top.get(0))));
			ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
           		 SkullMeta meta = (SkullMeta) is.getItemMeta();
            		meta.setDisplayName(ChatColor.YELLOW + "[|] " + ChatColor.GOLD + ChatColor.UNDERLINE + top.get(0));
            		meta.setOwner(top.get(0));
            		meta.setLore(lore);
            		is.setItemMeta(meta);
            		baltop.setItem(m, is);
           		 top.remove(0);
		}

		user.openInventory(baltop);
	}
}
