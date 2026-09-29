# Guide de développement — PluginMinecraft

Ce guide explique la structure du projet et l'utilisation de Paper, LuckPerms et Multiverse-Core.

## Stack

- Paper 26.2
- Java 25
- Gradle Kotlin DSL
- LuckPerms API 5.5
- Multiverse-Core fourni par le serveur
- Package Java : `fr.cristallya.islandsplugin`

## Dépendances Gradle

Dans `build.gradle.kts` :

```kotlin
dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
    compileOnly("net.luckperms:api:5.5")
}
```

`compileOnly` signifie que l'API sert à compiler sans être embarquée dans le JAR. Paper et LuckPerms sont fournis par le serveur.

## Dépendances serveur

Dans `plugin.yml` :

```yaml
depend:
  - LuckPerms
  - Multiverse-Core
```

Pour une dépendance optionnelle, utiliser `softdepend`, puis vérifier sa présence avant d'appeler son API.

## LuckPerms

Imports :

```java
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
```

Obtenir l'API :

```java
LuckPerms luckPerms =
        getServer().getServicesManager().load(LuckPerms.class);
```

Obtenir un utilisateur :

```java
User user = luckPerms.getUserManager()
        .getUser(player.getUniqueId());
```

Le résultat peut être `null`.

Groupe principal :

```java
String group = user.getPrimaryGroup();
```

Préfixe :

```java
String prefix = user.getCachedData()
        .getMetaData()
        .getPrefix();
```

Permission :

```java
if (!player.hasPermission("islands.admin")) {
    return;
}
```

Utiliser des permissions préfixées par le plugin : `islands.command`, `islands.admin`, `islands.create`, etc.

## Multiverse-Core

Pour les opérations générales sur les mondes, utiliser l'API Bukkit/Paper.

Imports :

```java
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
```

Vérifier Multiverse :

```java
Plugin plugin = Bukkit.getPluginManager()
        .getPlugin("Multiverse-Core");

boolean available = plugin != null && plugin.isEnabled();
```

Récupérer un monde chargé :

```java
World world = Bukkit.getWorld("island-123");
```

Lister les mondes :

```java
List<String> worlds = Bukkit.getWorlds()
        .stream()
        .map(World::getName)
        .toList();
```

Pour les fonctionnalités propres à Multiverse, ajouter l'API correspondant à la version installée et utiliser ses classes publiques.

## Architecture recommandée

Garder une séparation simple :

```text
Commande -> Service -> API externe
```

Une commande doit préférer `luckPermsService.getPrimaryGroup(player)` plutôt que de manipuler directement LuckPerms partout.

## Ajouter une commande

Dans `plugin.yml` :

```yaml
commands:
  island:
    description: Commandes des îles.
    usage: /island <create|delete|home>
    permission: islands.command
```

Implémenter ensuite `CommandExecutor` et `TabCompleter`.

## Ajouter une dépendance

Pour une API Maven :

```kotlin
repositories {
    maven("https://exemple.repo/")
}

dependencies {
    compileOnly("com.exemple:exemple-api:1.0.0")
}
```

Puis déclarer le plugin avec `depend` ou `softdepend`.

Une dépendance a trois aspects :

1. Gradle permet de compiler.
2. `plugin.yml` contrôle sa présence et son chargement.
3. Les imports Java permettent d'appeler son API.

## Bonnes pratiques

- Ne pas embarquer Paper ou LuckPerms dans le JAR.
- Utiliser `compileOnly` pour les APIs fournies par le serveur.
- Vérifier les dépendances optionnelles.
- Garder les APIs externes dans des services.
- Éviter les opérations longues sur le thread principal.
- Vérifier les valeurs pouvant être `null`.

## Commandes actuelles

- `/pm info` : état du plugin et des dépendances.
- `/pm whoami` : groupe, préfixe LuckPerms et monde actuel.
- `/pm worlds` : mondes chargés.
- `/pm reload` : recharge la configuration, permission `pluginminecraft.admin`.

## Imports utiles

Paper/Bukkit :

```java
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
```

Commandes :

```java
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
```

LuckPerms :

```java
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
```

## Structure future conseillée

```text
fr.cristallya.islandsplugin
├── PluginMinecraft.java
├── command/
│   ├── IslandCommand.java
│   └── AdminIslandCommand.java
├── island/
│   ├── Island.java
│   ├── IslandManager.java
│   └── IslandService.java
├── service/
│   ├── LuckPermsService.java
│   └── MultiverseService.java
├── listener/
│   └── PlayerListener.java
└── util/
    └── MessageUtil.java
```
