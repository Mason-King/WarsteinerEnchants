package customenchants.Enchants;

import customenchants.Utils.EnchantmentUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class FreezeEnchant extends Enchantment implements Listener {

    private final int MAX_LEVEL = 1;
    private final String ENCHANTMENT_NAME = "Freeze";

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
        if(!(e.getEntity() instanceof EntityType.ARROW)) return;
        Player shooter = e.getEntity().getShooter();
    }
}
