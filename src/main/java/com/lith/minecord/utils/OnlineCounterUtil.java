package com.lith.minecord.utils;

import org.bukkit.Bukkit;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;
import com.lith.minecord.events.player.UpdateChannelTopic;

public class OnlineCounterUtil {
    public static void setPlayerOnlineCounter() {
        String description = ConfigManager.livechatConfig.onlineCounterFormat
                .replace(Static.MessageKey.CURRENT, String.valueOf(UpdateChannelTopic.playerCount))
                .replace(Static.MessageKey.MAX, String.valueOf(Bukkit.getMaxPlayers()));

        DiscordManager.init().setChannelTopic(
                ConfigManager.botConfig.serverId,
                ConfigManager.livechatConfig.channelId,
                description);
    }
}
