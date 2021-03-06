/*
 * This file is part of coins3
 *
 * Copyright © 2020 Beelzebu
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.beelzebu.coins.bukkit.command;

import com.github.beelzebu.coins.bukkit.CoinsBukkitPlugin;
import com.github.beelzebu.coins.bukkit.utils.CompatUtils;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author Beelzebu
 */
public class CommandManager {

    @NotNull
    private final CoinsBukkitPlugin plugin;
    private SimpleCommandMap commandMap;
    @NotNull
    private final Command coinsCommand;
    @NotNull
    private final Command multiplier;

    public CommandManager(@NotNull CoinsBukkitPlugin plugin) {
        this.plugin = plugin;
        coinsCommand = new CoinsCommand(plugin, plugin.getConfig().getCommand()).setDescription(plugin.getConfig().getCommandDescription()).setAliases(plugin.getConfig().getCommandAliases()).setUsage(plugin.getConfig().getCommandUsage());
        coinsCommand.setPermission(plugin.getConfig().getString("General.Command.Coins.Permission", "coins.use"));

        multiplier = new MultipliersCommand(plugin, plugin.getMultipliersConfig().getCommand()).setDescription(plugin.getMultipliersConfig().getCommandDescription()).setAliases(plugin.getMultipliersConfig().getCommandAliases()).setUsage(plugin.getMultipliersConfig().getCommandUsage());
        multiplier.setPermission(plugin.getConfig().getString("General.Command.Multiplier.Permission", "coins.multiplier"));

    }

    public void registerCommand() {
        registerCommand(plugin.getBootstrap(), coinsCommand);
        registerCommand(plugin.getBootstrap(), multiplier);
    }

    public void unregisterCommand() {
        unregisterCommand(plugin.getBootstrap(), coinsCommand);
        unregisterCommand(plugin.getBootstrap(), multiplier);
    }

    private void registerCommand(@NotNull Plugin plugin, @NotNull Command command) {
        unregisterCommand(plugin, command);
        getCommandMap().register(/*fallback prefix*/plugin.getName(), command);
    }

    public void unregisterCommand(@NotNull Plugin plugin, @NotNull Command command) {
        Map<String, Command> knownCommands = getKnownCommandsMap();
        knownCommands.remove(plugin.getName() + ":" + command.getName().toLowerCase(Locale.ENGLISH).trim());
        command.getAliases().forEach(knownCommands::remove);
        command.setLabel(command.getName());
    }

    private Object getPrivateField(@NotNull Object object, @NotNull String field) throws ReflectiveOperationException {
        return getPrivateField(object, field, false);
    }

    private Object getPrivateField(@NotNull Object object, @NotNull String field, boolean superClass) throws ReflectiveOperationException {
        Class<?> clazz = superClass ? object.getClass().getSuperclass() : object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        return objectField.get(object);
    }

    private SimpleCommandMap getCommandMap() {
        if (commandMap != null) {
            return commandMap;
        }
        try {
            return (commandMap = (SimpleCommandMap) getPrivateField(Bukkit.getPluginManager(), "commandMap"));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new SimpleCommandMap(Bukkit.getServer());
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private Map<String, Command> getKnownCommandsMap() {
        try {
            return (Map<String, Command>) getPrivateField(getCommandMap(), "knownCommands", CompatUtils.VERSION.isAfterOrEq(CompatUtils.MinecraftVersion.MINECRAFT_1_13));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
