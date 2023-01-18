package net.tonimatasdev.perworldplugins.util;

import net.tonimatasdev.perworldplugins.PerWorldPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.HashMap;
import java.util.Map;

public class ListenerUtils {
    private static final Map<HandlerList, RegisteredListener[]> map = new HashMap<>();

    public static void addListeners() {
        HandlerList.getHandlerLists().forEach((handlerList -> map.put(handlerList, handlerList.getRegisteredListeners())));

        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (!plugin.getName().equals("PerWorldPlugins")) {
                HandlerList.unregisterAll(plugin);
            }
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "PerWorldPlugins unregistered all Listeners correctly.");
    }

    public static void perWorldPlugins(Event event, World world) {
        RegisteredListener[] registeredListeners = map.get(event.getHandlers());

        if (registeredListeners != null) {
            for (RegisteredListener registeredListener : registeredListeners) {
                Plugin plugin = registeredListener.getPlugin();

                if (!plugin.getName().equals("PerWorldPlugins")) {
                    if (PerWorldPlugins.getInstance().getConfig().getBoolean("blacklist")) {
                        if (!PerWorldPlugins.getInstance().getConfig().getStringList("plugins." + plugin.getName()).contains(world.getName())) {
                            try {
                                registeredListener.callEvent(event);
                            } catch (EventException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        if (PerWorldPlugins.getInstance().getConfig().getStringList("plugins." + plugin.getName()).contains(world.getName())) {
                            try {
                                registeredListener.callEvent(event);
                            } catch (EventException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void noWorldEvents(Event event) {
        RegisteredListener[] registeredListeners = map.get(event.getHandlers());

        if (registeredListeners != null) {
            for (RegisteredListener registeredListener : registeredListeners) {
                Plugin plugin = registeredListener.getPlugin();

                if (!plugin.getName().equals("PerWorldPlugins")) {
                    try {
                        registeredListener.callEvent(event);
                    } catch (EventException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
