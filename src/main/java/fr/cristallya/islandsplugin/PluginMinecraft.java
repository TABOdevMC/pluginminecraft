package fr.cristallya.islandsplugin;

import fr.cristallya.islandsplugin.command.PluginMinecraftCommand;
import fr.cristallya.islandsplugin.service.LuckPermsService;
import fr.cristallya.islandsplugin.service.MultiverseService;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMinecraft extends JavaPlugin {
    @Override public void onEnable() {
        LuckPerms lp = getServer().getServicesManager().load(LuckPerms.class);
        if (lp == null || getServer().getPluginManager().getPlugin("Multiverse-Core") == null) {
            getServer().getPluginManager().disablePlugin(this); return;
        }
        var command = new PluginMinecraftCommand(this, new LuckPermsService(lp), new MultiverseService());
        var pluginCommand = getCommand("pluginminecraft");
        if (pluginCommand == null) { getServer().getPluginManager().disablePlugin(this); return; }
        pluginCommand.setExecutor(command); pluginCommand.setTabCompleter(command);
    }
}
