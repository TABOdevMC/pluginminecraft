package fr.tabodevmc.pluginminecraft.service;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.util.List;

public final class MultiverseService {
    public boolean isAvailable() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        return plugin != null && plugin.isEnabled();
    }

    public List<String> getLoadedWorlds() {
        return Bukkit.getWorlds().stream().map(World::getName).toList();
    }

    public World getWorld(String name) {
        return Bukkit.getWorld(name);
    }
}
