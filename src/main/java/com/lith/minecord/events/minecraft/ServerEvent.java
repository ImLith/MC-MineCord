package com.lith.minecord.events.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import com.lith.minecord.MineCordPlugin;

public class ServerEvent implements Listener {
    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        MineCordPlugin.getDiscordManager().start();
    }
}
