package listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

import me.vehqzi.vanish.Config;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockAction;
import utils.BukkitUtils;

public class VanishListener implements Listener {

	public Config config = Config.getInstance();
	


	public static void setVanish(boolean type, Player p) {
		if(type) {
			for(Player g : Bukkit.getOnlinePlayers()) {
				if(!g.hasPermission("desteria.commands.vanish")) {
					g.hidePlayer(p);
				}
			}
			Config.getInstance().getStorageFile().set("Players." + p.getUniqueId() + ".isVanished", true);
		} else {
			for(Player g : Bukkit.getOnlinePlayers()) {
				if(!g.hasPermission("vehqzi.commands.vanish")) {
					g.showPlayer(p);
				}
			}
			Config.getInstance().getStorageFile().set("Players." + p.getUniqueId() + ".isVanished", false);
		}
		Config.getInstance().saveFiles();
	}
	
	public static boolean isVanished(Player p ) {
		if(Config.getInstance().getStorageFile().getBoolean("Players." + p.getUniqueId() + ".isVanished")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void handleFakeChest(Player player, Chest chest, boolean open) {
		Inventory chestInventory = chest.getInventory();
		if ((chestInventory instanceof DoubleChestInventory)) {
			chest = (Chest)((DoubleChestInventory)chestInventory).getHolder().getLeftSide();
		}
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutBlockAction(chest.getX(), chest.getY(), chest.getZ(), Blocks.CHEST, 1, open ? 1 : 0));
		player.playSound(chest.getLocation(), open ? Sound.CHEST_OPEN : Sound.CHEST_CLOSE, 1.0F, 1.0F);
	}
	
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().hasPermission("desteria.commands.vanish")) {
			e.getPlayer().sendMessage(ChatColor.YELLOW + "You have joined the server whilst in vanish.");
			setVanish(true, e.getPlayer());
		}
	}
	
	public void onQuit(PlayerQuitEvent e) {
		if(isVanished(e.getPlayer())) {
			setVanish(false, e.getPlayer());
		}
	}
		
	@EventHandler(ignoreCancelled=true, priority=EventPriority.NORMAL)
	public void onEntityTarget(EntityTargetEvent e) {
		if (e.getReason() == EntityTargetEvent.TargetReason.CUSTOM) {
	  	   return;
		}
		Entity target = e.getTarget();
		Entity entity = e.getEntity();
		if ((((entity instanceof ExperienceOrb)) || ((entity instanceof LivingEntity))) && ((target instanceof Player)) && isVanished((Player) target)) {
			e.setCancelled(true);
		}
	  }
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if(isVanished(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.YELLOW + "You are not allowed to drop items whilst in vanish!");
		}
	}
	  
	@EventHandler(ignoreCancelled=true, priority=EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(isVanished(e.getEntity())) {
			e.setDeathMessage(null);
		}
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.NORMAL)
		public void onEntityDamage(EntityDamageEvent event) {
		EntityDamageEvent.DamageCause cause = event.getCause();
		if ((cause == EntityDamageEvent.DamageCause.VOID) || (cause == EntityDamageEvent.DamageCause.SUICIDE)) {
			return;
		}
		Entity entity = event.getEntity();
		if ((entity instanceof Player)) {
			Player attacked = (Player)entity;
			Player attacker = BukkitUtils.getFinalAttacker(event, true);
			if(isVanished(attacked)) {
				if ((attacker != null) && attacker.hasPermission("desteria.commands.vanish")) {
					attacker.sendMessage(ChatColor.RED + "That player is currently vanished!");
				}
				event.setCancelled(true);
				return;
			}
			if ((attacker != null) && isVanished(attacker)) {
				attacker.sendMessage(ChatColor.RED + "You cannot attack players whilst vanished!");
				event.setCancelled(true);
			}
		}
	}
	  
	@EventHandler
	public void onQuit2(PlayerQuitEvent e) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(isVanished(p)) {
				e.getPlayer().showPlayer(p);
			}
		}
	}
	
	  
	@EventHandler
	public void onQuit2(PlayerJoinEvent e) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(isVanished(p)) {
				e.getPlayer().hidePlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onBuild(BlockBreakEvent e ) {
		if(isVanished(e.getPlayer())) {
			e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to build whilst in vanish!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBuild(BlockPlaceEvent e ) {
		if(isVanished(e.getPlayer())) {
			e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to build whilst in vanish!");
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void pickupItem(PlayerPickupItemEvent e) {
		if(isVanished(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
}

