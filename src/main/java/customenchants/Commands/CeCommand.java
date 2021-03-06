package customenchants.Commands;

import customenchants.Gui.AnvilGui;
import customenchants.Gui.CeGui;
import customenchants.Main;
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

        Main main = Main.getInstance();

        Player p = (Player) sender;
        if(args.length == 0) {
            new CeGui().gui().show(p);
        } else {
            if(args[0].equalsIgnoreCase("book")) {
                //enchanted book
                if(!p.hasPermission("enchants.give")) return false;
                if(args.length < 3) {
                    p.sendMessage(EnchantmentUtils.color(main.getConfig().getString("messages.incorrectUsage")));
                    return false;
                } else {
                    String enchantName = args[1];

                    int level = 0;
                    try {
                        level = Integer.parseInt(args[2]);
                    } catch(NumberFormatException e) {
                        p.sendMessage(EnchantmentUtils.color(main.getConfig().getString("messages.tooHigh")));
                        return false;
                    }
                    Enchantment ench = EnchantmentUtils.getEnchant(enchantName);
                    if(ench == null) {
                        p.sendMessage(EnchantmentUtils.color(main.getConfig().getString("messages.invalidEnchant")));
                        return false;
                    }
                    if(level > ench.getMaxLevel()) {
                        p.sendMessage(EnchantmentUtils.color(main.getConfig().getString("messages.tooHigh")));
                        return false;
                    }
                    p.getInventory().addItem(EnchantmentUtils.enchantBook(ench, level));
                }
            }
        }

        return false;
    }
}
