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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.SplittableRandom;

public class FreezeEnchant extends Enchantment implements Listener {

    private static final int MAX_LEVEL = 1;
    private static final String ENCHANTMENT_NAME = "Freeze";
    private final SplittableRandom random;
    public FreezeEnchant(final JavaPlugin plugin)
    {
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

    private static HashMap<Player, Integer> temp = new HashMap<>();

    //works!

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        System.out.println("ran!");
        if(!(e.getEntity() instanceof Player)) return;

        Player shooter = (Player) e.getEntity();
        ItemStack bow = shooter.getItemInUse();

        //Check if its bow.
        int level = bow.getEnchantments().get(this);

        double chance = level / 1.0;

        double rolled = random.nextDouble(1);

        if(rolled > chance) return;


        temp.put(shooter, level);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Arrow) || !(e.getEntity() instanceof Player) || !((((Arrow) e.getDamager()).getShooter()) instanceof Player)) return;

        Player target = (Player) e.getEntity();
        Player damager = (Player) ((Arrow) e.getDamager()).getShooter();

        if(temp.containsKey(damager)) {
            //the get slowness
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, temp.get(damager)));
        }

    }
}
