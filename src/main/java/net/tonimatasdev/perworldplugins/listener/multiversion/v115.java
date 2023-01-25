package net.tonimatasdev.perworldplugins.listener.multiversion;

import net.tonimatasdev.perworldplugins.util.HandlerListUtil;
import net.tonimatasdev.perworldplugins.util.ListenerUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.event.world.TimeSkipEvent;

import java.util.Arrays;

public class v115 implements Listener {
    public static void addHandlerList() {
        HandlerListUtil.minecraftHandlerLists.addAll(Arrays.asList(
                EntityEnterBlockEvent.getHandlerList(),
                LootGenerateEvent.getHandlerList(),
                TimeSkipEvent.getHandlerList()
        ));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public static void onEnterBlock(EntityEnterBlockEvent event) {
        ListenerUtils.perWorldPlugins(event, event.getEntity().getWorld());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public static void onLootGenerate(LootGenerateEvent event) {
        ListenerUtils.perWorldPlugins(event, event.getWorld());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public static void onTimeSkip(TimeSkipEvent event) {
        ListenerUtils.perWorldPlugins(event, event.getWorld());
    }
}
