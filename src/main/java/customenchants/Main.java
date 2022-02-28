package customenchants;

import customenchants.ArmorEquipAPI.ArmorListener;
import customenchants.Commands.AnvilCommand;
import customenchants.Commands.CeCommand;
import customenchants.Managers.EnchantmentManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static EnchantmentManager enchantmentManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        enchantmentManager = new EnchantmentManager(this);

        enchantmentManager.registerAllCustomEnchantments();

        getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);


        saveDefaultConfig();
        saveResource("anvil.yml", false);
        saveResource("ce.yml", false);

        this.getCommand("ce").setExecutor(new CeCommand());
        this.getCommand("anvil").setExecutor(new AnvilCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        enchantmentManager.unregisterAllCustomEnchantments();
    }

    public static Main getInstance() {
        return instance;
    }

    public static EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }
}
