package fr.cristallya.islandsplugin.service;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public final class LuckPermsService {
    private final LuckPerms luckPerms;
    public LuckPermsService(LuckPerms luckPerms) { this.luckPerms = luckPerms; }
    public String getPrimaryGroup(Player player) { User u = luckPerms.getUserManager().getUser(player.getUniqueId()); return u == null ? "unknown" : u.getPrimaryGroup(); }
    public String getPrefix(Player player) { User u = luckPerms.getUserManager().getUser(player.getUniqueId()); if (u == null) return ""; String p = u.getCachedData().getMetaData().getPrefix(); return p == null ? "" : p; }
}
