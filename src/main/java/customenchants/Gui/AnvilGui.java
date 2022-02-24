package customenchants.Gui;

import customenchants.Main;
import customenchants.Utils.EnchantmentUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnvilGui {

    private Main main = Main.getInstance();
    private File file = new File(main.getDataFolder().getAbsolutePath() + "/anvil.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    
    public Gui gui() {
        Gui anvil = new Gui(color(config.getString("title")), config.getInt("size")).c()
                .noShifties();

        List<String> format = config.getStringList("format");
        makeFormat(config, anvil, format, "items");

        anvil.onClick(e -> {
           Player clicked = (Player) e.getWhoClicked();
           int slot = e.getSlot();

           int input = config.getInt("input");
           int enchantment = config.getInt("enchant");
           int output = config.getInt("output");

           //need to check if the item in their cursor is null
            if(e.getCursor() == null) return;

            if(slot == input) {
                //They are putting an item in
                if(anvil.getContents()[input] == null && anvil.getContents()[enchantment] != null) {

                    ItemStack tooEnchant = e.getCursor();
                    ItemStack finalStack = tooEnchant.clone();
                    ItemStack book = anvil.getContents()[enchantment];


                    if(!book.getType().equals(Material.BOOK)) return;
                    if(book.getEnchantments() == null) return;

                    Enchantment ench = null;
                    int level = 0;

                    for(Map.Entry entry : book.getEnchantments().entrySet()) {
                        ench = (Enchantment) entry.getKey();
                        level = (Integer) entry.getValue();
                    }

                    if(level < finalStack.getEnchantmentLevel(ench)) return;

                    EnchantmentUtils.applyEnchantment(ench, finalStack, level);
                    anvil.i(output, finalStack);

                }
                if(anvil.getContents()[slot] == null) {
                    anvil.i(slot, e.getCursor());
                    e.setCursor(null);
                } else {
                    e.setCursor(anvil.getContents()[slot]);
                    anvil.i(slot, null);
                    anvil.i(output, null);
                }
            } else if(slot == enchantment) {
                if(anvil.getContents()[input] != null && anvil.getContents()[enchantment] == null) {

                    ItemStack tooEnchant = anvil.getContents()[input];
                    ItemStack finalStack = tooEnchant.clone();
                    ItemStack book = e.getCursor();


                    if(!book.getType().equals(Material.BOOK)) return;
                    if(book.getEnchantments() == null) return;

                    Enchantment ench = null;
                    int level = 0;

                    for(Map.Entry entry : book.getEnchantments().entrySet()) {
                        ench = (Enchantment) entry.getKey();
                        level = (Integer) entry.getValue();
                    }

                    if(level < finalStack.getEnchantmentLevel(ench)) return;

                    EnchantmentUtils.applyEnchantment(ench, finalStack, level);
                    anvil.i(output, finalStack);

                }
                //they are putting in an a enchant!
                if(anvil.getContents()[slot] == null) {
                    anvil.i(slot, e.getCursor());
                    e.setCursor(null);
                } else {
                    e.setCursor(anvil.getContents()[slot]);
                    anvil.i(slot, null);
                    anvil.i(output, null);
                }
            } else if(slot == output) {
                if(anvil.getContents()[output] == null || (anvil.getContents()[input] == null) || (anvil.getContents()[enchantment] == null)) return;

                ItemStack finalStack =  anvil.getContents()[output];

                anvil.i(input, null);
                anvil.i(enchantment, null);

                e.setCursor(finalStack);

                anvil.i(output, null);

            }



        });

        return anvil;
    }
    
    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void makeFormat(FileConfiguration config, Gui gui, List<String> toFormat, String keyForItems) {
        int size = gui.getInventory().getSize();
        if (toFormat.size() == size / 9) {
            for (int i = 0; i < (size / 9); i++) {
                String s = toFormat.get(i);
                for (int z = 0; z < 9; z++) {
                    String removeSpaces = s.replaceAll(" ", "");
                    char individual = removeSpaces.charAt(z);
                    if (i > 0) {
                        if (config.get(keyForItems + "." + individual) == null) {
                            continue;
                        } else {
                            ItemStack stack;
                            if(config.getInt(keyForItems + "." + individual + ".data") == 0) {
                                stack = new ItemStack(Material.matchMaterial(config.getString(keyForItems + "." + individual + ".material")));
                            } else {
                                stack = new ItemStack(Material.matchMaterial(config.getString(keyForItems + "." + individual + ".material")), 1, (short) config.getInt(keyForItems + "." + individual + ".data"));
                            }
                            ItemMeta im = stack.getItemMeta();
                            im.setDisplayName(color(config.getString(keyForItems + "." + individual + ".name")));
                            List<String> lore = new ArrayList<>();
                            for(String loreString : config.getStringList(keyForItems + "." + individual + ".lore")) {
                                lore.add(color(loreString));
                            }
                            im.setLore(lore);
                            stack.setItemMeta(im);


                            gui.i((9 * i) + z, stack);
                        }
                    } else {
                        if (config.get(keyForItems + "." + individual) == null) {
                            continue;
                        } else {
                            ItemStack stack;
                            if(config.getInt(keyForItems + "." + individual + ".data") == 0) {
                                stack = new ItemStack(Material.matchMaterial(config.getString(keyForItems + "." + individual + ".material")));
                            } else {
                                stack = new ItemStack(Material.matchMaterial(config.getString(keyForItems + "." + individual + ".material")), 1, (short) config.getInt(keyForItems + "." + individual + ".data"));
                            }
                            ItemMeta im = stack.getItemMeta();
                            im.setDisplayName(color(config.getString(keyForItems + "." + individual + ".name")));
                            List<String> lore = new ArrayList<>();
                            for(String loreString : config.getStringList(keyForItems + "." + individual + ".lore")) {
                                lore.add(color(loreString));
                            }
                            im.setLore(lore);
                            stack.setItemMeta(im);


                            gui.i(z, stack);
                        }
                    }
                }

            }

        }
    }
    
}
