package utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.google.common.base.Strings;

public class BukkitUtils {

    public static String STRAIGHT_LINE_TEMPLATE = ChatColor.STRIKETHROUGH.toString() + Strings.repeat("-", 256);
    public static String STRAIGHT_LINE_DEFAULT = BukkitUtils.STRAIGHT_LINE_TEMPLATE.substring(0, 55);

    public static Player getFinalAttacker(final EntityDamageEvent ede, final boolean ignoreSelf) {
    	Player attacker = null;
    	if (ede instanceof EntityDamageByEntityEvent) {
    		final EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) ede;
    		final Entity damager = event.getDamager();
    		if (event.getDamager() instanceof Player) {
                attacker = (Player) damager;
            } else if (event.getDamager() instanceof Projectile) {
                final Projectile projectile = (Projectile) damager;
                @SuppressWarnings("deprecation")
				final ProjectileSource shooter = projectile.getShooter();
                if (shooter instanceof Player) {
                    attacker = (Player) shooter;
                }
            }
            if (attacker != null && ignoreSelf && event.getEntity().equals(attacker)) {
                attacker = null;
            }
        }
        return attacker;
    }
    
    public static Player getPlayer(final String string) {
        if (string == null) {
            return null;
        }
        return isUUID(string) ? Bukkit.getPlayer(UUID.fromString(string)) : Bukkit.getPlayer(string);
    }

    public static Player getPlayerList(final String string) {
        if (string == null) {
            return null;
        }
        return isUUID(string) ? Bukkit.getPlayer(UUID.fromString(string)) : Bukkit.getPlayer(string);
    }

    @Deprecated
    public static OfflinePlayer getOfflinePlayer(final String string) {
        if (string == null) {
            return null;
        }
        return isUUID(string) ? Bukkit.getOfflinePlayer(UUID.fromString(string)) : Bukkit.getOfflinePlayer(string);
    }
    
    public static boolean isUUID(String string) {
    	try{
    	    UUID.fromString(string);
    	    return true;
    	} catch (IllegalArgumentException exception){
    	    return false;
    	}
    }
    
}
