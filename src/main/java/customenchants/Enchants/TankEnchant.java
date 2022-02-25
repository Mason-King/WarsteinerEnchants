package customenchants.Enchants;

import customenchants.ArmorEquipAPI.ArmorEquipEvent;
import customenchants.Utils.EnchantmentUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TankEnchant extends Enchantment implements Listener {

    public static final String ENCHANTMENT_NAME = "Tank";
    public static final int MAX_LEVEL = 2;

    public TankEnchant(final JavaPlugin plugin)
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
        return EnchantmentTarget.ARMOR;
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
        return false;
    }

    @EventHandler
    public void onEquip(ArmorEquipEvent e) {
        //check if they are equiping it
        if(e.getNewArmorPiece() != null && e.getNewArmorPiece().getType() != Material.AIR) {
            ItemStack newArmor = e.getNewArmorPiece();
            if(!newArmor.containsEnchantment(this)) return;
            int total = newArmor.getEnchantmentLevel(this);
            for(ItemStack i : e.getPlayer().getInventory().getArmorContents()) {
                if(i == null) continue;
                if(i.equals(newArmor)) continue;
                if(i.containsEnchantment(this)) {
                    total = total + i.getEnchantmentLevel(this);
                }
            }
            e.getPlayer().setMaxHealth(20 + (total * 0.5));
        } else if (e.getOldArmorPiece() != null & e.getOldArmorPiece().getType() != Material.AIR) {
            ItemStack newArmor = e.getNewArmorPiece();
            ItemStack oldArmor = e.getOldArmorPiece();
            if(!oldArmor.containsEnchantment(this)) return;
            int total = 0;
            for(ItemStack i : e.getPlayer().getInventory().getArmorContents()) {
                if(i == null) continue;
                if(i.equals(oldArmor)) continue;
                if(i.containsEnchantment(this)) {
                    total = total + i.getEnchantmentLevel(this);
                }
            }
            e.getPlayer().setMaxHealth(20 + (total * 0.5));
        }
    }

}
