package fr.tabodevmc.pluginminecraft.command;

import fr.tabodevmc.pluginminecraft.PluginMinecraft;
import fr.tabodevmc.pluginminecraft.service.LuckPermsService;
import fr.tabodevmc.pluginminecraft.service.MultiverseService;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public final class PluginMinecraftCommand implements CommandExecutor, TabCompleter {
    private final PluginMinecraft plugin;
    private final LuckPermsService luckPerms;
    private final MultiverseService multiverse;

    public PluginMinecraftCommand(PluginMinecraft plugin, LuckPermsService luckPerms, MultiverseService multiverse) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
        this.multiverse = multiverse;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(ChatColor.AQUA + "PluginMinecraft v" + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.GRAY + "Paper: " + plugin.getServer().getMinecraftVersion());
            sender.sendMessage(ChatColor.GRAY + "LuckPerms: " + ChatColor.GREEN + "OK");
            sender.sendMessage(ChatColor.GRAY + "Multiverse-Core: " +
                    (multiverse.isAvailable() ? ChatColor.GREEN + "OK" : ChatColor.RED + "OFF"));
            return true;
        }

        if (args[0].equalsIgnoreCase("whoami")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Commande réservée aux joueurs.");
                return true;
            }
            sender.sendMessage(ChatColor.AQUA + "Profil de " + player.getName());
            sender.sendMessage(ChatColor.GRAY + "Groupe: " + ChatColor.YELLOW + luckPerms.getPrimaryGroup(player));
            sender.sendMessage(ChatColor.GRAY + "Préfixe: " + ChatColor.WHITE +
                    (luckPerms.getPrefix(player).isEmpty() ? "(aucun)" : luckPerms.getPrefix(player)));
            sender.sendMessage(ChatColor.GRAY + "Monde: " + ChatColor.GREEN + player.getWorld().getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("worlds")) {
            sender.sendMessage(ChatColor.AQUA + "Mondes chargés:");
            for (String world : multiverse.getLoadedWorlds()) {
                sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + world);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("pluginminecraft.admin")) {
                sender.sendMessage(ChatColor.RED + "Tu n'as pas la permission.");
                return true;
            }
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "PluginMinecraft rechargé.");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <info|whoami|worlds|reload>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) return List.of();
        return List.of("info", "whoami", "worlds", "reload").stream()
                .filter(s -> s.startsWith(args[0].toLowerCase()))
                .toList();
    }
}
