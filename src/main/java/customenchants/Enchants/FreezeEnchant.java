package customenchants.Enchants;

import customenchants.Utils.EnchantmentUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.SplittableRandom;

public class FreezeEnchant extends Enchantment implements Listener {

    private static final int MAX_LEVEL = 1;
    private static final String ENCHANTMENT_NAME = "Freeze";

    public FreezeEnchant(final JavaPlugin plugin)
    {
        super(new NamespacedKey(plugin, ENCHANTMENT_NAME));
    }

    @Override
    public String getName() {
        return ENCHANTMENT_NAME;
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.BOW;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return EnchantmentUtils.isBow(item);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e) {
        if(!(e.getEntity() instanceof Arrow)) return;
        Player shooter = (Player) e.getEntity().getShooter();
    }
}
