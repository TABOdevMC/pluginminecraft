package fr.tabodevmc.pluginminecraft.service;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public final class LuckPermsService {
    private final LuckPerms luckPerms;

    public LuckPermsService(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    public String getPrimaryGroup(Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        return user == null ? "unknown" : user.getPrimaryGroup();
    }

    public String getPrefix(Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return "";
        String prefix = user.getCachedData().getMetaData().getPrefix();
        return prefix == null ? "" : prefix;
    }
}
