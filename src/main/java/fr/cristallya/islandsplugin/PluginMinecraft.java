package fr.cristallya.islandsplugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMinecraft extends JavaPlugin {

    private LuckPerms luckPerms;
    private MultiverseCore multiverse;

    @Override
    public void onEnable() {
        // Point d'entrée du plugin.
    }

    @Override
    public void onDisable() {
        // Nettoyage du plugin.
    }
}
