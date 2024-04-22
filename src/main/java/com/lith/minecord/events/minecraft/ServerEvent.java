package com.lith.minecord.events.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.MineCordPlugin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerEvent implements Listener {
    @NotNull
    private final MineCordPlugin plugin;

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        plugin.getDiscordManager().start();
    }
}
