package fr.cristallya.islandsplugin.service;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import java.util.List;

public final class MultiverseService {
    public boolean isAvailable() { Plugin p = Bukkit.getPluginManager().getPlugin("Multiverse-Core"); return p != null && p.isEnabled(); }
    public List<String> getLoadedWorlds() { return Bukkit.getWorlds().stream().map(World::getName).toList(); }
    public World getWorld(String name) { return Bukkit.getWorld(name); }
}
