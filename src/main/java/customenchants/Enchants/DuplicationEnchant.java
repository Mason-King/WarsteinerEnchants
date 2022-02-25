package customenchants.Enchants;

import customenchants.Utils.EnchantmentUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.SplittableRandom;

public class DuplicationEnchant extends Enchantment implements Listener {

    private static final String ENCHANTMENT_NAME = "Duplication";
    private static final int MAX_LEVEL = 5;

    private final SplittableRandom random;

    public DuplicationEnchant(final JavaPlugin plugin) {
        super(new NamespacedKey(plugin, ENCHANTMENT_NAME));
        this.random = new SplittableRandom();
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

    //Done
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player broke = e.getPlayer();

        if(broke.getInventory().getItemInMainHand() == null || broke.getInventory().getItemInMainHand().getType().equals(Material.AIR) || !broke.getInventory().getItemInMainHand().getEnchantments().containsKey(this)) return;

        int level = broke.getInventory().getItemInMainHand().getEnchantments().get(this);

        double chance = level / 2.0;

        double rolled = random.nextDouble(level * 2);

        if(rolled > chance) return;

        Collection<ItemStack> drops = e.getBlock().getDrops();

        for(ItemStack i : drops) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), i);
        }

    }

}
