package com.lith.minecord.events.player;

import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;

public class PlayerAchievement implements Listener {
    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent event) {
        Advancement advancement = event.getAdvancement();

        DiscordManager.init().sendMessage(
                ConfigManager.livechatConfig.channelId,
                ConfigManager.livechatConfig.achievement
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName())
                        .replace(Static.MessageKey.ACHIEVEMENT_NAME, advancement.getKey().getKey())
                        .replace(Static.MessageKey.ACHIEVEMENT_DESCRIPTION, advancement.toString())

        );
    }
}
