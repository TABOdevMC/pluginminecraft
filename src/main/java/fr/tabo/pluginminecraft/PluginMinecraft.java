package fr.tabo.pluginminecraft;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.mvplugins.multiverse.core.MultiverseCoreApi;

public final class PluginMinecraft extends JavaPlugin {

    private LuckPerms luckPerms;
    private MultiverseCoreApi multiverse;

    @Override
    public void onEnable() {
        // Both plugins are declared as hard dependencies in plugin.yml,
        // so their APIs are available when our plugin is enabled.
        this.luckPerms = LuckPermsProvider.get();
        this.multiverse = MultiverseCoreApi.get();

        PluginCommand command = getCommand("pluginminecraft");
        if (command == null) {
            getLogger().severe("Impossible d'enregistrer /pluginminecraft.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        command.setExecutor(new PluginMinecraftCommand(this));
        command.setTabCompleter(new PluginMinecraftCommand(this));

        getLogger().info("PluginMinecraft activé.");
        getLogger().info("LuckPerms: OK");
        getLogger().info("Multiverse-Core: OK");
    }

    @Override
    public void onDisable() {
        getLogger().info("PluginMinecraft désactivé.");
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public MultiverseCoreApi getMultiverse() {
        return multiverse;
    }
}
