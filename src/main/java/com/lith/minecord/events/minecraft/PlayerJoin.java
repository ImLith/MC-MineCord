package com.lith.minecord.events.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerJoin implements Listener {
    private final Plugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getDiscordManager().sendMessage(
                Static.textChannel,
                plugin.configs.dcMsg.join
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName()));
    }
}
