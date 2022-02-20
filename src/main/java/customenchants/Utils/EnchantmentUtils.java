package customenchants.Utils;

import customenchants.Main;
import customenchants.Managers.EnchantmentManager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.nio.charset.StandardCharsets;
import java.util.*;

public final class EnchantmentUtils
{

    static Main main = Main.getInstance();

    private static final Set<Material> PICKAXE_TYPES = EnumSet.of(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE);

    private static final Set<Material> SWORD_TYPES = EnumSet.of(
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.GOLDEN_SWORD,
            Material.DIAMOND_SWORD);

    private static final Set<Material> ARMOR_TYPES = EnumSet.of(
            Material.LEATHER_HELMET,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_BOOTS,
            Material.IRON_HELMET,
            Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS,
            Material.IRON_BOOTS,
            Material.GOLDEN_HELMET,
            Material.GOLDEN_CHESTPLATE,
            Material.GOLDEN_LEGGINGS,
            Material.GOLDEN_BOOTS,
            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS,
            Material.NETHERITE_HELMET,
            Material.NETHERITE_CHESTPLATE,
            Material.NETHERITE_LEGGINGS,
            Material.NETHERITE_BOOTS);

    private static final Set<Material> BOW = EnumSet.of(
            Material.BOW);


    private EnchantmentUtils()
    {

    }

    public static boolean isPickaxe(final ItemStack item)
    {
        return PICKAXE_TYPES.contains(item.getType());
    }

    public static boolean isSword(final ItemStack item) {
        return SWORD_TYPES.contains(item.getType());
    }

    public static boolean isArmor(final ItemStack item) {
        return ARMOR_TYPES.contains(item.getType());
    }

    public static boolean isBow(final ItemStack item) {
        return BOW.contains(item);

    }

    public static boolean containsCustomEnchant(final ItemStack item) {
        for(Map.Entry e : item.getEnchantments().entrySet()) {
            for(Enchantment ench : Main.getEnchantmentManager().getCustomEnchantments()) {
                if(e.getKey().equals(ench)) return true;
            }
        }

        return false;
    }

    /**
     * Because these are custom enchantments, the Minecraft client does not add them to the lore automatically.
     * As such, we have to add it ourselves.
     * <p>
     * This method uses {@link ItemMeta} and a few String operations to add the lore manually, removing old versioned lore.
     *
     * @param enchantment the enchantment to add.
     * @param itemStack   the item to add the enchantment to.
     * @param level       the level of the enchantment to add.
     * @throws IllegalArgumentException if the enchantment is unsafe (more than the maximum level).
     * @author Alex Wood
     */

    public static void applyEnchantment(final Enchantment enchantment, final ItemStack itemStack, final int level)
    {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return; //the item type is probably AIR so can't be enchanted.

        List<String> lore = itemMeta.getLore();

        if (lore == null)
        {
            lore = new ArrayList<>(1); //a nice little micro-optimization since we're only adding 1 element to the list
        } else
        {
            int existingLevel = itemMeta.getEnchantLevel(enchantment);
            if (existingLevel != 0) //Lore removal needed as some is present.
            {
                //remove any old lore. Note that this way of doing it could break lore ordering.
                lore.removeIf(line -> ChatColor.stripColor(line).matches(enchantment.getKey().getKey() + " " + existingLevel));
            }
        }

        lore.add(color(main.getConfig().getString("enchantmentLore")
                .replace("{enchant}", WordUtils.capitalizeFully(enchantment.getKey().getKey()))
                .replace("{level}", Roman.toRoman(level))));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        //Simple as this! Glowing is handled automatically.
        itemStack.addEnchantment(enchantment, level);
    }

    public static ItemStack enchantBook(final Enchantment enchantment, final int level) {

        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta im = book.getItemMeta();

        List<String> lore = new ArrayList<>();

        for(String s : main.getConfig().getStringList("enchantedBook.lore")) {
            lore.add(color(s)
                    .replace("{name}", enchantment.getName())
                    .replace("{roman}", Roman.toRoman(level))
                    .replace("{level}", level + "")
                    .replace("{description}", main.getConfig().getString("enchants." + enchantment.getName().toLowerCase() + ".description")));
        }

        im.setLore(lore);
        im.setDisplayName(color(main.getConfig().getString("enchantedBook.name")
                .replace("{name}", enchantment.getName())
                .replace("{roman}", Roman.toRoman(level))
                .replace("{level}", level + "")));

        book.setItemMeta(im);

        book.addUnsafeEnchantment(enchantment, level);

        return book;

    }

    public static Enchantment getEnchant(String name) {
        Set<Enchantment> enchants = Main.getEnchantmentManager().getCustomEnchantments();

        for(Enchantment e : enchants) {
            if(e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }

        return null;
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
