package fr.tabo.pluginminecraft;

import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mvplugins.multiverse.core.MultiverseCoreApi;

import java.util.ArrayList;
import java.util.List;

public final class PluginMinecraftCommand implements CommandExecutor, TabCompleter {

    private final PluginMinecraft plugin;

    public PluginMinecraftCommand(PluginMinecraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            sendInfo(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Configuration rechargée.");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <info|reload>");
        return true;
    }

    private void sendInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== PluginMinecraft ===");
        sender.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.WHITE + plugin.getDescription().getVersion());

        if (sender instanceof Player player) {
            UserManager userManager = plugin.getLuckPerms().getUserManager();
            User user = userManager.getUser(player.getUniqueId());

            if (user != null) {
                sender.sendMessage(ChatColor.YELLOW + "Groupe LuckPerms: "
                        + ChatColor.WHITE + user.getPrimaryGroup());
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Groupe LuckPerms: "
                        + ChatColor.RED + "utilisateur non chargé");
            }

            String worldName = player.getWorld().getName();
            MultiverseCoreApi mv = plugin.getMultiverse();

            boolean registered = mv.getWorldManager().getWorld(worldName).isDefined();

            sender.sendMessage(ChatColor.YELLOW + "Monde Bukkit: "
                    + ChatColor.WHITE + worldName);
            sender.sendMessage(ChatColor.YELLOW + "Monde Multiverse: "
                    + (registered ? ChatColor.GREEN + "enregistré" : ChatColor.RED + "non enregistré"));
        } else {
            sender.sendMessage(ChatColor.GRAY + "Connecte-toi en jeu pour afficher ton groupe et ton monde.");
        }
    }

    @Override
    public List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String alias,
            @NotNull String[] args
    ) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            suggestions.add("info");
            suggestions.add("reload");

            String prefix = args[0].toLowerCase();
            suggestions.removeIf(value -> !value.startsWith(prefix));
            return suggestions;
        }

        return List.of();
    }
}
