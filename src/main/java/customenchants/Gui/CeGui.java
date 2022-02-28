package customenchants.Gui;

import customenchants.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CeGui {

    private Main main = Main.getInstance();
    private File file = new File(main.getDataFolder().getAbsolutePath() + "/ce.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public Gui gui() {
        Gui ce = new Gui(color(config.getString("title")), config.getInt("size")).c()
                .noShifties();

        List<String> format = config.getStringList("format");
        makeFormat(config, ce, format, "items");

        return ce;
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
