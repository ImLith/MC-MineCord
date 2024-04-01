package com.lith.minecord.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DiscordManager.init().sendMessage(
                ConfigManager.livechatConfig.channelId,
                ConfigManager.livechatConfig.leaveMessage
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName()));
    }
}
