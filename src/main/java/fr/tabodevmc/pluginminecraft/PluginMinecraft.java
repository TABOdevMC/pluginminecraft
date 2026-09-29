package fr.tabodevmc.pluginminecraft;

import fr.tabodevmc.pluginminecraft.command.PluginMinecraftCommand;
import fr.tabodevmc.pluginminecraft.service.LuckPermsService;
import fr.tabodevmc.pluginminecraft.service.MultiverseService;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMinecraft extends JavaPlugin {
    @Override
    public void onEnable() {
        LuckPerms luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        if (luckPerms == null) {
            getLogger().severe("LuckPerms API introuvable.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (getServer().getPluginManager().getPlugin("Multiverse-Core") == null) {
            getLogger().severe("Multiverse-Core introuvable.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        var lp = new LuckPermsService(luckPerms);
        var mv = new MultiverseService();

        var command = new PluginMinecraftCommand(this, lp, mv);
        var pluginCommand = getCommand("pluginminecraft");
        if (pluginCommand == null) {
            getLogger().severe("Commande pluginminecraft absente de plugin.yml.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);

        getLogger().info("PluginMinecraft active - Paper " + getServer().getMinecraftVersion());
    }
}
