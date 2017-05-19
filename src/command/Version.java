package command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.vehqzi.vanish.Main;

public class Version
  implements CommandExecutor
{
  Main plugin;
  
  public Version(Main instance)
  {
    this.plugin = instance;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("vanishver")) {
      p.sendMessage("");
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Running &3Vanish Version &c&o1.0 &7Developed by &f&oVehqzi"));
      p.sendMessage("");
    }
    return false;
  }
}

