package customenchants.Commands;

import customenchants.Gui.AnvilGui;
import customenchants.Managers.EnchantmentManager;
import customenchants.Utils.EnchantmentUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class CeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0) {
            new AnvilGui().gui().show(p);
        } else {
            if(args[0].equalsIgnoreCase("book")) {
                //enchanted book
                if(args.length < 3) {
                    p.sendMessage(EnchantmentUtils.color("&c&lEnchants &7| Incorrect usage : /ce book <enchant> <level>"));
                    return false;
                } else {
                    String enchantName = args[1];
                    int level = Integer.parseInt(args[2]);
                    Enchantment ench = EnchantmentUtils.getEnchant(enchantName);
                    System.out.println(ench);

                    p.getInventory().addItem(EnchantmentUtils.enchantBook(ench, level));
                }
            }
        }

        return false;
    }
}
