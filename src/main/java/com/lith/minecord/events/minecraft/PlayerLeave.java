package com.lith.minecord.events.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerLeave implements Listener {
    private final Plugin plugin;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getDiscordManager().sendMessage(
                Static.textChannel,
                plugin.configs.dcMsg.leave
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName()));
    }
}
