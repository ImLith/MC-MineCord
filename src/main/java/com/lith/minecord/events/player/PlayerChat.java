package com.lith.minecord.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerChat implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        String msg = PlainTextComponentSerializer.plainText().serialize(event.originalMessage());

        DiscordManager.init().sendMessage(
                ConfigManager.livechatConfig.channelId,
                ConfigManager.livechatConfig.formatMinecraft
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName())
                        .replace(Static.MessageKey.CONTENT, msg));
    }
}
