package fr.cristallya.islandsplugin.command;

import fr.cristallya.islandsplugin.PluginMinecraft;
import fr.cristallya.islandsplugin.service.LuckPermsService;
import fr.cristallya.islandsplugin.service.MultiverseService;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.List;

public final class PluginMinecraftCommand implements CommandExecutor, TabCompleter {
    private final PluginMinecraft plugin; private final LuckPermsService luckPerms; private final MultiverseService multiverse;
    public PluginMinecraftCommand(PluginMinecraft plugin, LuckPermsService luckPerms, MultiverseService multiverse) { this.plugin=plugin; this.luckPerms=luckPerms; this.multiverse=multiverse; }
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info")) { sender.sendMessage(ChatColor.AQUA+"PluginMinecraft v"+plugin.getDescription().getVersion()); sender.sendMessage(ChatColor.GRAY+"LuckPerms: "+ChatColor.GREEN+"OK"); sender.sendMessage(ChatColor.GRAY+"Multiverse-Core: "+(multiverse.isAvailable()?ChatColor.GREEN+"OK":ChatColor.RED+"OFF")); return true; }
        if (args[0].equalsIgnoreCase("whoami")) { if (!(sender instanceof Player p)) { sender.sendMessage(ChatColor.RED+"Commande réservée aux joueurs."); return true; } sender.sendMessage(ChatColor.GRAY+"Groupe: "+luckPerms.getPrimaryGroup(p)); sender.sendMessage(ChatColor.GRAY+"Préfixe: "+luckPerms.getPrefix(p)); sender.sendMessage(ChatColor.GRAY+"Monde: "+p.getWorld().getName()); return true; }
        if (args[0].equalsIgnoreCase("worlds")) { for (String w : multiverse.getLoadedWorlds()) sender.sendMessage(ChatColor.GRAY+"- "+ChatColor.WHITE+w); return true; }
        if (args[0].equalsIgnoreCase("reload")) { if (!sender.hasPermission("pluginminecraft.admin")) { sender.sendMessage(ChatColor.RED+"Tu n'as pas la permission."); return true; } plugin.reloadConfig(); sender.sendMessage(ChatColor.GREEN+"PluginMinecraft rechargé."); return true; }
        sender.sendMessage(ChatColor.RED+"Usage: /"+label+" <info|whoami|worlds|reload>"); return true;
    }
    @Override public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) { if (args.length != 1) return List.of(); return List.of("info","whoami","worlds","reload").stream().filter(s->s.startsWith(args[0].toLowerCase())).toList(); }
}
