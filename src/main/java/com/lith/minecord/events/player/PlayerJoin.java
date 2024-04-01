package com.lith.minecord.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DiscordManager.init().sendMessage(
                ConfigManager.livechatConfig.channelId,
                ConfigManager.livechatConfig.joinMessage
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName()));
    }
}
