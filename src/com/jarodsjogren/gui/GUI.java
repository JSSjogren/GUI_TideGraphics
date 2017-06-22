package com.jarodsjogren.gui;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GUI extends JavaPlugin implements Listener{

	Kits kits = new Kits();
	Baltop balt = new Baltop();
	Warps warps = new Warps();

	@Override
	public void onEnable()
	{
		registerEvents(this, new Kits(), new Baltop(), new Warps());

		getLogger().info("Plugin by TheBeyonder");
		getLogger().info("GUI Enabled");
	}

	@Override
	public void onDisable()
	{
		getLogger().info("Plugin by TheBeyonder");
		getLogger().info("GUI Disabled");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equals("warps") || (cmd.getName().equals("warp") && args.length == 0))
		{
			if (args.length == 0)
				warps.openGui((Player) sender);
			return true;
		}
		else if (cmd.getName().equals("kits") && args.length == 0)
		{
			kits.openGui((Player) sender);
			return true;
		}
		else if (cmd.getName().equals("baltop"))
		{
			balt.openGui((Player) sender);
			return true;
		}
		return true;
	}

	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}
}
