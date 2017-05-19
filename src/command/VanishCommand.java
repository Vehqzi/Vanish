package command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import listeners.VanishListener;
import me.vehqzi.vanish.Main;

public class VanishCommand implements CommandExecutor {
	
	public VanishCommand(Main main) { }

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("desteria.commands.vanish")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
			return true;
		}
		Player p = (Player)sender;
		if(args.length == 0) {
			if(VanishListener.isVanished(p)) {
				VanishListener.setVanish(false, p);
				p.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.RED + "disabled" + ChatColor.YELLOW + " your visibility.");
			} else {
				VanishListener.setVanish(true, p);
				p.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.GREEN + "enabled" + ChatColor.YELLOW + " your visibility.");
			}
		}

		return true;
	}
	

	
	
}

