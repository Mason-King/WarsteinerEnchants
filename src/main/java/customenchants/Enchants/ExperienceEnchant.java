package customenchants.Enchants;

import customenchants.Utils.EnchantmentUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.SplittableRandom;

public class ExperienceEnchant extends Enchantment implements Listener {

    private static final String ENCHANTMENT_NAME = "Experience";
    private static final int MAX_LEVEL = 5;
    private final SplittableRandom random;

    public ExperienceEnchant(final JavaPlugin plugin) {
        super(new NamespacedKey(plugin, ENCHANTMENT_NAME));
        random = new SplittableRandom();
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
        return EnchantmentTarget.TOOL;
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
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return EnchantmentUtils.isPickaxe(itemStack);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player broke = e.getPlayer();

        if(broke.getInventory().getItemInMainHand() == null || broke.getInventory().getItemInMainHand().getType().equals(Material.AIR) || !broke.getInventory().getItemInMainHand().containsEnchantment(this)) return;

        int level = broke.getInventory().getItemInMainHand().getEnchantmentLevel(this);

        double chance = level / 2.0;

        double rolled = random.nextDouble(1);

        if(rolled > chance) return;

        if(e.getBlock().getType().equals(Material.STONE) || e.getBlock().getType().equals(Material.IRON_ORE) || e.getBlock().getType().equals(Material.COAL_ORE)) {
            if(e.getExpToDrop() == 0) {
                e.setExpToDrop(10);
            } else {
                e.setExpToDrop(e.getExpToDrop() * 2);
            }
        }
    }
}
