package customenchants.Enchants;

import customenchants.Utils.EnchantmentUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.SplittableRandom;

public class ToxicEnchant extends Enchantment implements Listener {

    private static final int MAX_LEVEL = 3;
    private static final String ENCHANTMENT_NAME = "Toxic";

    private final SplittableRandom random;

    public ToxicEnchant(final JavaPlugin plugin)
    {
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
        return EnchantmentTarget.WEAPON;
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
        return EnchantmentUtils.isSword(item);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof  Player)) return;

        Player damager = (Player) e.getDamager();
        Player target = (Player) e.getEntity();

        int level = damager.getInventory().getItemInMainHand().getEnchantmentLevel(this);

        if(level == 0) return;

        double percentChance = level / 2.0;
        double rolled = random.nextDouble(level * 2);

        if(rolled > percentChance) return;

        target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 5 * 20, 1));

    }

}
